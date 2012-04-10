package photosync.core;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import photosync.com.controllers.ConfigResourceController;

public class TaskManager implements ITaskManageable {

	private static Logger logger = Logger.getLogger(TaskManager.class);

	private static final int ThreadNumber = 4;
	private static final int TimeOut = 3600;

	private File inputDirectory;
	private File outputDirectory;

	private static Task inputFileQueue;
	private static ITaskable outputFileManuallyCopied;
	private static Set<String> outputFilesSet;

	private ExecutorService threadPoolHashQueue;
	private ExecutorService threadPoolCreationDateQueue;
	private ExecutorService threadPoolFileCopyQueue;

	private HashTask hashQueue;
	private Task creationDateQueue;
	private Task fileCopyQueue;
	private long filesToProcess;

	public TaskManager(final File inputDir, final File outputDir) {
		this.inputDirectory = inputDir;
		this.outputDirectory = outputDir;
	}

	private static void getAllFilesFromInputDirectory(final File myDir) {
		for (File file : Arrays.asList(myDir.listFiles())) {
			if (!file.isFile()) {
				getAllFilesFromInputDirectory(file);
			} else {
				String iExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
				try {
					inputFileQueue.enqueueItemInCurrentQueue(MediaFileFactory.getInstance().getMediaFile(iExtension.toLowerCase(), file.getAbsolutePath()));
				} catch (Exception e) {
					logger.error(e.toString());
				}
			}
		}
	}

	private static void getAllFilesFromOutputDirectory(final File myDir) {
		if (myDir.exists()) {
			for (File file : Arrays.asList(myDir.listFiles())) {
				if (!file.isFile()) {
					getAllFilesFromOutputDirectory(file);
				} else {
					if (file.getName().matches("[A-Za-z0-9]{32}.*")) {
						outputFilesSet.add(file.getName());
					} else {
						String iExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
						try {
							outputFileManuallyCopied.enqueueItemInCurrentQueue(MediaFileFactory.getInstance().getMediaFile(iExtension.toLowerCase(), file.getAbsolutePath()));
						} catch (Exception e) {
							logger.error(e.toString());
						}
					}
				}
			}
		}
	}

	@Override
	public final void init() {

		// Get all files in input directory
		inputFileQueue = new FileListTask();
		getAllFilesFromInputDirectory(inputDirectory);
		logger.info("Files from input directory");

		// Get all files in output directory
		outputFilesSet = new HashSet<String>();
		getAllFilesFromOutputDirectory(outputDirectory);
		logger.info("Files from output directory");

		// number of files
		filesToProcess = inputFileQueue.getCurrentQueueSize();

		// Initialize singleton
		ConfigResourceController.init();

	}

	@Override
	public final void run() {

		logger.info("Hash task thread pool start");
		hashQueue = new HashTask(inputFileQueue);
		hashQueue.addFilesAlreadyDone(outputFilesSet);
		threadPoolHashQueue = Executors.newFixedThreadPool(ThreadNumber);
		CompletionService<Boolean> compServiceHash = new ExecutorCompletionService<Boolean>(threadPoolHashQueue);
		for (int i = 0; i < ThreadNumber; i++) {
			compServiceHash.submit(hashQueue, null);
		}

		logger.info("Creation date task thread pool start");
		creationDateQueue = new CreationDateTask(hashQueue);
		threadPoolCreationDateQueue = Executors.newFixedThreadPool(ThreadNumber);
		CompletionService<Boolean> compServiceCreationDate = new ExecutorCompletionService<Boolean>(threadPoolCreationDateQueue);
		for (int i = 0; i < ThreadNumber; i++) {
			compServiceCreationDate.submit(creationDateQueue, null);
		}

		logger.info("File copy task thread pool start");
		fileCopyQueue = new FileCopyTask(creationDateQueue, outputDirectory);
		threadPoolFileCopyQueue = Executors.newFixedThreadPool(ThreadNumber);
		CompletionService<Boolean> compServiceFileCopy = new ExecutorCompletionService<Boolean>(threadPoolFileCopyQueue);
		for (int i = 0; i < ThreadNumber; i++) {
			compServiceFileCopy.submit(fileCopyQueue, null);
		}

		threadPoolHashQueue.shutdown();
		threadPoolCreationDateQueue.shutdown();
		threadPoolFileCopyQueue.shutdown();

		try {
			logger.info("Wait for thread pools to finish");
			threadPoolHashQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolCreationDateQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolFileCopyQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e);
		}

		logger.debug("Queue Hash:" + hashQueue.getCurrentQueueSize());
		logger.debug("Exception queue Hash:" + hashQueue.getExceptionQueue().size());

		logger.debug("Queue Creation date:" + creationDateQueue.getCurrentQueueSize());
		logger.debug("Exception queue Creation date:" + creationDateQueue.getExceptionQueue().size());

		logger.debug("Queue File copy:" + fileCopyQueue.getCurrentQueueSize());
		logger.debug("Exception queue File copy:" + fileCopyQueue.getExceptionQueue().size());
	}

	@Override
	public final void stop() {
		logger.info("Abort all operations");
		
		threadPoolHashQueue.shutdownNow();
		threadPoolCreationDateQueue.shutdownNow();
		threadPoolFileCopyQueue.shutdownNow();

		try {
			logger.info("Wait for thread pools to finish after abort");
			threadPoolHashQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolCreationDateQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolFileCopyQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error(e);
		}

	}

	@Override
	public final Map<String, Long> getTaskProcessedStatistics() {
		Map<String, Long> taskProcessed = new HashMap<String, Long>();
		taskProcessed.put("FileListTask", inputFileQueue.getTaskProcessed());
		taskProcessed.put("HashTask", hashQueue.getTaskProcessed());
		taskProcessed.put("CreationDateTask", creationDateQueue.getTaskProcessed());
		taskProcessed.put("FileCopyTask", fileCopyQueue.getTaskProcessed());
		return taskProcessed;
	}

	public final ConcurrentLinkedQueue<MediaFile> getSynchronizedItemsQueue() {
		return fileCopyQueue.getQueue();
	}

	public final ConcurrentLinkedQueue<MediaFile> getExceptionItemsQueue() {
		ConcurrentLinkedQueue<MediaFile> exceptionQueue = new ConcurrentLinkedQueue<MediaFile>();
		for (MediaFile mediaFile : hashQueue.getExceptionQueue()) {
			exceptionQueue.add(mediaFile);
		}
		for (MediaFile mediaFile : creationDateQueue.getExceptionQueue()) {
			exceptionQueue.add(mediaFile);
		}
		for (MediaFile mediaFile : fileCopyQueue.getExceptionQueue()) {
			exceptionQueue.add(mediaFile);
		}
		return exceptionQueue;
	}

	@Override
	public final boolean hasTaskToProcess() {
		if (fileCopyQueue != null) {
		return fileCopyQueue.hasTaskToProcess();
		} else {
			return true;
		}
	}

	public final long getFilesToProcess() {
		return filesToProcess;
	}

	public final long getFilesStatus() {
		long total = 0;
		if (hashQueue != null) {
			total += hashQueue.getExceptionQueue().size();
		}
		if (creationDateQueue != null) {
			total += creationDateQueue.getExceptionQueue().size();
		}
		if (fileCopyQueue != null) {
			total += fileCopyQueue.getTaskProcessed() + fileCopyQueue.getExceptionQueue().size();
		}
		logger.info("Files processed : " + total + " / " + filesToProcess);
		return total;
	}
}

package photosync.wkr;

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

import photosync.com.controllers.ConfigResourceController;
import photosync.core.CreationDateTask;
import photosync.core.FileCopyTask;
import photosync.core.FileListTask;
import photosync.core.HashTask;
import photosync.core.ITaskable;
import photosync.core.MediaFile;
import photosync.core.MediaFileFactory;
import photosync.core.Task;

public class TaskManager implements ITaskManageable {

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

		// Get all files in output directory
		outputFilesSet = new HashSet<String>();
		getAllFilesFromOutputDirectory(outputDirectory);

		// number of files
		filesToProcess = inputFileQueue.getCurrentQueueSize();

		// Initialize singleton
		ConfigResourceController.init();

	}

	@Override
	public final void run() {

		hashQueue = new HashTask(inputFileQueue);
		hashQueue.addFilesAlreadyDone(outputFilesSet);
		threadPoolHashQueue = Executors.newFixedThreadPool(ThreadNumber);
		CompletionService<Boolean> compServiceHash = new ExecutorCompletionService<Boolean>(threadPoolHashQueue);
		for (int i = 0; i < ThreadNumber; i++) {
			compServiceHash.submit(hashQueue, null);
		}

		creationDateQueue = new CreationDateTask(hashQueue);
		threadPoolCreationDateQueue = Executors.newFixedThreadPool(ThreadNumber);
		CompletionService<Boolean> compServiceCreationDate = new ExecutorCompletionService<Boolean>(threadPoolCreationDateQueue);
		for (int i = 0; i < ThreadNumber; i++) {
			compServiceCreationDate.submit(creationDateQueue, null);
		}

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
			threadPoolHashQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolCreationDateQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolFileCopyQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Queue Hash:" + hashQueue.getCurrentQueueSize());
		System.out.println("Exception queue Hash:" + hashQueue.getExceptionQueue().size());
		System.out.println("Queue Creation date:" + creationDateQueue.getCurrentQueueSize());
		System.out.println("Exception queue Creation date:" + creationDateQueue.getExceptionQueue().size());
		System.out.println("Queue File copy:" + fileCopyQueue.getCurrentQueueSize());
		System.out.println("Exception queue File copy:" + fileCopyQueue.getExceptionQueue().size());
	}

	@Override
	public final void stop() {

		threadPoolHashQueue.shutdownNow();
		threadPoolCreationDateQueue.shutdownNow();
		threadPoolFileCopyQueue.shutdownNow();

		try {
			threadPoolHashQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolCreationDateQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
			threadPoolFileCopyQueue.awaitTermination(TimeOut, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		return total;
	}
}

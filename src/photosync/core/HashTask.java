package photosync.core;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


public class HashTask extends Task implements IComputable {

	private static Logger logger = Logger.getLogger(HashTask.class);

	public HashTask(final ITaskable inputFileQueue) {
		super(inputFileQueue);
	}

	private Set<String> filesAlreadyDone = new HashSet<String>();

	@Override
	public final void compute() {
		MediaFile file = dequeueItemFromPrecedingQueue();
		if (file != null) {
			logger.info("Computing hash task on file : " + file.getAbsolutePath());
			try {
				file.computeHash();
				if (!filesAlreadyDone.contains(file.getHash() + "." + file.getExtension())) {
					enqueueItemInCurrentQueue(file);
					filesAlreadyDone.add(file.getHash() + "." + file.getExtension());
					logger.debug("New file : " + file.getAbsolutePath());
				} else {
					exceptionQueue.add(file);
					logger.debug("File already done : " + file.getAbsolutePath());
				}
			} catch (IOException e) {
				exceptionQueue.add(file);
				logger.error(e.toString());
			}
		}
	}

	public final void addFilesAlreadyDone(final Collection<String> col) {
		filesAlreadyDone.addAll(col);
	}

}

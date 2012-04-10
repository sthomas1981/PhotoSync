package photosync.core;

import org.apache.log4j.Logger;

import photosync.com.exceptions.CreationDateNotFoundException;

public class CreationDateTask  extends Task implements IComputable {

	private static Logger logger = Logger.getLogger(CreationDateTask.class);

	public CreationDateTask(final Task qTask) {
		super(qTask);
	}

	@Override
	public final void compute() {
		MediaFile file = dequeueItemFromPrecedingQueue();
		if (file != null) {
			logger.info("Computing creation date task on file : " + file.getAbsolutePath());
			try {
				file.computeCreationDate();
				enqueueItemInCurrentQueue(file);
				logger.debug("Creation date done on file : " + file.getAbsolutePath());
			} catch (CreationDateNotFoundException e) {
				exceptionQueue.add(file);
				logger.error(e.toString());
			}
		}
	}

}

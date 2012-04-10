package photosync.core;

import org.apache.log4j.Logger;


public class FileListTask extends Task {

	private static Logger logger = Logger.getLogger(FileListTask.class);

	public FileListTask() {
		super(null);
	}

	protected FileListTask(final Task qTask) {
		super(qTask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public final MediaFile dequeueItemFromPrecedingQueue() {
		logger.debug("Queue size : " + getCurrentQueueSize());
		if (getCurrentQueueSize() > 0) {
			logger.info("Dequeue from current queue");
			return queue.remove();
		} else {
			return null;
		}
	}

	@Override
	public final boolean hasTaskToProcess() {
		return !queue.isEmpty();
	}

	@Override
	public void compute() {
	}
}

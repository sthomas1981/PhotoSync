package photosync.core;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.log4j.Logger;


public abstract class Task implements ITaskable, IComputable, Runnable {

	protected ConcurrentLinkedQueue<MediaFile> queue = new ConcurrentLinkedQueue<MediaFile>();
	protected ConcurrentLinkedQueue<MediaFile> exceptionQueue = new ConcurrentLinkedQueue<MediaFile>();

	private static Logger logger = Logger.getLogger(Task.class);

	protected ITaskable queueTask;

	protected int timeToWait = 1500;
	protected Long taskProcessed = (long) 0;

	protected Task(final ITaskable inputFileQueue) {
		this.queueTask = inputFileQueue;
	}


	@Override
	public final void enqueueItemInCurrentQueue(final MediaFile item) {
		logger.info("Enqueueing item " + item.getAbsolutePath());
		taskProcessed++;
		queue.add(item);
	}

	@Override
	public MediaFile dequeueItemFromPrecedingQueue() {
		logger.debug("Queue size: " + queueTask.getCurrentQueueSize());
		if (queueTask.getCurrentQueueSize() > 0) {
			logger.info("Dequeue from preceding task queue");
			return queueTask.dequeueItemFromCurrentQueue();
		} else {
			return null;
		}
	}

	@Override
	public final MediaFile dequeueItemFromCurrentQueue() {
		logger.info("Dequeue from current queue");
		return queue.poll();
	}

	public boolean hasTaskToProcess() {
		return queueTask.hasTaskToProcess() || (queueTask.getCurrentQueueSize() > 0);
	}

	public final int getCurrentQueueSize() {
		if (!queue.isEmpty()) {
			return queue.size();
		} else {
			return 0;
		}
	}

	public abstract void compute();

	public final void run() {
		while (hasTaskToProcess()) {

			logger.info("Run process");

			while (queueTask.getCurrentQueueSize() > 0) {
				compute();
			}

			try {
				Thread.sleep(timeToWait);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public final Long getTaskProcessed() {
		return taskProcessed;
	}

	public final ConcurrentLinkedQueue<MediaFile> getExceptionQueue() {
		return exceptionQueue;
	}

	public final ConcurrentLinkedQueue<MediaFile> getQueue() {
		return queue;
	}

}

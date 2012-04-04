package photosync.core;

import java.util.concurrent.ConcurrentLinkedQueue;


public class Task implements ITaskable, IComputable, Runnable {

	protected ConcurrentLinkedQueue<MediaFile> queue = new ConcurrentLinkedQueue<MediaFile>();
	protected ConcurrentLinkedQueue<MediaFile> exceptionQueue = new ConcurrentLinkedQueue<MediaFile>();

	protected ITaskable queueTask;

	protected int timeToWait = 1500;
	protected Long taskProcessed = (long) 0;

	protected Task(final ITaskable inputFileQueue) {
		this.queueTask = inputFileQueue;
	}


	@Override
	public final void enqueueItemInCurrentQueue(final MediaFile item) {
		System.out.println(getClass().getName() + "\t- Enqueueing item " + item.getAbsolutePath());
		taskProcessed++;
		queue.add(item);
	}

	@Override
	public MediaFile dequeueItemFromPrecedingQueue() {
		if (queueTask.getCurrentQueueSize() > 0) {
			System.out.println(getClass().getName() + "\t- Queue size: " + queueTask.getCurrentQueueSize());
			return queueTask.dequeueItemFromCurrentQueue();
		} else {
			return null;
		}
	}

	@Override
	public final MediaFile dequeueItemFromCurrentQueue() {
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


	public void compute() {
	}

	public final void run() {
		while (hasTaskToProcess()) {

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

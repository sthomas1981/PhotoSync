package photosync.core;


public class FileListTask extends Task {

	public FileListTask() {
		super(null);
	}

	protected FileListTask(final Task qTask) {
		super(qTask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public final MediaFile dequeueItemFromPrecedingQueue() {
		if (getCurrentQueueSize() > 0) {
			System.out.println(getClass().getName() + "\t- Queue size: " + getCurrentQueueSize());
			return queue.remove();
		} else {
			return null;
		}
	}

	@Override
	public final boolean hasTaskToProcess() {
		return !queue.isEmpty();
	}
}

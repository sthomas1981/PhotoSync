package photosync.core;


public interface ITaskable {

	MediaFile dequeueItemFromPrecedingQueue();

	MediaFile dequeueItemFromCurrentQueue();
	void enqueueItemInCurrentQueue(final MediaFile iFile);
	int getCurrentQueueSize();

	boolean hasTaskToProcess();
}

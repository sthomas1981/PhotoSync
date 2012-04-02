package photosync.core;

import photosync.com.exceptions.CreationDateNotFoundException;

public class CreationDateTask  extends Task implements IComputable {

	public CreationDateTask(final Task qTask) {
		super(qTask);
	}

	@Override
	public final void compute() {
		MediaFile file = dequeueItemFromPrecedingQueue();
		if (file != null) {
			System.out.println(getClass().getName() + "\t- File dequeued : " + file.getAbsolutePath());
			try {
				file.computeCreationDate();
				enqueueItemInCurrentQueue(file);
			} catch (CreationDateNotFoundException e) {
				exceptionQueue.add(file);
			}
		}
	}

}

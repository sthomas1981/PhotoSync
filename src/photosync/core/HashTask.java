package photosync.core;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


public class HashTask extends Task implements IComputable {

	public HashTask(final ITaskable inputFileQueue) {
		super(inputFileQueue);
	}

	private Set<String> filesAlreadyDone = new HashSet<String>();

	@Override
	public final void compute() {
		MediaFile file = dequeueItemFromPrecedingQueue();
		if (file != null) {
			System.out.println(getClass().getName() + "\t- File dequeued : " + file.getAbsolutePath());
			try {
				file.computeHash();
				if (!filesAlreadyDone.contains(file.getHash() + "." + file.getExtension())) {
					enqueueItemInCurrentQueue(file);
					filesAlreadyDone.add(file.getHash() + "." + file.getExtension());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public final void addFilesAlreadyDone(final Collection<String> col) {
		filesAlreadyDone.addAll(col);
	}

}

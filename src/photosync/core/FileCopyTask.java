package photosync.core;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import photosync.com.controllers.ConfigResourceController;

public class FileCopyTask extends Task implements IComputable {

	private File outputDirectory;
	private DateFormat dfm;

	private FileCopyTask(final Task qTask) {
		super(qTask);
	}

	public FileCopyTask(final Task qTask, final File outputDir) {
		super(qTask);
		this.outputDirectory = outputDir;
		try {
			this.dfm =  new SimpleDateFormat(ConfigResourceController.getInstance().getOutPutDirectoryFormat());
		} catch (Exception e) {
			this.dfm = new SimpleDateFormat("yyyy_MM");
		}
	}

	@Override
	public final void compute() {
		MediaFile file = dequeueItemFromPrecedingQueue();
		if (file != null) {
			System.out.println(getClass().getName() + "\t- File dequeued : " + file.getAbsolutePath());
			try {
				if (file.getHash() != null && file.getCreationDate() != null) {
					file.writeToFileSystem(outputDirectory.getAbsolutePath(), dfm);
					enqueueItemInCurrentQueue(file);
				}
			} catch (IOException e) {
				exceptionQueue.add(file);
			}
		}
	}
}

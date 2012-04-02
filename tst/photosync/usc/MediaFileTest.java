package photosync.usc;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFile;
import photosync.core.MediaFileFactory;

public class MediaFileTest {

	private static ConcurrentLinkedQueue<MediaFile> mediaFiles = new ConcurrentLinkedQueue<MediaFile>();

	private static void getAllFiles(final File myDir) {
		for (File file : Arrays.asList(myDir.listFiles())) {
			if (!file.isFile()) {
				getAllFiles(file);
			} else {
				String iExtension = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length());
				try {
					mediaFiles.add(MediaFileFactory.getInstance().getMediaFile(iExtension.toLowerCase(), file.getAbsolutePath()));
				} catch (Exception e) {
				}
			}
		}
	}

	@Before
	public final void setUp() throws Exception {
		File test = new File("pub");
	    getAllFiles(test);
	    FileUtils.deleteQuietly(new File("pub" + File.separator + "tmp"));
	}

	@After
	public final void tearDown() throws Exception {
	    FileUtils.deleteQuietly(new File("pub" + File.separator + "tmp"));
	}

	@Test
	public final void test() throws  CreationDateNotFoundException, IOException {
		while (!mediaFiles.isEmpty()) {
			MediaFile mediaFile = mediaFiles.remove();

			mediaFile.computeHash();
			mediaFile.computeCreationDate();

			assertNotNull(mediaFile.getHash());
			assertNotNull(mediaFile.getCreationDate());

			mediaFile.writeToFileSystem("pub" + File.separator + "tmp", new SimpleDateFormat("yyyy_MM"));

			System.out.println("File " + mediaFile.getAbsolutePath() + "\t" + mediaFile.getHash() + "\t" + mediaFile.getCreationDate().toString());
		}
	}

}

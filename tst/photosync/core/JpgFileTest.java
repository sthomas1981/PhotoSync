package photosync.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFileFactory;

public class JpgFileTest extends MediaFileTest {

	@Before
	public final void setUp() throws Exception {
		myMediaFile = MediaFileFactory.getInstance().getMediaFile(MediaFileFactory.EXTENSION_JPG, "pub/test.jpg");
	}

	@Test
	public final void testGetHash() throws IOException {
		myMediaFile.computeHash();
		assertEquals(myMediaFile.getHash(), "172445ef54dcd30943b7cb5aa6fdb038");
	}

	@Test
	public final void testGetCreationDate() throws CreationDateNotFoundException {
		myMediaFile.computeCreationDate();
		assertEquals(myMediaFile.getCreationDate().toString(), "Sat May 21 14:35:48 CEST 2011");
	}

}

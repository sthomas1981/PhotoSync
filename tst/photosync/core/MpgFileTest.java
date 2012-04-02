package photosync.core;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFileFactory;

public class MpgFileTest extends MediaFileTest {

	@Before
	public final void setUp() throws Exception {
		myMediaFile = MediaFileFactory.getInstance().getMediaFile(MediaFileFactory.EXTENSION_MPG, "pub/test.MPG");
	}

	@Test
	public final void testGetCreationDate() throws CreationDateNotFoundException {
		myMediaFile.computeCreationDate();
		assertEquals(myMediaFile.getCreationDate().toString(), "Sun Oct 26 08:15:06 CET 2008");
	}

	@Test
	public final void testGetHash() throws IOException {
		myMediaFile.computeHash();
		assertEquals(myMediaFile.getHash(), "a65c2557ef366faeabc5381129758acd");
	}

}

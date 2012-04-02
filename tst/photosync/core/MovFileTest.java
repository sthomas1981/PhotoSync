package photosync.core;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFileFactory;

public class MovFileTest extends MediaFileTest {

	@Before
	public final void setUp() throws Exception {
		myMediaFile = MediaFileFactory.getInstance().getMediaFile(MediaFileFactory.EXTENSION_MOV, "pub/test.mov");
	}

	@Test
	public final void testGetCreationDate() throws CreationDateNotFoundException {
		myMediaFile.computeCreationDate();
		assertEquals(myMediaFile.getCreationDate().toString(), "Mon Nov 03 02:38:37 CET 2008");
	}

	@Test
	public final void testGetHash() throws IOException {
		myMediaFile.computeHash();
		assertEquals(myMediaFile.getHash(), "3b4d99badb14892827194812c8110540");
	}

}

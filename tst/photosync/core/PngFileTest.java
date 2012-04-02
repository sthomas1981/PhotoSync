package photosync.core;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFileFactory;

public class PngFileTest extends MediaFileTest {

	@Before
	public final void setUp() throws Exception {
		myMediaFile = MediaFileFactory.getInstance().getMediaFile(MediaFileFactory.EXTENSION_PNG, "pub/test.png");
	}

	@Test
	public final void testGetHash() throws IOException {
		myMediaFile.computeHash();
		assertEquals(myMediaFile.getHash(), "515daa4a0d00c91d99deeffc464940da");
	}

	@Test
	public final void testGetCreationDate() throws CreationDateNotFoundException {
		myMediaFile.computeCreationDate();
		assertEquals(myMediaFile.getCreationDate().toString(), "Thu Feb 23 08:17:09 CET 2012");
	}

}

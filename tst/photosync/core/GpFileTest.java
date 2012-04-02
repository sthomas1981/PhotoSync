package photosync.core;


import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import photosync.com.exceptions.CreationDateNotFoundException;
import photosync.core.MediaFileFactory;

public class GpFileTest extends MediaFileTest {

	@Before
	public final void setUp() throws Exception {
		myMediaFile = MediaFileFactory.getInstance().getMediaFile(MediaFileFactory.EXTENSION_3GP, "pub/test.3gp");
	}

	@Test
	public final void testGetCreationDate() throws CreationDateNotFoundException {
		myMediaFile.computeCreationDate();
		assertEquals(myMediaFile.getCreationDate().toString(), "Wed Oct 05 09:13:38 CEST 2005");
	}

	@Test
	public final void testGetHash() throws IOException {
		myMediaFile.computeHash();
		assertEquals(myMediaFile.getHash(), "5b2744aeb17107218d02520ea4307ea8");
	}
}

package photosync.com;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.jdom.JDOMException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserConfigResourceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testSaveConfig() throws ParserConfigurationException, TransformerException, JDOMException, IOException {
		UserConfigResource test = new UserConfigResource("test.xml", new File("c:" + File.separator + "Documents"), new File("c:" + File.separator + "Documents" + File.separator + "test"));
		test.saveConfig();

		UserConfigResource test2 = new UserConfigResource("test.xml");
		assertEquals("c:" + File.separator + "Documents", test2.getInputDirectory().getAbsolutePath());
		assertEquals("c:" + File.separator + "Documents" + File.separator + "test", test2.getOutputDirectory().getAbsolutePath());
	}

	@Test
	public final void testDeleteConfig() throws JDOMException, IOException {
		UserConfigResource test = new UserConfigResource("config" + File.separator +"test.xml");
		test.deleteConfig();
		File testDeleted = new File("config" + File.separator + "test.xml");
		assertFalse(testDeleted.exists());
	}

}

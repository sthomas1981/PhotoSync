package photosync.usc;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import photosync.wkr.ITaskManageable;
import photosync.wkr.TaskManager;

public class TaskManagementTest {

	@Before
	public final void setUp() throws Exception {
		FileUtils.deleteQuietly(new File("pub_tmp"));
	}

	private Long taskBeforeProcess = (long) 53;
	private Long taskProcessed = (long) 52;
	private Long taskProcessedAfterRestart = (long) 0;

	@Test
	public final void test() {

		final ITaskManageable taskManagerTest = new TaskManager(
				new File("pub"),
				new File("pub_tmp"));
		taskManagerTest.init();
		taskManagerTest.run();

		Map<String, Long> statistics = taskManagerTest.getTaskProcessedStatistics();
		assertEquals(statistics.get("FileListTask"), taskBeforeProcess);
		assertEquals(statistics.get("HashTask"), taskProcessed);
		assertEquals(statistics.get("CreationDateTask"), taskProcessed);
		assertEquals(statistics.get("FileCopyTask"), taskProcessed);

		taskManagerTest.init();
		taskManagerTest.run();
		statistics = taskManagerTest.getTaskProcessedStatistics();
		assertEquals(statistics.get("FileListTask"), taskBeforeProcess);
		assertEquals(statistics.get("HashTask"), taskProcessedAfterRestart);
		assertEquals(statistics.get("CreationDateTask"), taskProcessedAfterRestart);
		assertEquals(statistics.get("FileCopyTask"), taskProcessedAfterRestart);
	}

	@After
	public final void tearDown() throws Exception {
	    FileUtils.deleteQuietly(new File("pub_tmp"));
	}

}

package photosync.core;

import java.util.Map;

public interface ITaskManageable {

	void init();
	void run();
	void stop();

	boolean hasTaskToProcess();

	Map<String, Long> getTaskProcessedStatistics();

}

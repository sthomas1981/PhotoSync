package photosync.wkr;

import java.util.Map;

public interface ITaskManageable {

	void init();
	void run();
	void stop();

	Map<String, Long> getTaskProcessedStatistics();

}

package photosync.wkr;

import java.util.Map;

public interface ITaskManageable {

	void init();
	void start();
	void stop();

	Map<String, Long> getTaskProcessedStatistics();

}

package photosync.wkr;

import java.util.Map;

public interface ITaskManageable {

	void init();
	void compare();
	void synchronize();
	void run();
	void stop();

	Map<String, Long> getTaskProcessedStatistics();

}

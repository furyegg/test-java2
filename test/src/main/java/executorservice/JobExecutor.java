package executorservice;

import java.util.List;

public interface JobExecutor {
	void addJobs(List<Runnable> jobs);
	void close();
}
package executorservice;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class JobExecutorImpl implements JobExecutor {
	
	private final ExecutorService executorService;
	
	public JobExecutorImpl() {
		BlockingQueue blockingQueue = new ArrayBlockingQueue(128);
		executorService = new ThreadPoolExecutor(5, 5, 1, TimeUnit.HOURS, blockingQueue);
	}
	
	@Override
	public void addJobs(List<Runnable> jobs) {
		jobs.forEach(job -> executorService.execute(job));
	}
	
	@Override
	public void close() {
		executorService.shutdown();
	}
}
package completablefuture;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ScheduleThreadPoolTest {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		final int[] count = {0};
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(
				() -> {
					System.out.println(new Date());
					++count[0];
				},
				0,
				2,
				TimeUnit.SECONDS);
		
		while (count[0] < 5) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		future.cancel(false);
		executor.shutdownNow();
	}
}
package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Test {
	public static void main(String[] args) {
		second();
	}
	
	private static void first() {
		CompletableFuture
				.runAsync(() -> {
					System.out.println("first: " + System.currentTimeMillis());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				})
				.thenRunAsync(() -> System.out.println("second: " + System.currentTimeMillis()));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private static void second() {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
		
		CompletableFuture<Integer> cf = new CompletableFuture<>();
		
		final int[] count = {0};
		
		ScheduledFuture<?> future = executor.scheduleAtFixedRate(
				() -> {
					count[0]++;
					System.out.println("count: " + count[0]);
					if (count[0] > 3) {
						// cf.complete(count[0]);
                        cf.completeExceptionally(new IllegalStateException("exceeded 3"));
					}
				},
				0,
				1,
				TimeUnit.SECONDS);
		
		System.out.println("start");
		
		cf.thenAccept(n -> {
			System.out.println("completed with count: " + count[0]);
			future.cancel(false);
			executor.shutdownNow();
		}).exceptionally((t) -> {
            System.out.println("error: " + t.toString());
            future.cancel(false);
            executor.shutdownNow();
            return null;
            // return 0;
        });
		
		System.out.println("end");
	}
}

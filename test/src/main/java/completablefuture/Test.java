package completablefuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

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
		
		CompletableFuture
				.runAsync(() -> {
					System.out.println("first: " + System.currentTimeMillis());
				}, executor)
				.thenRunAsync(() -> System.out.println("second: " + System.currentTimeMillis()));
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		executor.shutdownNow();
	}
}

package thread;

import com.google.common.collect.ImmutableList;
import one.util.streamex.StreamEx;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadPoolTest {
	public static void main(String[] args) throws InterruptedException {
		List<Task> tasks = ImmutableList.of(
				new Task(100),
				new Task(200),
				new Task(300),
				new Task(400),
				new Task(500),
				new Task(600),
				new Task(700),
				new Task(800),
				new Task(900)
//				new Task(1000),
//				new Task(1100),
//				new Task(1200)
		);
		ExecutorService executor = Executors.newWorkStealingPool(4);

//		List<? extends Future<?>> futures = StreamEx.of(tasks).map(t -> executor.submit(t)).toList();
//		futures.forEach(f -> {
//			try {
//				f.get();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			} catch (ExecutionException e) {
//				e.printStackTrace();
//			}
//		});
	}

	private static class Task implements Runnable {
		private int start;

		public Task(int start) {
			this.start = start;
		}

		@Override
		public void run() {
			try {
				PrintWriter out = new PrintWriter("C:/test/" + start + ".txt");
				for (int i = start; i < start + 10; ++i) {
					out.println(i);
					System.out.println(i);
				}
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
}
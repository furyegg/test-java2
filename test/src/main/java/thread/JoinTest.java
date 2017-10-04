package thread;

public class JoinTest {
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			int i = 0;
			while (i < 10) {
				System.out.println("t1: " + i);
				i += 1;
			}
		});
		
		Thread t2 = new Thread(() -> {
			int i = 0;
			while (i < 10) {
				System.out.println("t2: " + i);
				i += 1;
			}
		});
		
		t1.start();
		t2.start();
		t1.join();
		System.out.println("Finished 1...");
		t2.join();
		System.out.println("Finished 2...");
	}
}
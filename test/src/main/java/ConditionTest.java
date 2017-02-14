import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Ray Rui on 2/14/2017.
 */
public class ConditionTest implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(ConditionTest.class);

	public static ReentrantLock lock = new ReentrantLock();
	public static Condition condition = lock.newCondition();

	public void run() {
		try {
			log.info("sub thread lock");
			lock.lock();
			condition.await();
			System.out.println("Thread is going on");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		ConditionTest t = new ConditionTest();
		Thread thread = new Thread(t);
		thread.start();
		Thread.sleep(2000);

		log.info("main thread lock");
		lock.lock();
		condition.signal();
		lock.unlock();
	}
}

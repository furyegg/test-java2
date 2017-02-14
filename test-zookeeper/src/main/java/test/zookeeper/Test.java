package test.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class Test implements Watcher {
	private static CountDownLatch connectedSemaphore = new CountDownLatch(2);

	public static void main(String[] args) throws IOException {
		ZooKeeper zooKeeper = new ZooKeeper("localhost:2181", 5000, new Test());
		System.out.println(zooKeeper.getState());

		try {
			connectedSemaphore.await();
		} catch (InterruptedException e) {
			System.out.println("Zookeeper session established");
		}
	}

	public void process(WatchedEvent event) {
		System.out.println("received: " + event);
		if (Event.KeeperState.SyncConnected == event.getState()) {
			connectedSemaphore.countDown();
			System.out.println(connectedSemaphore.getCount());
		}
	}
}
package executorservice;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.util.List;

@Getter
@Builder
public class Job implements Runnable {
	
	private String jobName;
	private int duration;
	
	// builder test
	@Singular
	private List<String> suckers;
	
	@Override
	public void run() {
		try {
			Thread.sleep(duration * 1000);
			System.out.println(String.format("Job: %s executed: %d seconds", jobName, duration));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

package executorservice;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import lombok.Cleanup;

import java.util.List;
import java.util.Random;

public class Run {
	public static void main(String[] args) {
		@Cleanup
		JobExecutor jobExecutor = new JobExecutorImpl();
		Random random = new Random();
		
		// lombok builder test
		Job job = Job.builder().sucker("1").sucker("2").sucker("3").build();
		System.out.println("Suckers: " + Joiner.on(", ").join(job.getSuckers()));
		
		List<Runnable> jobs1 = Lists.newArrayList();
		for (int i = 0; i < 10; ++i) {
			jobs1.add(Job.builder().jobName("job" + i).duration(random.nextInt(10)).build());
		}
		jobExecutor.addJobs(jobs1);
		
		List<Runnable> jobs2 = Lists.newArrayList();
		for (int i = 10; i < 20; ++i) {
			jobs2.add(Job.builder().jobName("Job" + i).duration(random.nextInt(10)).build());
		}
		jobExecutor.addJobs(jobs2);
		
	}
}
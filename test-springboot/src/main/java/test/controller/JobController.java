package test.controller;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

	@Autowired
	private JobOperator jobOperator;

	@RequestMapping(value = "/job/start", method = RequestMethod.POST)
	public void start() throws JobParametersInvalidException, JobInstanceAlreadyExistsException, NoSuchJobException {
		long jobId = jobOperator.start("job1", "");
		System.out.println("JobId: " + jobId);
	}

}
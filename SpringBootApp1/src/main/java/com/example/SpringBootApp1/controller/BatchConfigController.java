package com.example.SpringBootApp1.controller;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BatchConfigController
{
    @Autowired
    Job job;

    @Autowired
    JobLauncher jobLauncher;

    @RequestMapping(path = "/SpringBatchDemo/convert_from_csv_database", method = RequestMethod.GET)
    ResponseEntity<BatchStatus> processCsvToDatabase() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException
    {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        /* Adding the JobId as parameter so that the Job can be run 2nd time as well. else the 2nd ron of job will fail with error 'Job Already Completed' */
        jobParametersBuilder.addString("JobId", String.valueOf(System.currentTimeMillis()));

        // Job Parameters preparations
        JobParameters jobParameters = jobParametersBuilder.toJobParameters();
        // Run the job using JobLaurence
        JobExecution jobExecution = jobLauncher.run(job, jobParameters);

        return ResponseEntity.ok(jobExecution.getStatus());
    }

}

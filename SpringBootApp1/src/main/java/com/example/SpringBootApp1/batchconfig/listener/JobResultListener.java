package com.example.SpringBootApp1.batchconfig.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobResultListener implements JobExecutionListener
{

    @Override
    public void beforeJob(JobExecution jobExecution)
    {
        System.out.println("Called beforeJob().");
    }

    @Override
    public void afterJob(JobExecution jobExecution)
    {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED)
        {
            log.info("SpringBatchJobCompletionListener - BATCH JOB COMPLETED SUCCESSFULLY");
        }
        else if (jobExecution.getStatus() == BatchStatus.FAILED)
        {
            log.info("SpringBatchJobCompletionListener - BATCH JOB FAILED");
        }
    }
}

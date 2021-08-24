package com.example.SpringBootApp1.scheduling;

import com.example.SpringBootApp1.model.Emp;
import com.example.SpringBootApp1.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
@EnableAsync
public class EmpAdditionScheduler
{
    @Autowired
    EmpService empService;
    /*
        fixedRate: This is used to run the scheduled jobs in every n milliseconds.
                   It does not matter whether the job has already finished its previous turn or not.

        fixedDelay: It is used to run the scheduled job sequentially with the given n milliseconds delay time between turns.
                    The task always waits until the previous one is finished.
                    Which means, the time spent on the job will affect the start time of the next run of scheduled job.
     */
    @Scheduled(fixedDelay = 600000, initialDelay = 1000)
    @Async("asyncExecutor")
    void printTimeStampUsingFixedDelay()
    {
        log.info("Current Thread Name inside printTimeStampUsingFixedDelay Scheduler : " + Thread.currentThread().getName());
        log.info(String.format("Current Date and Time using Scheduler fixedDelay is : " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
        addNewEmployee();
    }

    @Scheduled(fixedRateString = "${fixedRate.in.milliseconds}")
    void printTimeStampUsingFixedRate()
    {
        log.info(String.format("Current Date and Time using Scheduler fixedRateString is : " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        addNewEmployee();
    }

    /*
    It should be value="0 0/30 * * * ?"
    The field order of the cronExpression is
        1.Seconds
        2.Minutes
        3.Hours
        4.Day-of-Month
        5.Month
        6.Day-of-Week
        7.Year (optional field)

        Ensure you have at least 6 parameters, or you will get an error (year is optional)
        Graphically, the cron syntax for Quarz is (source):
        +-------------------- second (0 - 59)
        |  +----------------- minute (0 - 59)
        |  |  +-------------- hour (0 - 23)
        |  |  |  +----------- day of month (1 - 31)
        |  |  |  |  +-------- month (1 - 12)
        |  |  |  |  |  +----- day of week (0 - 6) (Sunday=0 or 7)
        |  |  |  |  |  |  +-- year [optional]
        |  |  |  |  |  |  |
        *  *  *  *  *  *  * command to be executed

        If you want to create a job for every 30 min, the below will be the cron pattern.
        0 0/30 * * * * ?
        0 0,30 * * * * ?
     */
    @Scheduled(cron = "${cron.pattern}")
    void triggerJobUsingCron()
    {
        log.info(String.format("Current Date and Time using Scheduler Cron is : " + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)));
        addNewEmployee();
    }

    @Async("asyncExecutor")
    @Transactional()
    void addNewEmployee()
    {
        log.info("Current Thread Name inside addNewEmployee Scheduler : " + Thread.currentThread().getName());
        Long empCount = empService.getEmpCount();
        long nextEmpId = empCount + 1;
        Emp e = new Emp();
        e.setFname(String.format("ABC%s", nextEmpId));
        e.setLname(String.format("XYZ%s", nextEmpId));
        e.setEmailId(e.getFname() + "@gmail.com");
        e.setSalary((double) Math.round(Math.random()*10000));
        empService.saveOrUpdateEmp(e);
    }
}

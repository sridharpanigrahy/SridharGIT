package com.example.SpringBootApp1.batchconfig;

import com.example.SpringBootApp1.batchconfig.listener.JobResultListener;
import com.example.SpringBootApp1.batchconfig.listener.StepResultListener;
import com.example.SpringBootApp1.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.BufferedReaderFactory;
import org.springframework.batch.item.file.DefaultBufferedReaderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.function.Predicate;

/*
    https://docs.spring.io/spring-batch/docs/current/reference/html/index-single.html
    https://examples.javacodegeeks.com/spring-batch-classifiercompositeitemwriter-example/
    https://www.javadevjournal.com/spring-batch/spring-batch-listeners/
    https://github.com/vdubois/spring-batch-samples/blob/master/spring-batch-sample-composite-writer/src/main/java/io/github/com/config/CompositeWriterConfiguration.java
 */
/*
    The Spring Batch provides the following control structure represented by a set of classes:

    JobRunner:
    Class responsible to make the execution of a job by external request. Has several implementations to provide method invocation call for different modes such as a shell script, for example. Performs the instantiation of a JobLauncher;

    JobLocator:
    Class responsible for getting the configuration information, such as the implementation plan (job script), for a given job passed by parameter. Works in conjunction with the JobRunner;

    JobLauncher: Class responsible for managing the start and manage the actual execution of the job, is instantiated by JobRunner;

    JobRepository: Facade class that interface the access of the framework classes to the tables of the repository, it is through this class that jobs communicate the progress of its executions, thus ensuring that it could make his restart;

    Components

    Tasklet: Basic unit of a step, can be created for the development of specific actions of the batch, like calling a webservice which data is to be used for all steps of the implementation, for example.

    ItemReader: Component used in a structure known as chunk, where we have a data source that is read, processed and written in an iterative fashion, into blocks – chunks – until all the data has been processed. This component is the logic of reading, that read sources such as databases. The framework comes with a set of pre-build readers, but the developer can also develop your own if necessary.

    ItemProcessor: Component used in a structure known as chunk, where we have a data source that is read, processed and written in an iterative fashion, into blocks – chunks – until all the data has been processed. This component is the processing logic, which typically consists of the execution of business rules, calls to external resources for enrichment of data, such as web services, among others.

    ItemWriter: Component used in a structure known as chunk, where we have a data source that is read, processed and written in an iterative fashion, into blocks – chunks – until all the data has been processed. This component is for the writte logic of the processed data, like with the ItemReaders, the framework also comes with a set of pre-build ItemWriters to write on sources such as databases, but the developer can also develop your own writer, if necessary.

    Decider: Component responsible for making use of logic to perform tasks like “go to the step 1 if a value equal to X, if equal to Y go to the step 2, and ends the execution if the value is equal to Z “.

    Classifier: Component that can be used in conjunction with other components, such as a ItemWriter and perform classification logic, such as “run ItemWriterA for the item if it has the property X = true, otherwise, execute the ItemWriterB “. IMPORTANT: IN THIS SCENARIO, THE ORDER OF EXECUTION OF THE ITEMS WITHIN THE CHUNK IS MODIFIED, BECAUSE THE FRAMEWORK  MAKES ALL THE CLASSIFICATION OF THE ITEMS FIRST, AND THEN EXECUTE 1 ITEMWRITER AT A TIME!

    Split: Component used when you want, at a certain point of execution, a set of steps to run in parallel through multithreading.

    ==================================================================================================================================================================================
    A batch process is typically encapsulated by a Job consisting of multiple Steps. Each Step typically has a single ItemReader, ItemProcessor, and ItemWriter.
    A Job is executed by a JobLauncher, and metadata about configured and executed jobs is stored in a JobRepository.

    Each Job may be associated with multiple JobInstances, each of which is defined uniquely by its particular JobParameters that are used to start a batch job.
    Each run of a JobInstance is referred to as a JobExecution. Each JobExecution typically tracks what happened during a run, such as current and exit statuses, start and end times, etc.

    A Step is an independent, specific phase of a batch Job, such that every Job is composed of one or more Steps.
    Similar to a Job, a Step has an individual StepExecution that represents a single attempt to execute a Step. StepExecution stores the information about current and exit statuses, start and end times, and so on, as well as references to its corresponding Step and JobExecution instances.

    An ExecutionContext is a set of key-value pairs containing information that is scoped to either StepExecution or JobExecution.
    Spring Batch persists the ExecutionContext, which helps in cases where you want to restart a batch run (e.g., when a fatal error has occurred, etc.). All that is needed is to put any object to be shared between steps into the context and the framework will take care of the rest. After restart, the values from the prior ExecutionContext are restored from the database and applied.

    JobRepository is the mechanism in Spring Batch that makes all this persistence possible. It provides CRUD operations for JobLauncher, Job, and Step instantiations.
    Once a Job is launched, a JobExecution is obtained from the repository and, during the course of execution, StepExecution and JobExecution instances are persisted to the repository.
 */

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfigCSVToDB
{
    @Value("input/inputData.csv")
    private String inputFilePath;  // Assigning the value using @Value annotation

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    DataSource dataSource;

    final private Predicate<String> IS_VALID_LINE= (line) ->
    {
        long charCount = line.chars().filter(c -> c==',').count();
        return charCount == 2? true: false;
    };

    @Autowired
    @Bean
    public Job readCSVFileJob(FlatFileItemReader<Person> personFlatFileItemReader  /* FlatFileItemReader will be injected  */ )
    {
        /*
            This tasklet has been created to validate the file.
            The file will be valid if the every line has 2 comma.
            If valid, update the execution context with parameter as valid_file as yes else no.
         */
        Tasklet fileValidationTasklet = (stepContribution, chunkContext) ->
        {
            /* Getting the Execution Context from JobExecution as JobExecution context will be valid till job completion. */
            ExecutionContext executionContext  = chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext();
            /* BufferedReaderFactory is interface and DefaultBufferedReaderFactory is class. */
            BufferedReaderFactory bufferedReaderFactory = new DefaultBufferedReaderFactory();
            /* For File path on system */
            // Resource r = new FileSystemResource(inputFilePath);
            Resource resource = new ClassPathResource(inputFilePath);
            try(BufferedReader bufferedReader = bufferedReaderFactory.create(resource, "UTF-8"))
            {
                boolean isValidFile = true;
                String line;
                while( (line = bufferedReader.readLine()) != null)
                {
                    isValidFile = isValidFile && IS_VALID_LINE.test(line);
                }
                if(isValidFile)
                {
                    executionContext.putString("VALID_FILE","YES");
                }
                else
                {
                    executionContext.putString("VALID_FILE","NO");
                }
                log.info("Is Valid File on fileValidationTasklet: " + executionContext.get("VALID_FILE"));
            }
            catch (IOException | ParseException exception)
            {
                log.error("Invalid File or Error reading in file" + exception.getMessage());
                executionContext.putString("VALID_FILE","NO");
            }
            return RepeatStatus.FINISHED;
        };


        JobExecutionDecider validFileExecutionDecider = (jobExecution, stepExecution) ->
        {
            ExecutionContext executionContext = jobExecution.getExecutionContext();
            log.info("Is Valid File : " + executionContext.get("VALID_FILE"));
            if("YES".equals(executionContext.get("VALID_FILE")))
                return new FlowExecutionStatus("VALID");
            else
                return new FlowExecutionStatus("NOT_VALID");
        };

        Step stepForValidation = stepBuilderFactory.get("CSVValidation").tasklet(fileValidationTasklet).build();

        // Building the Steps
        Step stepForCsvToDb = stepBuilderFactory
                .get("CsvToDbJobStep")
                .listener(new StepResultListener()) // Adding lister for Step. It is not compulsory.
                .<Person, Person>chunk(5) // Read and process in chunk of 5.
                .reader(personFlatFileItemReader) // Reader
                .processor(processor())// Processor
                .writer(personJdbcBatchItemWriter())  // personJdbcBatchItemWriter to write into database
                .writer(compositeWriter()) // Composite Writer to write on database and on console.
                .taskExecutor(new SimpleAsyncTaskExecutor("MyThread-"))
                .build();

        Tasklet endStep = (stepContribution, chunkContext) ->
        {
            log.info("Last Step is executed.");
            return RepeatStatus.FINISHED;
        };

        // Building the jobs.
        return jobBuilderFactory.get("CsvToDbJob").
                incrementer(new RunIdIncrementer())
                .listener(new JobResultListener())  // Adding lister for Job. It is not compulsory.
                .start(stepForValidation)           // File Validation and the validation status in Job Execution Context
                .next(validFileExecutionDecider)    // Valid File Decider which will decide the next flow
                .on("NOT_VALID").end()       // If Not Valid, then end the job
                .on("VALID")                // If VALID, then go to the next flow
                .to(stepForCsvToDb)

                .end().build();

    }

    @Bean
    FlatFileItemReader<Person> reader(LineMapper<Person> lineMapper)
    {
        FlatFileItemReader<Person> flatFileItemReader = new FlatFileItemReader<>();
        flatFileItemReader.setStrict(true);
        // Setting the File path as Resource.
        flatFileItemReader.setResource(new ClassPathResource(inputFilePath));
        // Setting the line to skip and line tokenizer
        flatFileItemReader.setLinesToSkip(1);
        // Setting the call back for Skipped lines.
        flatFileItemReader.setSkippedLinesCallback((line) ->
                {
                    log.info("The skipped line content is : " + line);
                });
        flatFileItemReader.setLineMapper(lineMapper);
        return flatFileItemReader;
    }

    @Bean
    public LineMapper<Person> lineMapper()
    {
        // Creating the line mapper
        DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<Person>();

        //Creating the Line tokenizer
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setNames(new String[] { "id", "firstName", "lastName" });
        lineTokenizer.setIncludedFields(new int[] { 0, 1, 2 });

        // Field Mapper
        BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<Person>();
        fieldSetMapper.setTargetType(Person.class);

        // Setting the line tokenizer and field mapper.
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }

    @Bean
    public ItemProcessor<Person, Person> processor()
    {
        /*
            As part of Item Processor, need to implement the process method.
            We have implemented using lambda expression since the ItemProcessor has one method.
         */
        return (inputPerson) ->
        {
            Person outputPerson = inputPerson;
            log.info(Thread.currentThread().getName() + " Thread using -  Inserting : " + outputPerson.toString());
            return outputPerson;
        };
    }

    @Bean
    public JdbcBatchItemWriter<Person> personJdbcBatchItemWriter()
    {
        JdbcBatchItemWriter<Person> itemWriter = new JdbcBatchItemWriter<Person>();
        itemWriter.setDataSource(dataSource);
        itemWriter.setSql("INSERT INTO Person (ID, FIRST_NAME, LAST_NAME) VALUES (:id, :firstName, :lastName)");

        // Set the parameter source provider.
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        return itemWriter;
    }

    /*
        Composite Writer is used to multiple writer destinations.
    */
    @Bean
    @StepScope
    public CompositeItemWriter<Person> compositeWriter()
    {
        CompositeItemWriter<Person> compositeItemWriter = new CompositeItemWriter<>();

        JdbcBatchItemWriter<Person> personJdbcBatchItemWriter = personJdbcBatchItemWriter();

        // Creating Customized Item Writer to print on console. We are providing the definition to write method.
        ItemWriter<Person> consoleItemWriter = (personList) ->
        {
            personList.stream().forEach(System.out::println);
        };

        compositeItemWriter.setDelegates(Arrays.asList(personJdbcBatchItemWriter, consoleItemWriter));
        return compositeItemWriter;
    }

}

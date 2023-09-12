package com.example.springbatch.config;
import com.example.springbatch.batch.UserProcessor;
import com.example.springbatch.batch.UserWriter;
import com.example.springbatch.model.User;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchConfig {


    private UserProcessor userProcessor;
    @Bean
    public Job userReaderJob(JobRepository jobRepository,
    PlatformTransactionManager tansactionManager){
        return new JobBuilder("userReadJob",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(chunkStep(jobRepository,tansactionManager))
                .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository,PlatformTransactionManager transactionManager){
        return new StepBuilder("bookReaderStep",jobRepository)
                .<User, User>chunk(10,transactionManager)
                .reader(reader())
                .processor(userProcessor)
                .writer(writer())
                .build();

    }
//    Index,User Id,First Name,Last Name,Sex,Email,Phone,Date of birth,Job Title
    @Bean
    @StepScope
    public FlatFileItemReader<User>reader(){
        return new FlatFileItemReaderBuilder<User>()
                .name("userReader")
                .linesToSkip(1)
                .resource(new ClassPathResource("peopleRecords.csv"))
                .lineMapper(lineMapper())
                .build();
    }

    private LineMapper<User> lineMapper() {
//        18:38
        DefaultLineMapper<User>lineMapper=new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer=new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("srNo","userId","firstName","lastName","sex","email","jobTitle");
        BeanWrapperFieldSetMapper<User>fieldSetMapper=new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }

    @Bean
    @StepScope
    public ItemWriter<User>writer(){
        return new UserWriter();
    }

}

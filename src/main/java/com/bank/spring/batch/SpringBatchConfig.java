package com.bank.spring.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.bank.spring.batch.dao.BankTransaction;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private ItemReader<BankTransaction> bankTrasactionItemReader;
	@Autowired
	private ItemWriter<BankTransaction> bankTrasactionItemWriter;
//	@Autowired
//	private ItemProcessor<BankTransaction, BankTransaction> bankTrasactionItemProcessor;

	@Bean
	public Job bankJob() {

		Step step1 = stepBuilderFactory.get("step-load-data").<BankTransaction, BankTransaction>chunk(100)
				.reader(bankTrasactionItemReader).processor(compositeItemProcessor()).writer(bankTrasactionItemWriter)
				.build();
		return jobBuilderFactory.get("bank-data-loader-job").start(step1).build();
	}

	@Bean
	public  ItemProcessor<BankTransaction, ? extends BankTransaction> compositeItemProcessor() {

		List<ItemProcessor<BankTransaction, BankTransaction>> itemProcessors = new ArrayList<>();
		itemProcessors.add(itemProcessor1());
		itemProcessors.add(itempProcessor2());

		CompositeItemProcessor<BankTransaction, BankTransaction> compositItemProcessor = new CompositeItemProcessor<>();
		compositItemProcessor.setDelegates(itemProcessors);
		return compositItemProcessor;
	}

	// remplace le @component annotation qui fait l'instanciation
	@Bean
	BankTransactionItemProcessor itemProcessor1() {
		return new BankTransactionItemProcessor();
	}

	@Bean
	public BankTransactionItemAnalyticsProcessor itempProcessor2() {
		return new BankTransactionItemAnalyticsProcessor();
	}

	@Bean
	public FlatFileItemReader<BankTransaction> fileItemReader(@Value("${inputFile}") Resource resource) {

		FlatFileItemReader<BankTransaction> flatFileItemReader = new FlatFileItemReader<BankTransaction>();
		flatFileItemReader.setName("FFIR1");
		flatFileItemReader.setLinesToSkip(1);
		flatFileItemReader.setResource(resource);
		flatFileItemReader.setLineMapper(lineMappe());
		return flatFileItemReader;
	}

	@Bean
	public LineMapper<BankTransaction> lineMappe() {
		DefaultLineMapper<BankTransaction> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer();
		delimitedLineTokenizer.setDelimiter(",");
		delimitedLineTokenizer.setStrict(false);
		delimitedLineTokenizer.setNames("id", "accoutId", "strTransactionDate", "transactioType", "amount");
		lineMapper.setLineTokenizer(delimitedLineTokenizer);
		BeanWrapperFieldSetMapper beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper();
		beanWrapperFieldSetMapper.setTargetType(BankTransaction.class);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);
		return lineMapper;
	}

}

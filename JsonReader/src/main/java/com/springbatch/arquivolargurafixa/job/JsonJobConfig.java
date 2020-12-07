package com.springbatch.arquivolargurafixa.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableBatchProcessing
@Configuration
public class JsonJobConfig {
	@Autowired
	public JobBuilderFactory jobBuilderFactory;
	
	@Bean
	public Job jsonJob(Step leituraJsonStep) {
		return jobBuilderFactory
				.get("jsonJob")
				.start(leituraJsonStep)
				//.incrementer(new RunIdIncrementer())
				.build();
	}
}

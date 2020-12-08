package com.springbatch.processadorclassifier.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class ProcessadorClassifierReaderConfig {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Bean
	@StepScope
	public FlatFileItemReader processadorClassifierReader(

			LineMapper lineMapper) {
		return new FlatFileItemReaderBuilder().name("processadorClassifierReader")
				.resource(new FileSystemResource("files/clientes.txt")).lineMapper(lineMapper).build();

	}

}

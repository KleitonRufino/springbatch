package com.example.springbatch.jobs;

import java.util.Arrays;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableBatchProcessing
//@Configuration
public class ParImparBatchConfig {

	@Autowired
	private JobBuilderFactory factory;
	@Autowired
	private StepBuilderFactory builder;

	@Bean
	public Job imprimeJob() {
		return factory.get("imprimeParImparJob").start(imprimeParImparStep()).incrementer(new RunIdIncrementer())
				.build();
	}

	private Step imprimeParImparStep() {
		return builder.get("imprimeParImparStep").<Integer, String>chunk(10).reader(contaAteDezReader())
				.processor(parOuImparProcessor()).writer(imprimeWriter()).build();
	}

	private ItemWriter<String> imprimeWriter() {
		return itens -> itens.forEach(System.out::println);
	}

	private ItemProcessor<Integer, String> parOuImparProcessor() {
		return new FunctionItemProcessor<Integer, String>(
				item -> item % 2 == 0 ? String.format("item %s é Par", item) : String.format("item %s é Impar", item));
	}

	private ItemReader<Integer> contaAteDezReader() {
		List<Integer> numerosDeUmADez = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		return new IteratorItemReader<Integer>(numerosDeUmADez);
	}
}
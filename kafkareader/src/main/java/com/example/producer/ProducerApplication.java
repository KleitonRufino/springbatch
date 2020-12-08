package com.example.producer;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.kafka.KafkaItemWriter;
import org.springframework.batch.item.kafka.builder.KafkaItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

import com.example.Customer;

@EnableBatchProcessing
@SpringBootApplication
public class ProducerApplication {

	public static void main(String args[]) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@Autowired
	private  JobBuilderFactory jobBuilderFactory;
	@Autowired
	private  StepBuilderFactory stepBuilderFactory;
	@Autowired
	private  KafkaTemplate<String, Customer> template;

	@Bean
	Job job() {
		return this.jobBuilderFactory
			.get("job")
			.start(start())
			.incrementer(new RunIdIncrementer())
			.build();
	}

	@Bean
	KafkaItemWriter<String, Customer> kafkaItemWriter() {
		return new KafkaItemWriterBuilder<String, Customer>()
			.kafkaTemplate(template)
			.itemKeyMapper(item -> String.valueOf(item.getId()))
			.build();
	}


	@Bean
	Step start() {

		var id = new AtomicLong();
		var reader = new ItemReader<Customer>() {

			@Override
			public Customer read() {

				if (id.incrementAndGet() < 10_1000)
					return new Customer(id.get(), Math.random() > .5 ? "Jane" : "Jose");

				return null;
			}
		};

		return this.stepBuilderFactory
			.get("s1")
			.<Customer, Customer>chunk(10)
			.reader(reader)
			.writer(kafkaItemWriter())
			.build();
	}

}

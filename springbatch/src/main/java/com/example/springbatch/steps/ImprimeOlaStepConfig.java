package com.example.springbatch.steps;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.springbatch.tasklets.ImprimeOlaTasklet;

@Configuration
public class ImprimeOlaStepConfig {

	@Autowired
	private StepBuilderFactory builder;

	
	@Bean
	public Step imprimeOlaStep(ImprimeOlaTasklet tasklet) {
		return builder.get("imprimeOlaStep").tasklet(tasklet).build();
	}
}

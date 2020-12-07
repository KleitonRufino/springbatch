package com.springbatch.arquivolargurafixa.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class LeituraArquivoJsonStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step leituraJsonStep(JsonItemReader<Cliente> leituraArquivoJsonReader,
			ItemWriter<Cliente> leituraJsonWriter) {
		return stepBuilderFactory
				.get("leituraJsonStep")
				.<Cliente, Cliente>chunk(1)
				.reader(leituraArquivoJsonReader)
				.writer(leituraJsonWriter)
				.build();
	}
}

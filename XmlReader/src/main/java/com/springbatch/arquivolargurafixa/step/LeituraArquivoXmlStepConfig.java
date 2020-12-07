package com.springbatch.arquivolargurafixa.step;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class LeituraArquivoXmlStepConfig {
	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public Step leituraXmlStep(StaxEventItemReader<Cliente> leituraArquivoXmlnReader,
			ItemWriter<Cliente> leituraXmlWriter) {
		return stepBuilderFactory
				.get("leituraXmlStep")
				.<Cliente, Cliente>chunk(1)
				.reader(leituraArquivoXmlnReader)
				.writer(leituraXmlWriter)
				.build();
	}
}

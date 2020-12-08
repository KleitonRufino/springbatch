package com.springbatch.processadorvalidacao.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.processadorvalidacao.dominio.Cliente;

@Configuration
public class ProcessadorValidacaoReaderConfig {
	@StepScope
	@Bean
	public FlatFileItemReader<Cliente> processadorValidacaoReader(
			) {
		return new FlatFileItemReaderBuilder<Cliente>()
				.name("processadorValidacaoReader")
				.resource(new FileSystemResource("files/clientes.txt"))
				.delimited()
				.names("nome", "idade", "email")
				.targetType(Cliente.class)
				.build();
	}
}

package com.springbatch.arquivodelimitado.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.arquivodelimitado.dominio.Cliente;

@Configuration
public class ArquivoDelimitadoWriterConfig {
	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> arquivoDelimitadoWriter() {
		return new FlatFileItemWriterBuilder<Cliente>().name("arquivoDelimitadoWriter")
				.resource(new FileSystemResource("files/clientes_res.txt")).delimited()
				.names("nome", "sobrenome", "idade", "email").build();
	}
}

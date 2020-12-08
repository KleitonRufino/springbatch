package com.springbatch.arquivolargurafixa.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class ArquivoLarguraFixaWriterConfig {
	
	@StepScope
	@Bean
	public FlatFileItemWriter<Cliente> escritaArquivoLarguraFixaWriter() {
		
		return new FlatFileItemWriterBuilder<Cliente>()
				.name("escritaArquivoLarguraFixaWriter")
				.resource(new FileSystemResource("files/clientes_res.txt"))
				.formatted()
				.format("%-9s %-9s %-2s %-19s")
				.names("nome", "sobrenome", "idade", "email")
				.build();
	}
}

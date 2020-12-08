package com.springbatch.arquivodelimitado.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.arquivodelimitado.dominio.Cliente;

/**
 * O job vai ler clientes e imprimí-los no console (mostrar o job config).
 * 
 * Mostrar o formato do arquivo de clientes.
 * 
 * Rodar aplicação com: arquivoClientes=file:files/clientes.txt
 */
@Configuration
public class ArquivoDelimitadoReaderConfig {
	@StepScope
	@Bean
	public FlatFileItemReader<Cliente> leituraArquivoDelimitadoReader() {
		return new FlatFileItemReaderBuilder<Cliente>()
                .name("leituraArquivoDelimitadoReader")
                .resource(new FileSystemResource("files/clientes.txt"))
                .delimited()
                .names(new String[] {"nome", "sobrenome", "idade", "email"})
                .targetType(Cliente.class)
                .build();
    }
}

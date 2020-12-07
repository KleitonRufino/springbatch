package com.springbatch.arquivolargurafixa.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class LeituraArquivoJsonReaderConfig {
	
	//run as configurarion params
		//arquivoClientes=file:files/clientes.json
	@StepScope
	@Bean
	public JsonItemReader<Cliente> leituraArquivoJsonReader(@Value("#{jobParameters['arquivoClientes']}")Resource resource) {
		return new JsonItemReaderBuilder<Cliente>()
                .jsonObjectReader(new JacksonJsonObjectReader<Cliente>(Cliente.class))
                .resource(resource)
                .name("leituraArquivoJsonReader")
                .build();
	}
}

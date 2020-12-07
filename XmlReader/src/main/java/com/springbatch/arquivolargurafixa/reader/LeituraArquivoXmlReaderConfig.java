package com.springbatch.arquivolargurafixa.reader;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.springbatch.arquivolargurafixa.dominio.Cliente;

@Configuration
public class LeituraArquivoXmlReaderConfig {
	
	//run as configurarion params
		//arquivoClientes=file:files/clientes.xml
	@StepScope
	@Bean
	public StaxEventItemReader <Cliente> leituraArquivoXmlReader() {
		return new StaxEventItemReaderBuilder<Cliente>()
                .resource(new FileSystemResource("files/clientes.xml"))
            	//.resource(resource)
				.addFragmentRootElements("cliente")
    			.unmarshaller(clienteMarshaller())
                .name("leituraArquivoXmlReader")
                .build();
	}
	
	@SuppressWarnings("rawtypes")
	@Bean
	public XStreamMarshaller clienteMarshaller() {
		Map<String, Class> aliases = new HashMap<>();
		aliases.put("cliente", Cliente.class);
		aliases.put("nome", String.class);
		aliases.put("sobrenome", String.class);
		aliases.put("idade", String.class);
		aliases.put("email", String.class);

		XStreamMarshaller marshaller = new XStreamMarshaller();

		marshaller.setAliases(aliases);

		return marshaller;
	}
}

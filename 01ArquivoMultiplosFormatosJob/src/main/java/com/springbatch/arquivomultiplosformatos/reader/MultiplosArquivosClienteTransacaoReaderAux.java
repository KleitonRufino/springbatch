package com.springbatch.arquivomultiplosformatos.reader;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultiplosArquivosClienteTransacaoReaderAux {

	
	//run as configurarion params
		//arquivoClientes=file:files/clientes*
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@StepScope
	@Bean
	public MultiResourceItemReader multiplosArquivosClienteTransacaoReader(@Value("#{jobParameters['arquivoClientes']}")Resource[] resources, FlatFileItemReader leituraArquivosMultiplosFormatosReader) {
		return new MultiResourceItemReaderBuilder<>()
				.name("multiplosArquivosClientesTransacaoReader")
				.resources(resources)
				.delegate(new ArquivoClienteTransacaoReader(leituraArquivosMultiplosFormatosReader))
				.build();
	}
	
}

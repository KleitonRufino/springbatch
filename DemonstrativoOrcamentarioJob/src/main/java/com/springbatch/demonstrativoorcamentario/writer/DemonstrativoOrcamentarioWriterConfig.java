package com.springbatch.demonstrativoorcamentario.writer;

import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileFooterCallback;
import org.springframework.batch.item.file.FlatFileHeaderCallback;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.MultiResourceItemWriter;
import org.springframework.batch.item.file.ResourceSuffixCreator;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.builder.MultiResourceItemWriterBuilder;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.springbatch.demonstrativoorcamentario.dominio.GrupoLancamento;
import com.springbatch.demonstrativoorcamentario.dominio.Lancamento;

@Configuration
public class DemonstrativoOrcamentarioWriterConfig {

	@StepScope
	@Bean
	public MultiResourceItemWriter<GrupoLancamento> multiDemonstrativoOrcamentarioWriter(FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter){
		return new MultiResourceItemWriterBuilder()
				.name("multiDemonstrativoOrcamentarioWriter")
				.resource(new FileSystemResource("files/demonstrativosOrcamentarios"))
				.delegate(demonstrativoOrcamentarioWriter)
				.resourceSuffixCreator(suffixCreator())
				.itemCountLimitPerResource(1)
				.build();
	}
	
	private ResourceSuffixCreator suffixCreator() {
			return new ResourceSuffixCreator() {
				
				@Override
				public String getSuffix(int index) {			
					return index + ".txt";
				}
			};
	}

	@StepScope
	@Bean
	public FlatFileItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter(DemonstrativoOrcamentarioRodape rodapeCallback) {
		return new FlatFileItemWriterBuilder<GrupoLancamento>().name("demonstrativoOrcamentarioWriter")
				.resource(new FileSystemResource("files/demonstrativoOrcamentario.txt"))
				.lineAggregator(lineAggregator())
				.headerCallback(headerCallback())
				.footerCallback(rodapeCallback)
				.build();
	}

	private FlatFileHeaderCallback headerCallback() {
		return new FlatFileHeaderCallback() {
			
			@Override
			public void writeHeader(Writer writer) throws IOException {
				writer.append(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s\n", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
				writer.append(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s\n", new SimpleDateFormat("HH:MM").format(new Date())));
				writer.append(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));
				writer.append(String.format("CODIGO NOME VALOR\n"));
				writer.append(String.format("\t Data Descricao Valor\n"));
				writer.append(String.format("----------------------------------------------------------------------------\n"));			
			}
		};
	}

	private LineAggregator<GrupoLancamento> lineAggregator() {
		return new LineAggregator<GrupoLancamento>() {
			@Override
			public String aggregate(GrupoLancamento grupoLancamento) {
				String formatGrupoLancamento = String.format("[%d] %s - %s\n", grupoLancamento.getCodigoNaturezaDespesa(),
						grupoLancamento.getDescricaoNaturezaDespesa(),
						NumberFormat.getCurrencyInstance().format(grupoLancamento.getTotal()));
				StringBuilder builder = new StringBuilder();
				for (Lancamento lancamento : grupoLancamento.getLancamentos()) {
					builder.append(String.format("\t [%s] %s - %s\n",
							new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
				}
				String formatLancamento = builder.toString();
				return formatGrupoLancamento + formatLancamento;
			}
		};
	}

//	@Bean
//	public ItemWriter<GrupoLancamento> demonstrativoOrcamentarioWriter() {
//		return itens -> {
//			System.out.println("\n");
//			System.out.println(String.format("SISTEMA INTEGRADO: XPTO \t\t\t\t DATA: %s", new SimpleDateFormat("dd/MM/yyyy").format(new Date())));
//			System.out.println(String.format("MÓDULO: ORÇAMENTO \t\t\t\t\t HORA: %s", new SimpleDateFormat("HH:MM").format(new Date())));
//			System.out.println(String.format("\t\t\tDEMONSTRATIVO ORCAMENTARIO"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//			System.out.println(String.format("CODIGO NOME VALOR"));
//			System.out.println(String.format("\t Data Descricao Valor"));
//			System.out.println(String.format("----------------------------------------------------------------------------"));
//			
//			Double totalGeral = 0.0;
//			for (GrupoLancamento grupo : itens) {
//				System.out.println(String.format("[%d] %s - %s", grupo.getCodigoNaturezaDespesa(),
//						grupo.getDescricaoNaturezaDespesa(),
//						NumberFormat.getCurrencyInstance().format(grupo.getTotal())));
//				totalGeral += grupo.getTotal();
//				for (Lancamento lancamento : grupo.getLancamentos()) {
//					System.out.println(String.format("\t [%s] %s - %s", new SimpleDateFormat("dd/MM/yyyy").format(lancamento.getData()), lancamento.getDescricao(),
//							NumberFormat.getCurrencyInstance().format(lancamento.getValor())));
//				}
//			}
//			System.out.println("\n");
//			System.out.println(String.format("\t\t\t\t\t\t\t  Total: %s", NumberFormat.getCurrencyInstance().format(totalGeral)));
//			System.out.println(String.format("\t\t\t\t\t\t\t  Código de Autenticação: %s", "fkyew6868fewjfhjjewf"));
//		};
//	}
}

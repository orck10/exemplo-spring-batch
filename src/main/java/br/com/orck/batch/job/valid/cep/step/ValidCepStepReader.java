package br.com.orck.batch.job.valid.cep.step;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import br.com.orck.batch.dto.ViacepResponse;

@Configuration
public class ValidCepStepReader{
	
	@Bean
	@StepScope
	public FlatFileItemReader<ViacepResponse> getValidCepStepReader(@Value("#{jobParameters['nomeArquivo']}") Resource fileResource) throws InternalError{
		String[] columnsArray = {"cep"};
		if(!fileResource.exists()) throw new InternalError("Arquivo não encontrado");
		return new FlatFileItemReaderBuilder<ViacepResponse>()
				.name("getValidCepStepReader")
				.resource(fileResource)
				.delimited()
				.names(columnsArray)
				.targetType(ViacepResponse.class)
				.build();
	}
}

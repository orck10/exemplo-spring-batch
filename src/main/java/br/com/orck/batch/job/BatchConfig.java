package br.com.orck.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.orck.batch.dto.ViacepResponse;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
	
	@Bean
	public Step getValidCepStep(StepBuilderFactory stepBuilderFactory, ItemReader<ViacepResponse> reader, ItemProcessor<ViacepResponse, ViacepResponse> processor, ItemWriter<ViacepResponse> writer) 
			{
		return stepBuilderFactory
				.get("getValidCepStep")
				.<ViacepResponse, ViacepResponse>chunk(10)
				.reader(reader)
				.processor(processor)
				.writer(writer)
				.build();
	}
	
	@Bean
	public Job getValidCepJob(JobBuilderFactory jobBuilderFactory, Step step){
		return jobBuilderFactory
				.get("getValidCepJob")
				.start(step)
				.build();
	}
	
	public ItemWriter<ViacepResponse> write(){
		return itens -> itens.forEach(System.out::println);
	}
}

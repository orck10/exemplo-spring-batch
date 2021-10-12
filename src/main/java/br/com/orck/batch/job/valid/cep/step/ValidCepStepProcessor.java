package br.com.orck.batch.job.valid.cep.step;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.batch.item.validator.Validator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.orck.batch.connections.Viacep;
import br.com.orck.batch.dao.LocalidadesDAO;
import br.com.orck.batch.dto.ViacepResponse;
import br.com.orck.batch.error.BadRequestError;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class ValidCepStepProcessor{
	
	@Bean
	public ItemProcessor<ViacepResponse, ViacepResponse> getProcessor(Viacep<ViacepResponse> viaCep, LocalidadesDAO dao){
		ValidatingItemProcessor<ViacepResponse> processor = new ValidatingItemProcessor<>();
		processor.setFilter(true);
		processor.setValidator(validator(viaCep, dao));
		return processor;
	}
	
	private Validator<ViacepResponse> validator(Viacep<ViacepResponse> viaCep, LocalidadesDAO dao){
		return new Validator<ViacepResponse>(){
			@Override
			public void validate(ViacepResponse value) throws ValidationException {
				if(dao.findIdByCep(value.getCep()).isPresent()) {
					log.warn("Cep já consta na base - : " + value.getCep());
					throw new ValidationException("Já consta na base");
				}
				try {
					ViacepResponse newViaCep = viaCep.getCepData(value.getCep());
					value.setAddress(newViaCep.getAddress());
					value.setCity(newViaCep.getCity());
					value.setCity_ibge(newViaCep.getCity_ibge());
					value.setDistrict(newViaCep.getDistrict());
					value.setDdd(newViaCep.getDdd());
					value.setState(newViaCep.getState());
					log.info(value.getCep() + " processado com sucesso");
				}catch (BadRequestError e) {
					log.warn("Cep não existe - : " + value.getCep());
					throw new ValidationException(e.getMessage());
				}catch (Exception e) {
					log.error(e.getMessage());
					throw e;
				}
			}
			
		};
		
	}
}

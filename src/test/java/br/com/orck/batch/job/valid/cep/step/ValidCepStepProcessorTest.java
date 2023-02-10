package br.com.orck.batch.job.valid.cep.step;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.orck.batch.connections.Viacep;
import br.com.orck.batch.dao.impl.LocalidadesDAOImpl;
import br.com.orck.batch.dto.ViacepResponse;
import br.com.orck.batch.error.BadRequestError;
import br.com.orck.batch.utils.MockUtils;

@RunWith(MockitoJUnitRunner.class)
public class ValidCepStepProcessorTest {
	
	@InjectMocks
	private ValidCepStepProcessor validCepStepProcessor;
	
	@Mock
	private Viacep<ViacepResponse> viaCep;
	
	@Mock
	private LocalidadesDAOImpl dao;
	
	@Test
	public void getProcessor_FOUND_IN_DATA_BASE_Test() throws Exception {
		var viacepResponse = MockUtils.getViacepResponse();
		when(dao.findIdByCep(viacepResponse.getCep())).thenReturn(Optional.of(Integer.parseInt("1")));
		
		var obj = validCepStepProcessor.getProcessor(viaCep, dao).process(viacepResponse);
		verify(dao, times(1)).findIdByCep(viacepResponse.getCep());
		assertNull(obj);
		
	}
	
	@Test
	public void getProcessor_SUCCESS_Test() throws Exception {
		var viacepResponse = MockUtils.getViacepResponse();
		when(dao.findIdByCep(viacepResponse.getCep())).thenReturn(Optional.empty());
		when(viaCep.getCepData(viacepResponse.getCep())).thenReturn(viacepResponse);
		
		var obj = validCepStepProcessor.getProcessor(viaCep, dao).process(viacepResponse);
		verify(dao, times(1)).findIdByCep(viacepResponse.getCep());
		assertEquals(viacepResponse, obj);
		
	}
	
	@Test
	public void getProcessor_CEP_NOT_FOUND_Test() throws Exception {
		var viacepResponse = MockUtils.getViacepResponse();
		when(dao.findIdByCep(viacepResponse.getCep())).thenReturn(Optional.empty());
		when(viaCep.getCepData(viacepResponse.getCep())).thenThrow(new BadRequestError("TESTE"));
		
		var obj = validCepStepProcessor.getProcessor(viaCep, dao).process(viacepResponse);
		verify(dao, times(1)).findIdByCep(viacepResponse.getCep());
		assertNull(obj);
		
	}
	
	@Test
	public void getProcessor_REST_REQUEST_ERROR_Test() throws Exception{
		var viacepResponse = MockUtils.getViacepResponse();
		when(dao.findIdByCep(viacepResponse.getCep())).thenReturn(Optional.empty());
		when(viaCep.getCepData(viacepResponse.getCep())).thenThrow(new NullPointerException());
		try {
			validCepStepProcessor.getProcessor(viaCep, dao).process(viacepResponse);
			fail();
		}catch (Exception e) {
			verify(dao, times(1)).findIdByCep(viacepResponse.getCep());
			assertEquals(NullPointerException.class, e.getClass());
		}
		
	}
	
}

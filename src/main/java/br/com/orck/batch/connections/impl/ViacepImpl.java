package br.com.orck.batch.connections.impl;

import java.util.Collections;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;

import br.com.orck.batch.connections.Viacep;
import br.com.orck.batch.dto.ViacepResponse;
import br.com.orck.batch.error.BadRequestError;
import br.com.orck.batch.error.InternalSeverError;
import lombok.extern.log4j.Log4j2;

@Qualifier("ViaCep")
@Log4j2
@Service
public class ViacepImpl implements Viacep<ViacepResponse>{
	
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${app.connection.viacep.get.url}")
	private String url;
	
	@Override
	public ViacepResponse getCepData(String cep) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(this.url.replace("?", cep));
		try {
			JSONObject response = new JSONObject(
	        		restTemplate.exchange(
	        				builder.toUriString(),
	        				HttpMethod.GET,
	        				this.buildHttpEntity(),
	        				String.class));
			return parseJson(response);
		}catch (BadRequestError e) {
			throw e;
		}catch (Exception e) {
			log.error("Erro na conexão com Viacep - Erro : " + e.getMessage());
			throw new InternalSeverError(e.getMessage());
		}
	}
	
	private HttpEntity<String> buildHttpEntity(){
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.ALL));
        return new HttpEntity<>(headers);
	}
	
	private ViacepResponse parseJson(JSONObject json) {
		return new Gson().fromJson(json.getString("body"), ViacepResponse.class); 
	}

}

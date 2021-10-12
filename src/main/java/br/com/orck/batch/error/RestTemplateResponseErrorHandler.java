package br.com.orck.batch.error;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler{
	
	public RestTemplateResponseErrorHandler() {
		super();
	}

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        return (response.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR || response.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		String e = new String(response.getBody().readAllBytes());
		if(response.getStatusCode().series() ==  HttpStatus.Series.SERVER_ERROR) {
			throw new InternalSeverError(new String(response.getBody().readAllBytes()));
		}else {
			log.error("Falha ao tentar acessar recurso - Body de status : " + response.getStatusText());
			log.error("Falha ao tentar acessar recurso - Body de resposta : " + e);
			throw new BadRequestError(e);
		}
	}

}

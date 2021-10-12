package br.com.orck.batch.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseBody
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestError extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestError(String message) {
        super(message);
    }
}

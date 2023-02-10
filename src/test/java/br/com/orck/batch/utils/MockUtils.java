package br.com.orck.batch.utils;

import java.util.UUID;

import br.com.orck.batch.dto.ViacepResponse;

public class MockUtils {
	
	private MockUtils() {}
	
	public static ViacepResponse getViacepResponse() {
		return new ViacepResponse(UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString(),
				UUID.randomUUID().toString()
		);
	}

}

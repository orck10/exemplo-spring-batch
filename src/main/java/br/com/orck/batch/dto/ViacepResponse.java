package br.com.orck.batch.dto;

import com.google.gson.Gson;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ViacepResponse {
	
	private String cep;
	private String address;
	private String state;
	private String district;
	private String city;
	private String city_ibge;
	private String ddd;
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}

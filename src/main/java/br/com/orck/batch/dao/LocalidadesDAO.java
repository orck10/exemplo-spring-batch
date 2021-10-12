package br.com.orck.batch.dao;

import java.util.Optional;

public interface LocalidadesDAO {
	Optional<Integer> findIdByCep(String cep);
}

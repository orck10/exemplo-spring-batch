package br.com.orck.batch.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.orck.batch.dao.LocalidadesDAO;
import br.com.orck.batch.enums.SQLEnums;

@Repository
public class LocalidadesDAOImpl implements LocalidadesDAO{
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private static final String TABLE = "localidades";
	private static final String COND_FIND_BY_CEP = "cep = {cep}";
	
	@Override
	@Transactional
	public Optional<Integer> findIdByCep(String cep) {
 		String cond = COND_FIND_BY_CEP.replace("{cep}", cep);
		String sql = SQLEnums.SIMPLE_FIND_WITH_WHERE.getSql()
				.replace("{val}", "id")
				.replace("{table}", TABLE)
				.replace("{cond}",cond);
		
		try {
			Integer resp = jdbcTemplate.queryForObject(sql, Integer.class);
			return resp == null ? Optional.empty() : Optional.of(resp);
		}catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}catch (Exception e) {
			throw e;
		}
		
	}

}

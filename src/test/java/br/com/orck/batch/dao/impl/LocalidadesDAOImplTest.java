package br.com.orck.batch.dao.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@RunWith(MockitoJUnitRunner.class)
public class LocalidadesDAOImplTest {
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	private LocalidadesDAOImpl localidadesDAOImpl;
	
	
	@Test
	public void findIdByCep_SUCCESS() {
		Integer i = Integer.parseInt("1");
		when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(i);
		
		var rowChanged = localidadesDAOImpl.findIdByCep(UUID.randomUUID().toString());
		assertNotNull(rowChanged.get());
		assertEquals(i , rowChanged.get());
	}
	
	@Test
	public void findIdByCep_NO_CHANGES() {
		when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenReturn(null);
		
		var rowChanged = localidadesDAOImpl.findIdByCep(UUID.randomUUID().toString());
		assertTrue(rowChanged.isEmpty());
	}
	
	@Test
	public void findIdByCep_EmptyResultDataAccessException() {
		when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenThrow(new EmptyResultDataAccessException("TESTE", 1));
		
		var rowChanged = localidadesDAOImpl.findIdByCep(UUID.randomUUID().toString());
		assertTrue(rowChanged.isEmpty());
	}
	
	@Test
	public void findIdByCep_OTHER_Exception() {
		when(jdbcTemplate.queryForObject(Mockito.anyString(), Mockito.eq(Integer.class))).thenThrow(new NullPointerException());
		
		try {
			localidadesDAOImpl.findIdByCep(UUID.randomUUID().toString());
			fail();
		}catch (Exception e) {
			assertEquals(NullPointerException.class, e.getClass());
		}
	}
}

package br.com.orck.batch.job.valid.cep.step;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.orck.batch.dto.ViacepResponse;
import br.com.orck.batch.utils.MockUtils;

@RunWith(MockitoJUnitRunner.class)
public class ValidCepStepWriterTest {
	
	
	@Mock
	private JdbcBatchItemWriterBuilder<ViacepResponse> builder;
	
	@Mock
	private JdbcBatchItemWriter<ViacepResponse>  writer;
	
	@Mock
	private DataSource dataSource;
	
	@Mock
	private PreparedStatement ps;
	
	@InjectMocks
	private ValidCepStepWriter validCepStepWriter;
	
	private final String sql = "INSERT INTO localidades (cep, logradouro, localidade, bairro, uf, ibge, ddd) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
	
	@Before
	public void setUp() {
		
		when(builder.dataSource(dataSource)).thenReturn(builder);
		when(builder.sql(sql)).thenReturn(builder);
		when(builder.itemPreparedStatementSetter(Mockito.any())).thenReturn(builder);
		when(builder.build()).thenReturn(writer);
	}
	
	@Test
	public void write_SUCCESS() {
		ReflectionTestUtils.setField(validCepStepWriter, "builder", builder);
		var writer = validCepStepWriter.write(dataSource);
		assertEquals(this.writer, writer);
		verify(builder, times(1)).sql(sql);
	}
	
	@Test
	public void preparedStatementSetterTest() throws SQLException {
		ReflectionTestUtils.setField(validCepStepWriter, "builder", builder);
		var prep = validCepStepWriter.preparedStatementSetter();
		var viaCep = MockUtils.getViacepResponse();
		prep.setValues(viaCep , ps);
		
		verify(ps, times(1)).setString(1, viaCep.getCep());
		verify(ps, times(1)).setString(Mockito.anyInt(), Mockito.eq(viaCep.getCep()));
		verify(ps, times(7)).setString(Mockito.anyInt(), Mockito.anyString());
	}
	
}

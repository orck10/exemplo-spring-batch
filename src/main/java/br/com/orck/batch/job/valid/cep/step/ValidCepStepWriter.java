package br.com.orck.batch.job.valid.cep.step;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.orck.batch.dto.ViacepResponse;
import br.com.orck.batch.enums.SQLEnums;

@Configuration
@Qualifier("ValidCepStepWriter")
public class ValidCepStepWriter {
	
	private static final String TABLE = "localidades";
	private static final String VAR = "cep, logradouro, localidade, bairro, uf, ibge, ddd";
	private static final String VAL = "?, ?, ?, ?, ?, ?, ?";
	
	@Bean
	public JdbcBatchItemWriter<ViacepResponse> write(DataSource dataSource){
		String sql = SQLEnums.SIMPLE_INSERT.getSql()
				.replace("{tab}", TABLE)
				.replace("{var}", VAR)
				.replace("{val}", VAL);
		return new JdbcBatchItemWriterBuilder<ViacepResponse>()
				.dataSource(dataSource)
				.sql(sql)
				.itemPreparedStatementSetter(preparedStatementSetter())
				.build();
	}

	private ItemPreparedStatementSetter<ViacepResponse> preparedStatementSetter() {
		return new ItemPreparedStatementSetter<ViacepResponse>() {

			@Override
			public void setValues(ViacepResponse item, PreparedStatement ps) throws SQLException {
				ps.setString(1, item.getCep());
				ps.setString(2, item.getAddress());
				ps.setString(3, item.getDistrict());
				ps.setString(4, item.getCity());
				ps.setString(5, item.getState());
				ps.setString(6, item.getCity_ibge());
				ps.setString(7, item.getDdd());
			}
		};
	}
}

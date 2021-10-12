package br.com.orck.batch.enums;

public enum SQLEnums {
	SIMPLE_INSERT("INSERT INTO {tab} ({var}) VALUES ({val})"),
	SIMPLE_FIND_WITH_WHERE("SELECT {val} FROM {table} WHERE {cond}");
	
	private String sql;
	
	SQLEnums(String sql){
		this.sql = sql;
	}
	
	public String getSql() {
		return sql;
	}
}

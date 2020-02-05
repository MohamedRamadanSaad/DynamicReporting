package com.giza.adaay.performance.reportEngine.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DBUtils {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	LoggerUtils loggerUtils;
	
	public Connection getConnection() {
		try {
			return jdbcTemplate.getDataSource().getConnection();
		} catch (SQLException e) {
			loggerUtils.logErrorAndThrowExceptionPrintStackTrace(this.getClass(), "Error in Retriving DB Connection " + e.getMessage(), e);
		}
		return null;
	}
}

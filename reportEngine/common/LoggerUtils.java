package com.giza.adaay.performance.reportEngine.common;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LoggerUtils {



	private Logger logger = null;

	

	public void logErrorMessage(Class cls, String msg) {
		setLoggerClass(cls);
		logger.error(msg);
	}

	public void logInfoMessage(Class cls, String msg) {
		setLoggerClass(cls);
		logger.info(msg);
	}

	public void logErrorAndThrowException(Class cls, String msg) {
		setLoggerClass(cls);
		logger.error(msg);
		throw new RuntimeException(msg);
	}
	public void logErrorAndThrowExceptionPrintStackTrace(Class cls, String msg,Exception e) {
		setLoggerClass(cls);
		logger.error(msg);
		e.printStackTrace();
		throw new RuntimeException(msg);
	}

	private void setLoggerClass(Class cls) {
		logger = LoggerFactory.getLogger(cls);
	}
}

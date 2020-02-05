package com.adaay.reporting.reportingmodule.dynamicReport.customAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnName {
	 public int id() default 0;
	 public String labelEN() default "";
	 public String labelAR() default "";
	 
	
}
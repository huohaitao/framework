package com.hht.annotation.config;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicate componenet-scan will be enabale
 * 
 * @author huoht
 * @date 2018.11.23
 * @version V1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Scan {
	
	/**
	 * same with basePackages
	 * @return
	 */
	String[] value() default {};

}

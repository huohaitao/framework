package com.hht.annotation.factory;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicate that the field or method args of marked by this annotation will be injectted by framework
 * 
 * if the value of the annotation not be setted framwork will inject value by type, otherwise it inject by name;
 * 
 * @author huoht
 * @date 2018Äê11ÔÂ23ÈÕ
 * @version V1.0
 */
@Documented
@Retention(RUNTIME)
@Target({FIELD, METHOD})
public @interface Inject {

	/**
	 * @see name of be injectted object 
	 * @return
	 */
	String value() default "";
	
	/**
	 * Is the injectted object required
	 * @return
	 */
	boolean required() default true;
	
}

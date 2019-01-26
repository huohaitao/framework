package com.hht.annotation.component;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicate the class that be marked be a controller component.
 * 
 * @author huoht
 * @date 2018Äê11ÔÂ23ÈÕ
 * @version V1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Control {

	String value() default "";
}

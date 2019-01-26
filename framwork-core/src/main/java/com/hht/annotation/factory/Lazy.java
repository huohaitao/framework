package com.hht.annotation.factory;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * indicate that bean will not be createed when context refresh, 
 * but if bean is needed immediately(Such as bean is need to be injectted to others or bean will be used), 
 * it will be createed.
 * @author huoht
 * @date 2018Äê12ÔÂ8ÈÕ
 * @version V1.0
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
public @interface Lazy {

	boolean value() default true;
}

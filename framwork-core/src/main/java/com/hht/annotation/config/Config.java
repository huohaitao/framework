package com.hht.annotation.config;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Indicate the class that be marked is a config class
 * The instance of the marked class will be destoried when context complete init; 
 * @author huoht
 * @date 2018Äê11ÔÂ23ÈÕ
 * @version V1.0
 */
@Documented
@Retention(RUNTIME)
@Target(ElementType.TYPE)
public @interface Config {

}

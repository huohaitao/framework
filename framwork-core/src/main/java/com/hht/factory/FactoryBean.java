package com.hht.factory;

public interface FactoryBean {

	BeanDefinition getDefiniton();
	
	Object get();
}

package com.hht.factory;

import java.util.Map;

public interface ListableBeanFactory extends BeanFactory,BeanDefinitionRegistry{

	boolean containsBean(String name);
	
	Map<String, Object> nameBeansMap();
}

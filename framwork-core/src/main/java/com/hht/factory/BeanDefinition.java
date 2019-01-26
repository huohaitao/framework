package com.hht.factory;

import java.util.List;

public interface BeanDefinition {
	
	String getBeanName();
	
	void setBeanName(String beanName);
	
	Class<?> getBeanClass();
	
	boolean isScopeSingle();
	
	boolean isLazy();
	
	List<InjectField> getInjectFields();
}

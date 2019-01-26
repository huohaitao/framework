package com.hht.context;

import com.hht.factory.BeanFactory;

public interface ApplicationContext {

	BeanFactory getBeanFactory();
	
	void refresh();
	
	<R> R getBean(Class<R> clazz);
	
	<R> R getBean(String name, Class<R> clazz);
	
	Object getBean(String name);
	
}

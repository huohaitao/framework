package com.hht.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.exception.ApplicationContextRefreshException;
import com.hht.factory.AbstractListableBeanFactory;
import com.hht.factory.BeanFactory;

public abstract class AbstractApplicationContext implements ApplicationContext{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected String id;
	
	protected AbstractListableBeanFactory beanFactory;
	
	@Override
	public BeanFactory getBeanFactory() {
		return beanFactory;
	}
	
	@Override
	public void refresh() {
		try {
			this.beanFactory = obtainBeanFactory();
			
			initBeanFactory();
		}catch (Exception e) {
			throw new ApplicationContextRefreshException("Application context refresh failed !", e);
		}
	}
	
	abstract AbstractListableBeanFactory obtainBeanFactory();
	
	abstract void initBeanFactory();
	
}

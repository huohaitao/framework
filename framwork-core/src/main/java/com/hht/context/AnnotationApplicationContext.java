package com.hht.context;

import java.util.List;

import com.hht.exception.ApplicationContextRefreshException;
import com.hht.factory.AbstractListableBeanFactory;
import com.hht.factory.BeanDefinition;
import com.hht.factory.annotation.AnnotationBeanDefinitionReader;
import com.hht.factory.annotation.AnnotationListableBeanFactory;

public class AnnotationApplicationContext extends AbstractApplicationContext{

	protected List<String> scanRoots;
	
	public AnnotationApplicationContext(List<String> scanRoots) {
		this.scanRoots = scanRoots;
	}

	@Override
	protected AbstractListableBeanFactory obtainBeanFactory() {
		if(this.scanRoots==null || this.scanRoots.isEmpty()) {
			throw new ApplicationContextRefreshException("Scan roots is not setted !");
		}
		logger.info("Startting load components with root packages:{}", String.join(",", this.scanRoots));
		AnnotationListableBeanFactory factory = new AnnotationListableBeanFactory();
		AnnotationBeanDefinitionReader reader = new AnnotationBeanDefinitionReader(this.scanRoots);
		List<BeanDefinition> bds = reader.read();
		for(BeanDefinition bd : bds) {
			factory.registDefinition(bd);
		}
		return factory;
	}


	@Override
	protected void initBeanFactory() {
		this.beanFactory.init();
	}

	@Override
	public <R> R getBean(Class<R> clazz) {
		return this.beanFactory.getBean(clazz);
	}

	@Override
	public <R> R getBean(String name, Class<R> clazz) {
		return this.beanFactory.getBean(name, clazz);
	}

	@Override
	public Object getBean(String name) {
		return this.beanFactory.getBean(name);
	}
	
}

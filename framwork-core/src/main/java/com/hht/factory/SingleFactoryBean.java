package com.hht.factory;

import java.util.concurrent.locks.ReentrantLock;

public class SingleFactoryBean extends AbstractFactoryBean{
	
	protected ReentrantLock lock = new ReentrantLock(true);
	
	protected Object bean;
	
	protected Object beanReference;
	
	public SingleFactoryBean() {}

	public SingleFactoryBean(AbstractListableBeanFactory factory, BeanDefinition definiton) {
		this.factory = factory;
		this.definiton = definiton;
	}
	
	public BeanDefinition getDefiniton() {
		return definiton;
	}

	public Object get() {
		if(this.bean != null) return this.bean;
		this.lock.lock();
		if(this.bean != null) return this.bean;
		this.initBean();
		this.lock.unlock();
		return this.bean;
	}
	
	protected void initBean() {
		this.bean = this.createBean();
	}

	@Override
	Object getReference() {
		if(this.bean == null) {
			this.bean = this.createReference();
		}
		return this.bean;
	}
}

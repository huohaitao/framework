package com.hht.factory;

public abstract class AbstractFactoryBean implements FactoryBean{
	
	protected BeanDefinition definiton;
	
	protected Object bean;
	
	protected AbstractListableBeanFactory factory;
	
	public AbstractFactoryBean() {}

	public AbstractFactoryBean(AbstractListableBeanFactory factory, BeanDefinition definiton) {
		this.factory = factory;
		this.definiton = definiton;
	}
	
	public BeanDefinition getDefiniton() {
		return definiton;
	}
	
	protected Object createBean() {
		return this.factory.assemble(this);
	}

	public abstract Object get();
	
	abstract Object getReference();
	
	Object createReference() {
		Class<?> beanClass =  this.definiton.getBeanClass();
		Object beanReference;
		try {
			beanReference = beanClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Instanting bean of class["+beanClass.getTypeName()+"] failed:", e);
		}
		return beanReference;
	}
}

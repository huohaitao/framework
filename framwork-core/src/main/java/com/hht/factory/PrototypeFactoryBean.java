package com.hht.factory;

public class PrototypeFactoryBean extends AbstractFactoryBean{
	
	public PrototypeFactoryBean() {}

	public PrototypeFactoryBean(AbstractListableBeanFactory factory, BeanDefinition definiton) {
		this.factory = factory;
		this.definiton = definiton;
	}
	
	public BeanDefinition getDefiniton() {
		return definiton;
	}

	public Object get() {
		return createBean();
	}

	@Override
	Object getReference() {
		return null;
	}

}

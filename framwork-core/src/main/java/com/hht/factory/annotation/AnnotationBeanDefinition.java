package com.hht.factory.annotation;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.hht.factory.BeanDefinition;
import com.hht.factory.InjectField;

public class AnnotationBeanDefinition implements BeanDefinition {
	
	private String beanName;
	
	private Class<?> beanClass;
	
	private boolean scopeSingle = true;
	
	private boolean lazy;
	
	private List<InjectField> injectFields;

	
	public AnnotationBeanDefinition(String beanName, Class<?> beanClass) {
		this.beanName = StringUtils.isBlank(beanName)?null:beanName;
		this.beanClass = beanClass;
	}

	@Override
	public String getBeanName() {
		return this.beanName;
	}

	@Override
	public Class<?> getBeanClass() {
		return this.beanClass;
	}

	@Override
	public boolean isScopeSingle() {
		return this.scopeSingle;
	}

	@Override
	public boolean isLazy() {
		return this.lazy;
	}

	public void setScopeSingle(boolean scopeSingle) {
		this.scopeSingle = scopeSingle;
	}

	public void setLazy(boolean lazyInit) {
		this.lazy = lazyInit;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	@Override
	public List<InjectField> getInjectFields() {
		return this.injectFields;
	}

	public void setBeanClass(Class<?> beanClass) {
		this.beanClass = beanClass;
	}

	public void setInjectFields(List<InjectField> injectFields) {
		this.injectFields = injectFields;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AnnotationBeanDefinition [");
		if (beanName != null) {
			builder.append("beanName=");
			builder.append(beanName);
			builder.append(", ");
		}
		if (beanClass != null) {
			builder.append("beanClass=");
			builder.append(beanClass);
			builder.append(", ");
		}
		builder.append("scopeSingle=");
		builder.append(scopeSingle);
		builder.append(", lazyInit=");
		builder.append(lazy);
		builder.append(", ");
		if (injectFields != null) {
			builder.append("injectFields=");
			builder.append(injectFields);
		}
		builder.append("]");
		return builder.toString();
	}
}

package com.hht.factory.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.hht.annotation.component.Component;
import com.hht.annotation.component.Control;
import com.hht.annotation.component.Service;
import com.hht.annotation.factory.Inject;
import com.hht.annotation.factory.Lazy;
import com.hht.annotation.factory.Scope;
import com.hht.annotation.factory.Scopes;
import com.hht.factory.BeanDefinition;
import com.hht.factory.InjectField;
import com.hht.util.ClassUtil;

public class AnnotationBeanDefinitionReader {
	
	private List<String> scanRoots;
	
	
	
	public AnnotationBeanDefinitionReader(List<String> scanRoots) {
		this.scanRoots = scanRoots;
	}

	public List<BeanDefinition> read(){
		List<BeanDefinition> bds = new ArrayList<>();
		List<Class<?>> classes = new ArrayList<>();
		for(String root : scanRoots) {
			ClassUtil.extractClasses(root, true, classes);
		}
		
		for(Class<?> c : classes) {
			BeanDefinition bd = parse(c);
			if(bd==null) continue;
			bds.add(bd);
		}
		return bds;
	}

	private AnnotationBeanDefinition parse(Class<?> c) {
		AnnotationBeanDefinition abd = null;
		Component can = c.getAnnotation(Component.class);
		if(can != null) {
			abd = assemblyDefinition(can.value(), c);
		}
		Service san = c.getAnnotation(Service.class);
		if(san != null) {
			abd = assemblyDefinition(san.value(), c);
		}
		Control ctan = c.getAnnotation(Control.class);
		if(ctan != null) {
			abd = assemblyDefinition(ctan.value(), c);
		}
		if(abd != null) {
			Lazy ly = c.getAnnotation(Lazy.class);
			boolean isLazy = ly==null ? false : ly.value();
			abd.setLazy(isLazy);
			
			Scope scope = c.getAnnotation(Scope.class);
			boolean single = scope==null?true:scope.value().equals(Scopes.SINGLE);
			abd.setScopeSingle(single);
		}
		return abd;
	}

	private AnnotationBeanDefinition assemblyDefinition(String beanName, Class<?> beanClass) {
		AnnotationBeanDefinition bd = new AnnotationBeanDefinition(beanName, beanClass);
		bd.setInjectFields(extractInjectFields(beanClass));
		return bd;
	}
	
	private List<InjectField> extractInjectFields(Class<?> c) {
		List<InjectField> infs = new ArrayList<>();
		List<Field> fs = ClassUtil.getFields(c, true);
		for(Field f : fs) {
			Inject in = f.getAnnotation(Inject.class);
			if(in==null) continue;
			f.setAccessible(true);
			infs.add(new InjectField(in.value(), f, in.required()));
		}
		return infs;
	}
}

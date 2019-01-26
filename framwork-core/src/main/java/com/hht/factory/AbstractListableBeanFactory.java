package com.hht.factory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.exception.RegistBeanDefinitionException;
import com.hht.util.FrameworkUtil;

public class AbstractListableBeanFactory implements BeanFactory, BeanDefinitionRegistry{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final int STATUS_UNINIT = 0;
	private final int STATUS_INITTING_BEAN = 1;
	private final int STATUS_INITED = 2;
	
	private Map<String, BeanDefinition> nameDefinitionRegistry = new HashMap<>();
	private Map<String, AbstractFactoryBean> nameBeanRegistry = new HashMap<>();
	private volatile int status = STATUS_UNINIT;
	
	@Override
	public void registDefinition(BeanDefinition bd) {
		String name = bd.getBeanName();
		if(StringUtils.isBlank(name)) {
			name = FrameworkUtil.getDefaultBeanName(bd);
		}
		BeanDefinition oldBd = this.nameDefinitionRegistry.get(name);
		if(oldBd != null) {
			Class<?> beanClass = bd.getBeanClass();
			String beanClassName = beanClass.getTypeName();
			throw new RegistBeanDefinitionException("The bean name ["+name+"] is used by muilty classes :["+oldBd.getBeanClass().getTypeName()+", "+beanClassName+"]");
		}
		bd.setBeanName(name);
		logger.debug("Regist bean definition[name:{}, type:{}]", bd.getBeanName(), bd.getBeanClass().getName());
		this.nameDefinitionRegistry.put(name, bd);
	}
	
	public void init() {
		this.changeStatus(STATUS_INITTING_BEAN);
		Set<Entry<String, BeanDefinition>> bds = this.nameDefinitionRegistry.entrySet();
		bds.forEach(e->{
			if(this.nameBeanRegistry.containsKey(e.getKey())) return;
			registBean(e.getValue());
		});
		this.changeStatus(STATUS_INITED);
	}
	
	private FactoryBean registBean(BeanDefinition bd) {
		AbstractFactoryBean fb = null;
		if(bd.isScopeSingle()) {
			fb = new SingleFactoryBean(this, bd);
		}else {
			fb = new PrototypeFactoryBean(this, bd);
		}
		this.nameBeanRegistry.put(bd.getBeanName(), fb);
		if(bd.isScopeSingle() && (!bd.isLazy())) {
			((SingleFactoryBean) fb).initBean();
		}
		return fb;
	}
	
	Object assemble(AbstractFactoryBean fb) {
		BeanDefinition bd = fb.getDefiniton();
		logger.debug("Start creating bean [{}]...", bd.getBeanName());
		Object beanReference = fb.getReference();
		List<InjectField> ifs = bd.getInjectFields();
		if(ifs!=null && (!ifs.isEmpty())) {
			for(InjectField ifd : ifs) {
				if(ifd.isByName()) {
					injectByName(bd, beanReference, ifd);
				}else {
					injectByType(bd, beanReference, ifd);
				}
			}
		}
		logger.debug("Complete creating bean [{}] !", bd.getBeanName());
		return beanReference;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> List<R> getBeans(Class<R> clazz) {
		List<FactoryBean> matcheBeans = getMatchedFactoryBeans(clazz, true);
		List<R> beans = new ArrayList<>(matcheBeans.size());
		for(FactoryBean fb : matcheBeans) {
			beans.add((R)fb.get());
		}
		return beans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R getBean(Class<R> clazz) {
		List<FactoryBean> matcheBeans = getMatchedFactoryBeans(clazz, false);
		
		if(matcheBeans.isEmpty()) return null;
		
		return (R)matcheBeans.get(0).get();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R> R getBean(String name, Class<R> clazz) {
		Object bean = this.getBean(name);
		if(bean != null && clazz.isInstance(bean)) {
			return (R)bean;
		}
		return null;
	}

	@Override
	public Object getBean(String name) {
		FactoryBean fb = this.nameBeanRegistry.get(name);
		if(fb != null) return fb.get();
		return null;
	}
	
	private <R> List<FactoryBean> getMatchedFactoryBeans(Class<R> clazz, boolean allowMulti) {
		List<FactoryBean> matcheBeans = new ArrayList<>();
		for(FactoryBean fb : nameBeanRegistry.values()) {
			if(!matchDefinition(clazz, fb.getDefiniton())) continue;
			matcheBeans.add(fb);
			
			if(allowMulti) continue;
			
			if(matcheBeans.size() > 1) {
				throw new RuntimeException("Multi bean found for type : "+clazz.getTypeName());
			}
		}
		return matcheBeans;
	}
	
	private boolean matchDefinition(Class<?> clazz, BeanDefinition bd) {
		return clazz.isAssignableFrom(bd.getBeanClass());
	}
	
	private void injectByType(BeanDefinition bd, Object instance, InjectField ifd) {
		Field f = ifd.getField();
		Class<?> ft = f.getType();
		String beanTypeName = bd.getBeanClass().getTypeName();
		if(bd.isScopeSingle() && ft.getTypeName().equals(beanTypeName)){
			throw new RuntimeException("Inject self type is not allowed ! when inject by type and tagrget bean is singleton -> class["+beanTypeName+"] field["+f.getName()+"]");
		}
		BeanDefinition fbd = findMatchedDefinition(instance, ifd);
		if(fbd == null && ifd.isRequired()) {
			throw new RuntimeException("Bean definition [name : "+ifd.getDependName()+", type : "+f.getType().getTypeName()+"] not found ! When injectting field["+f.getName()+"] of class["+bd.getBeanClass().getTypeName()+"]");
		}
		Object v =  getInjectValue(ft, fbd);
		injectValue(instance, f, v);
	}

	private BeanDefinition findMatchedDefinition(Object bean, InjectField ifd) {
		Field f = ifd.getField();
		Class<?> ft = f.getType();
		List<BeanDefinition> matchBDs = new ArrayList<>();
		for(BeanDefinition bd : this.nameDefinitionRegistry.values()) {
			if(!matchDefinition(ft, bd)) continue;
			matchBDs.add(bd);
		}
		BeanDefinition target = null;
		if(matchBDs.size()>1) {
			if(ifd.getDependName() != null) {
				for(BeanDefinition mbd : matchBDs) {
					if(mbd.getBeanName().equals(ifd.getDependName())) {
						target = mbd;
						break;
					}
				}
			}
			if(target == null) throw new RuntimeException("Multi implements found for field [name:"+f.getName()+", type:"+ft.getTypeName()+"] in class["+bean.getClass().getTypeName()+"]");
		}else if(matchBDs.size() == 1){
			target = matchBDs.get(0);
		}
		return target;
	}
	
	private void injectByName(BeanDefinition bd, Object bean, InjectField ifd) {
		Field f = ifd.getField();
		Object v = this.getBean(ifd.getDependName());
		if(v == null) {
			BeanDefinition fbd = nameDefinitionRegistry.get(ifd.getDependName());
			if(fbd == null && ifd.isRequired())
				throw new RuntimeException("Bean definition [name : "+ifd.getDependName()+", type : "+f.getType().getTypeName()+"] not found ! When injectting field["+f.getName()+"] of class["+bd.getBeanClass().getTypeName()+"]");
			if(fbd != null) {
				v = this.registBean(fbd).get();
			}
		}
		injectValue(bean, f, v);
	}
	
	private Object getInjectValue(Class<?> fieldType, BeanDefinition fieldBeanDefinition) {
		Object v = null;
		if(fieldBeanDefinition != null) {
			v = this.getBean(fieldType);
			if(v == null) v = this.registBean(fieldBeanDefinition).get();
		}
		return v;
	}

	private void injectValue(Object instance, Field f, Object v) {
		if(v == null) return;
		try {
			f.set(instance, v);
		} catch (Exception e) {
			throw new RuntimeException("Inject field["+f.getName()+"] of class["+instance.getClass().getTypeName()+"] failed : ", e);
		}
	}
	
	private void changeStatus(int status) {
		this.status = status;
	}
	
	public boolean isInittingBean() {
		return this.status == STATUS_INITTING_BEAN;
	}
	
	public boolean isInitted() {
		return this.status == STATUS_INITED;
	}
}

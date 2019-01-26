package com.hht.factory;

import java.util.List;

public interface BeanFactory {

	/**
	 * ��ȡָ�����͵�bean
	 * �����ڶ��ͬ���͵�beanʱ�׳��쳣
	 * @param clazz
	 * @return
	 */
	<R> R getBean(Class<R> clazz);
	/**
	 * ��ȡָ�����͵�����beanʵ��
	 * @param clazz
	 * @return
	 */
	<R> List<R> getBeans(Class<R> clazz);
	
	/**
	 * ����ָ�����ͺ����ֻ�ȡbeanʵ��
	 * @param name
	 * @param clazz
	 * @return
	 */
	<R> R getBean(String name, Class<R> clazz);
	
	/**
	 * ����bean���ֻ�ȡbeanʵ��
	 * @param name
	 * @return
	 */
	Object getBean(String name);
}

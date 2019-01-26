package com.hht.factory;

import java.util.List;

public interface BeanFactory {

	/**
	 * 获取指定类型的bean
	 * 当存在多个同类型的bean时抛出异常
	 * @param clazz
	 * @return
	 */
	<R> R getBean(Class<R> clazz);
	/**
	 * 获取指定类型的所有bean实例
	 * @param clazz
	 * @return
	 */
	<R> List<R> getBeans(Class<R> clazz);
	
	/**
	 * 根据指定类型和名字获取bean实例
	 * @param name
	 * @param clazz
	 * @return
	 */
	<R> R getBean(String name, Class<R> clazz);
	
	/**
	 * 根据bean名字获取bean实例
	 * @param name
	 * @return
	 */
	Object getBean(String name);
}

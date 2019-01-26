package com.hht.util;

import org.apache.commons.lang3.StringUtils;

import com.hht.factory.BeanDefinition;

public class FrameworkUtil {

	/**
	 * 获取bean定义 默认名称
	 * @param bd
	 * @return
	 */
	public static String getDefaultBeanName(BeanDefinition bd) {
		Class<?> beanClass = bd.getBeanClass();
		return StringUtils.uncapitalize(beanClass.getSimpleName());
	}
	
}

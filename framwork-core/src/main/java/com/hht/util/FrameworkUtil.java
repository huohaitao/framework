package com.hht.util;

import org.apache.commons.lang3.StringUtils;

import com.hht.factory.BeanDefinition;

public class FrameworkUtil {

	/**
	 * ��ȡbean���� Ĭ������
	 * @param bd
	 * @return
	 */
	public static String getDefaultBeanName(BeanDefinition bd) {
		Class<?> beanClass = bd.getBeanClass();
		return StringUtils.uncapitalize(beanClass.getSimpleName());
	}
	
}

package com.hht.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class ClassUtil {

	/**
	 * �Ӹ�����root���»�ȡ��
	 * @param rootPackage
	 * @param recursionChild �Ƿ�ݹ��Ӱ�
	 * @return
	 */
	public static List<Class<?>> getClasses(String rootPackage, boolean recursionChild){
			List<Class<?>> classes = new ArrayList<>();
			extractClasses(rootPackage, recursionChild, classes);
			return classes;
	}
	
	/**
	 * ��ָ����root���µ�����Ϣ���ص�ָ����list��
	 * @param rootPackage
	 * @param recursionChild
	 * @param container
	 */
	public static void extractClasses(String rootPackage, boolean recursionChild, List<Class<?>> container){
		try {
			ClassLoader loader = ClassUtil.class.getClassLoader();
			
			String absClassPath = loader.getResource("").getPath();
			String packagePath = StringUtils.replace(rootPackage, ".", File.separator);
			List<File> classFiles = new ArrayList<>();
			Files.extractFiles(absClassPath+packagePath, recursionChild, f->(f.isFile() && f.getName().endsWith(".class")), classFiles);
			for(File f : classFiles) {
				String className = transformToClassName(packagePath, f.getPath());
				Class<?> clazz = loader.loadClass(className);
				container.add(clazz);
			}
		}catch (Exception e) {
			throw new RuntimeException("������ʱ�����쳣��", e);
		}
		
	}
	
	/**
	 * ��ȡָ��class�е������ֶ���Ϣ
	 * @param clazz
	 * @param includeSuper �Ƿ���������е��ֶ�
	 * @return
	 */
	public static List<Field> getFields(Class<?> clazz, boolean includeSuper) {
		List<Field> orgFields = new ArrayList<>();
		orgFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
		if(includeSuper) {
			while(true) {
				clazz = clazz.getSuperclass();
				if(clazz==null) break;
				orgFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
			}
		}
		return orgFields;
	}

	private static String transformToClassName(String packagePath, String path) {
		path = path.substring(path.indexOf(packagePath));
		path = StringUtils.replace(path, File.separator, ".");
		String className = StringUtils.substringBefore(path, ".class");
		return className;
	}
}

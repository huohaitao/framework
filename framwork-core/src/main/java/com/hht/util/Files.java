package com.hht.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Files {

	/**
	 * �г�ָ��Ŀ¼����ļ�����Ŀ¼
	 * @param path ��Ŀ¼
	 * @param recursionChild �Ƿ�ݹ��Ӽ�Ŀ¼
	 * @return
	 */
	public static List<File> listFiles(String path, boolean recursionChild){
		List<File> collector = new ArrayList<>();
		listFiles0(path, recursionChild, collector);
		return collector;
	}
	
	/**
	 * �г�ָ��Ŀ¼����ļ�����Ŀ¼
	 * @param path ��Ŀ¼
	 * @param recursionChild �Ƿ�ݹ��Ӽ�Ŀ¼
	 * @param filter ������(����true��ʾ��Ҫ��file)
	 * @return
	 */
	public static List<File> listFiles(String path, boolean recursionChild, Predicate<File> filter){
		List<File> collector = new ArrayList<>();
		if(filter==null) {
			listFiles0(path, recursionChild, collector);
		}else {
			listFilterFiles(path, recursionChild, filter, collector);
		}
		return collector;
	}
	
	/**
	 * ���ļ���ȡ��collector��
	 * @param path
	 * @param recursionChild
	 * @param filter
	 * @param collector
	 */
	public static void extractFiles(String path, boolean recursionChild, Predicate<File> filter, List<File> collector){
		if(filter==null) {
			listFiles0(path, recursionChild, collector);
		}else {
			listFilterFiles(path, recursionChild, filter, collector);
		}
	}
	
	private static void listFiles0(String path, boolean recursionChild, List<File> collector){
		File fs = new File(path);
		File[] cfs = fs.listFiles();
		for(File f : cfs) {
			collector.add(f);
			if(recursionChild && f.isDirectory()) {
				listFiles0(f.getPath(), true, collector);
			}
		}
	}
	
	private static void listFilterFiles(String path, boolean recursionChild, Predicate<File> filter, List<File> collector){
		File fs = new File(path);
		File[] cfs = fs.listFiles();
		for(File f : cfs) {
			if(filter.test(f)) {
				collector.add(f);
			}
			if(recursionChild && f.isDirectory()) {
				listFilterFiles(f.getPath(), true, filter, collector);
			}
		}
	}
}

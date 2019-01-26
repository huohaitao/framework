package com.hht.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Files {

	/**
	 * 列出指定目录里的文件或者目录
	 * @param path 根目录
	 * @param recursionChild 是否递归子级目录
	 * @return
	 */
	public static List<File> listFiles(String path, boolean recursionChild){
		List<File> collector = new ArrayList<>();
		listFiles0(path, recursionChild, collector);
		return collector;
	}
	
	/**
	 * 列出指定目录里的文件或者目录
	 * @param path 根目录
	 * @param recursionChild 是否递归子级目录
	 * @param filter 过滤器(返回true表示需要的file)
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
	 * 将文件提取到collector中
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

package com.hht.loader;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.context.AbstractApplicationContext;
import com.hht.context.AnnotationApplicationContext;
import com.hht.context.ApplicationContext;
import com.hht.scanner.ConfigScanner;

public class AnnotationContextLoader {

	static Logger logger = LoggerFactory.getLogger(AnnotationContextLoader.class);
	
	public static ApplicationContext load(Class<?> mainClass) {
		Package pk = mainClass.getPackage();
		ConfigScanner cs = new ConfigScanner();
		List<String> scanRoots = cs.scanForRoots(pk.getName());
		AbstractApplicationContext aac = new AnnotationApplicationContext(scanRoots);
		aac.refresh();
		return aac;
	}
}

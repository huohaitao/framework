package com.hht.scanner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hht.annotation.config.Scan;
import com.hht.util.ClassUtil;

public class ConfigScanner {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * …®√Ë¬∑æ∂≈‰÷√
	 * @param basePackage
	 * @return
	 */
	public List<String> scanForRoots(String basePackage) {
		List<Class<?>> classes = ClassUtil.getClasses(basePackage, false);
		List<String> scanRoots = new ArrayList<>();
		for(Class<?> c : classes) {
			Scan s = c.getAnnotation(Scan.class);
			if(s == null) continue;
			scanRoots.addAll(Arrays.asList(s.value()));
		}
		
		if(scanRoots.isEmpty()) {
			scanRoots.add(basePackage);
		}
		
		return scanRoots;
	}
}

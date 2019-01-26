package com.haitao;

import com.haitao.demo.bean.TestController;
import com.haitao.demo.bean.TestLazyer;
import com.haitao.demo.bean.TestService;
import com.hht.annotation.config.Scan;
import com.hht.context.ApplicationContext;
import com.hht.loader.AnnotationContextLoader;

@Scan({"com.haitao"})
public class App {

	public static void main(String[] args) {
		ApplicationContext context = AnnotationContextLoader.load(App.class);
		TestController h = context.getBean(TestController.class);
		h.test();
		context.getBean("testService", TestService.class);
		TestLazyer l1 = context.getBean(TestLazyer.class);
		TestLazyer l2 = context.getBean(TestLazyer.class);
		
		System.out.println(l1==l2);
		
		TestLazyer lz = context.getBean(TestLazyer.class);
		lz.test();
	}
}

package com.haitao.demo.bean;

import com.hht.annotation.component.Component;
import com.hht.annotation.factory.Inject;
import com.hht.annotation.factory.Lazy;

@Component
@Lazy
public class TestHelper {

	@Inject("testService")
	private TestService service;
	@Inject("testService1")
	private TestService service1;
	
	public void test() {
		System.out.println("Helper test");
	}
}

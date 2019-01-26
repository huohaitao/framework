package com.haitao.demo.bean;

import com.hht.annotation.component.Service;
import com.hht.annotation.factory.Inject;

@Service("testService1")
public class TestServiceImpl1 implements TestService{

	@Inject(required=false)
	private TestHelper testHelper;
	
	public void test() {
		System.out.println("调用 service test");
	}
}

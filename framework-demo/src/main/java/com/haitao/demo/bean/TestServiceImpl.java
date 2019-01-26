package com.haitao.demo.bean;

import com.hht.annotation.component.Service;
import com.hht.annotation.factory.Inject;

@Service("testService")
public class TestServiceImpl implements TestService, Helper{

	@Inject(value="testHelper", required=false)
	private TestHelper testHelper;
	
	@Inject("testService1")
	private TestService testService1;
	
	public void test() {
		System.out.println("Ö´ÐÐÄ¬ÈÏservice test");
		testHelper.test();
	}

	@Override
	public void helper() {
		System.out.println("Ö´ÐÐservice help");
	}
}

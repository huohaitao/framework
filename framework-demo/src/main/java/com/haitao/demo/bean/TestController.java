package com.haitao.demo.bean;

import com.hht.annotation.component.Control;
import com.hht.annotation.factory.Inject;

@Control("testController0")
public class TestController {

	@Inject("testService")
	private TestService testService;
	@Inject(value="testService2", required=false)
	private TestService testService1;
	@Inject
	private Helper testServiceHelper;
	@Inject
	private TestHelper thelper;
	
	public void test() {
		System.out.println("调用contoller test");
		this.testService.test();
		if(this.testService1 != null) {
			this.testService1.test();
		}
		this.testServiceHelper.helper();
		this.thelper.test();
	}
}

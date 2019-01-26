package com.haitao.demo.bean;

import com.hht.annotation.factory.Inject;
import com.hht.annotation.factory.Scope;
import com.hht.annotation.factory.Scopes;

@Scope(Scopes.PROTOTYPE)
public class TestPrototyper {

	@Inject("testService")
	private TestService service;
	@Inject("testService1")
	private TestService service1;
	
	public void test() {
		System.out.println("Lazyer test");
	}
}

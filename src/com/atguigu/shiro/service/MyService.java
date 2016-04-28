package com.atguigu.shiro.service;

import org.apache.shiro.authz.annotation.RequiresRoles;

public class MyService {
	@RequiresRoles({"test"})
	public void test(){
		System.out.println("test");
	}
}

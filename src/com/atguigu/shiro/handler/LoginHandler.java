package com.atguigu.shiro.handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.shiro.service.MyService;
@Controller
public class LoginHandler {
	@Autowired
	private MyService myService;
	
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation(){
		myService.test();
		return "index";
	}
	
	@RequestMapping(value="/shiro_login",method=RequestMethod.POST)
	public String login(@RequestParam("username") String username,
			@RequestParam("password") String password	){
		  // 获取当前的 Subject
        Subject currentUser = SecurityUtils.getSubject();
        
        // 验证当前用户是否被认证. 即是否登录
        if (!currentUser.isAuthenticated()) {
        	// 把用户名和密码封装为一个 UsernamePasswordToken 对象. 
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            token.setRememberMe(true);
            try {
            	// 执行登陆操作
                currentUser.login(token);
            } 
            // 若用户登陆失败, 则可能抛出以下异常
            // 用户名不存在的异常
            catch (UnknownAccountException uae) {
              System.out.println("用户名不存在");
            } 
            // 密码不正确
            catch (IncorrectCredentialsException ice) {
            	 System.out.println("密码不正确");
            } 
            // 用户被锁定. 需要在开发时, 自行抛出该异常
            catch (LockedAccountException lae) {
            	 System.out.println("用户被锁定");
            }
            // ... catch more exceptions here (maybe custom ones specific to your application?
            // 登陆验证错误异常, 以上异常实际上都是该异常的子类
            catch (AuthenticationException ae) {
            	 System.out.println("其他登陆异常");
            }
        }

		return "index";
	}
	
}

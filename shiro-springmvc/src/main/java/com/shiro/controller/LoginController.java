package com.shiro.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class LoginController {
	@RequestMapping("goLogin.html")
	public String goLogin(){
		return "login";
	}
	@RequestMapping("login.html")
	public String login(String username,String password,HttpServletRequest request){
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
			subject.login(token);
			return "redirect:index.html";
		} catch (AuthenticationException e) {
			e.printStackTrace();
			request.setAttribute("error", "用户名或者密码错误");
			return "login";
		}
	}
	@RequestMapping("/logout.html")
	public String logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}
}

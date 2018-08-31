package com.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageContoller {
	@RequestMapping("index.html")
	public String index(){
		return "index";
	}
	@RequestMapping("error.html")
	public String error(){
		return "error";
	}
}

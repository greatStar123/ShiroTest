<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>欢迎</title>
</head>
<body>
	<h1>欢迎</h1><a href="/logout.html">退出登录</a>
	<shiro:hasPermission name="menu:*">
		<a href="#">menu</a>
	</shiro:hasPermission>
	<shiro:hasRole name="role3">
		<a href="#">role</a>
	</shiro:hasRole>
</body>
</html>
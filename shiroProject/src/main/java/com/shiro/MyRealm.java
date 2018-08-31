package com.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;

public class MyRealm implements Realm {

	@Override
	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken arg0) throws AuthenticationException {
//		获取用户名
		String username = (String)arg0.getPrincipal();
//		获取密码
//		只能先转为char数组然后再转为String串
		String password = new String((char[])arg0.getCredentials());
		if(!"root".equals(username)){
			throw new UnknownAccountException();
		}
		if(!"123456".equals(password)){
			throw new IncorrectCredentialsException();
		}
		return new SimpleAuthenticationInfo(username, password, getName());
		
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MyRealm";
	}

	@Override
	public boolean supports(AuthenticationToken arg0) {
//		限制数据源只支持UsernamePasswordToken
		return arg0 instanceof UsernamePasswordToken;
	}

}

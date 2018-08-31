package com.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.jdbc.core.JdbcTemplate;

public class MyRealm01 extends AuthorizingRealm {
	private JdbcTemplate jdbctemplate;
	public JdbcTemplate getJdbctemplate() {
		return jdbctemplate;
	}

	public void setJdbctemplate(JdbcTemplate jdbctemplate) {
		this.jdbctemplate = jdbctemplate;
	}
//	权限验证
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String sql = "select role_name from shiro_user_role where user_name = ?";
		String username = (String)principals.getPrimaryPrincipal();
		List<String> roles = jdbctemplate.queryForList(sql, String.class,username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(roles);
		return info;
	}
//	登录验证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String sql = "select password from shiro_user where user_name = ?";
		String username = (String)token.getPrincipal();
		String password = jdbctemplate.queryForObject(sql, String.class,username);
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, null, getName());
		return info;
	}

}

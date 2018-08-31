package com.shiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.ModularRealmAuthorizer;
import org.apache.shiro.authz.permission.WildcardPermissionResolver;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        testOne();
//    	testTwo();
//    	testThree();
    	DefaultSecurityManager securityManager = new DefaultSecurityManager();
//    	指定验证策略：至少有一个验证通过
    	ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
    	authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
    	securityManager.setAuthenticator(authenticator);
//    	设置授权
    	ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
    	authorizer.setPermissionResolver(new WildcardPermissionResolver());
    	securityManager.setAuthorizer(authorizer);
//    	设置自定义realm
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
    	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
    	dataSource.setUrl("jdbc:mysql://localhost:3306/test");
    	dataSource.setUsername("root");
    	dataSource.setPassword("123456");
    	JdbcTemplate jdbctemplate = new JdbcTemplate(dataSource);
    	MyRealm01 myRealm = new MyRealm01();
    	myRealm.setJdbctemplate(jdbctemplate);
    	securityManager.setRealm(myRealm);
      	SecurityUtils.setSecurityManager(securityManager);
    	
    	Subject subject = SecurityUtils.getSubject();
    	AuthenticationToken token = new UsernamePasswordToken("admin","123456");
    	
    	try {
//        	验证用户名和密码
    		subject.login(token);
    		System.out.println("登陆成功");
    		if(subject.isAuthenticated()){
//				判断角色
    			if (subject.hasRole("admin")) {
    				System.out.println("有amdin角色");
    			}
//				判断权限
    			if (subject.isPermitted("add")) {
    				System.out.println("有add权限");
    			}
    		}
    	} catch (Exception e) {
    		System.out.println("登陆失败");
    	}
    }
    /*
     * 数据源（realm）使用数据库，读取数据库中的信息
     */
	public static void testThree() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-mysql.ini");
        SecurityManager securityManager = factory.getInstance();
//        设置为全局
        SecurityUtils.setSecurityManager(securityManager);
//        验证过程
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin","123456");
        
        try {
//        	验证用户名和密码
        	subject.login(token);
        	System.out.println("已登录");
        	if(subject.isAuthenticated()){
//				判断角色
        		if (subject.hasRole("admin")) {
        			System.out.println("有amdin角色");
        		}
//				判断权限
        		if (subject.isPermitted("add")) {
        			System.out.println("有add权限");
        		}
        	}
        } catch (Exception e) {
        	System.out.println("登陆失败");
        }
	}
    /*
     * java自定义realm替换shiro.ini
     */
	public static void testTwo() {
		DefaultSecurityManager securityManager = new DefaultSecurityManager();
//    	指定验证策略：至少有一个验证通过
    	ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
    	authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
    	securityManager.setAuthenticator(authenticator);
//    	设置授权
    	ModularRealmAuthorizer authorizer = new ModularRealmAuthorizer();
    	authorizer.setPermissionResolver(new WildcardPermissionResolver());
    	securityManager.setAuthorizer(authorizer);
//    	设置自定义realm
    	Realm myRealm = new MyRealm();
    	securityManager.setRealm(myRealm);
    	
    	System.out.println(myRealm.getName());
    	SecurityUtils.setSecurityManager(securityManager);
    	
    	Subject subject = SecurityUtils.getSubject();
    	AuthenticationToken token = new UsernamePasswordToken("root","123456");
    	
    	try {
//        	验证用户名和密码
    		subject.login(token);
//    		if(subject.isAuthenticated()){
////				判断角色
//    			if (subject.hasRole("admin")) {
//    				System.out.println("有amdin角色");
//    			}
////				判断权限
//    			if (subject.isPermitted("add")) {
//    				System.out.println("有add权限");
//    			}
//    		}
    	} catch (Exception e) {
    		System.out.println("登陆失败");
    	}
    	System.out.println(subject.isAuthenticated());
	}
    /*
     * 读取shiro.ini配置文件（用户/角色声明）
     */
	public static void testOne() {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
//        设置为全局
        SecurityUtils.setSecurityManager(securityManager);
//        验证过程
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("root","123456");
        
        try {
//        	验证用户名和密码
        	subject.login(token);
        	if(subject.isAuthenticated()){
//				判断角色
        		if (subject.hasRole("admin")) {
        			System.out.println("有amdin角色");
        		}
//				判断权限
        		if (subject.isPermitted("add")) {
        			System.out.println("有add权限");
        		}
        	}
        } catch (Exception e) {
        	System.out.println("登陆失败");
        }
        System.out.println(subject.isAuthenticated());
	}
}

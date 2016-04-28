package com.atguigu.shiro.realms;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class MyRealm extends AuthorizingRealm {

	private String username;

	// 进行授权(看用户是否有某一个权限)的方法
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("principalCollection");
		Object principal = principalCollection.getPrimaryPrincipal();
		Collection<String> roles=new ArrayList<>();
		roles.add("user");
		if("admin".equals(principal)){
			roles.add("admin");
		}
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.addRoles(roles);
		return info;
	}

	// 进行认证(登陆)的方法
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token=(UsernamePasswordToken)authenticationToken;
		username = token.getUsername();
		System.out.println("调用Dao方法");
		if("user".equals(username) || "admin".equals(username)){
			Object principal=username;
			Object hashedCredentials="038bdaf98f2037b31f1e75b5b4c9b26e";
			if("user".equals(username)){
				hashedCredentials="098d2c478e9c11555ce2823231e02ec1";
			}
			ByteSource credentialsSalt=ByteSource.Util.bytes(username);
			String realmName=getName();
			SimpleAuthenticationInfo authentication
			=new SimpleAuthenticationInfo(principal, hashedCredentials, credentialsSalt, realmName);
		}
		//System.out.println("doGetAuthenticationInfo");
		return null;
	}
	public static void main(String[] args) {
		String algorithmName="MD5";
		Object source="123456";
		ByteSource salt=ByteSource.Util.bytes("ceadfd47cdaa814c");
		int hashIterations=1024;
		Object result=new SimpleHash(algorithmName, source, salt, hashIterations);
		System.out.println(result);
	}
}

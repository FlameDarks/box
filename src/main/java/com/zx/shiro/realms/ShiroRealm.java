package com.zx.shiro.realms;

import com.zx.bean.Msg;
import com.zx.bean.User;
import com.zx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        System.out.println("Token:"+token);
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String name = userToken.getUsername();
        String pwd = String.valueOf(userToken.getPassword());
        String result = String.valueOf(new SimpleHash("MD5",pwd,name,1024));
        System.out.println("获取的!!!!!!："+name + "\t" + result);
        if ("unknown".equals(name)){
            throw new UnknownAccountException("用户不存在");
        }
        if ("monster".equals(name)){
            throw new LockedAccountException("用户被锁定");
        }
//        String pwd = userToken.getPassword();
        List<User> users = userService.getAll(name,result);
        if (users==null){
            System.out.println("全错了!");
            return null;
        }
        User user = users.get(0);
        System.out.println(user.toString());
        Object principal = user.getUserName();
        Object credentials = user.getUserPassword();
        user.setUserPassword(null);
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes(principal);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal,credentials,credentialsSalt,realmName);
        System.out.println("over");
        System.out.println(info);
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = String.valueOf(principalCollection.getPrimaryPrincipal());
        Set<String> roles = new HashSet<>();
        if (userService.getUserByName(principal).getUserType().equals("admin")){
            roles.add("admin");
        }
        roles.add("user");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roles);
        return info;
    }
}

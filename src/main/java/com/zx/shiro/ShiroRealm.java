package com.zx.shiro;

import com.zx.bean.User;
import com.zx.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    UserService userService;

    /**
     * 进行用户验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String name = userToken.getUsername();
        String pwd = String.valueOf(userToken.getPassword());
//        对获取的名字，密码进行加密
        String result = String.valueOf(new SimpleHash("MD5",pwd,name,1024));
        List<User> users = userService.getAll(name,result);
        if (users==null){
            System.out.println("全错了!");
            throw new UnknownAccountException("用户不存在");
//          throw new LockedAccountException("用户被锁定");
        }
        User user = users.get(0);
        user.setUserPassword(null);
        String realmName = getName();
        ByteSource credentialsSalt = ByteSource.Util.bytes((Object) name);
        return new SimpleAuthenticationInfo(name,pwd,credentialsSalt,realmName);
    }

    /**
     * 分配权限
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        String principal = String.valueOf(principalCollection.getPrimaryPrincipal());
        Set<String> roles = new HashSet<>();
        if (userService.getUserByName(principal).getUserType().equals("admin")){
            roles.add("admin");
        }
        roles.add("user");
        return new SimpleAuthorizationInfo(roles);
    }
}

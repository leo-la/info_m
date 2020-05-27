package com.tfswx.config;

import com.tfswx.dao.PermissionDao;
import com.tfswx.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 安全数据管理
 * 创建者：倪浪
 * 创建日期：2020-5-12
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    PermissionDao queryUser;

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AuthorizationInfo.class);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //拿到当前登录的用户对象
        Subject subject = SecurityUtils.getSubject();
        User principal = (User) subject.getPrincipal();
        List<String> userRoles = queryUser.listUserRoles(principal.getId());
        info.addRoles(userRoles);
        log.info("["+principal.getUsername()+"]登录了系统，具有角色：["+userRoles.get(0)+"]");
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //用户名密码
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        List<User> users = queryUser.getUserByName(token.getUsername());
        if(users.size()==0){
            return null;//抛出异常 UnknownAccountException
        }
        //密码认证
        return new SimpleAuthenticationInfo(users.get(0),users.get(0).getPassword(),"");
    }
}

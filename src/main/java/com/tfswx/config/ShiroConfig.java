package com.tfswx.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //1.创建realm对象，需要自定义
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }

    //2.DefaultWebSecurityManager
    @Bean(name = "manager")
    public DefaultWebSecurityManager defaultManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        //关联UserRealm
        manager.setRealm(userRealm);
        manager.setRememberMeManager(rememberMeManager());
        return manager;
    }

    //3.ShiroFilterFactoryBean
    @Bean(name = "bean")
    public ShiroFilterFactoryBean getFactoryBean(@Qualifier("manager") DefaultWebSecurityManager defaultManager){
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        //关联manager
        //设置安全管理器
        bean.setSecurityManager(defaultManager);
        //shiro内置过滤器
//        anno：无需认证就可以访问
//        authc:必须认证了才能访问
//        user:必须拥有 记住我 功能才可以访问
//        perms:拥有对某个资源的权限v爱可以访问
//        role:拥有某个角色权限才可以访问
        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/**","anon");
        filterMap.put("/route/permission/**","authc");
        filterMap.put("/route/beijing/**","authc");
        filterMap.put("/route/confidentiality/**","authc");
        filterMap.put("/route/firm/**","authc");
        filterMap.put("/route/discipline/**","authc");
        filterMap.put("/route/finance/**","authc");
        filterMap.put("/route/human/**","authc");
        filterMap.put("/route/law/**","authc");
        filterMap.put("/route/military/**","authc");
        filterMap.put("/route/office/**","authc");
        filterMap.put("/route/operation/**","authc");
        filterMap.put("/route/quality/**","authc");
        filterMap.put("/route/remote/**","authc");
//        filterMap.put("/main/toAdd","perms[user:add]");//授权
        bean.setUnauthorizedUrl("/");//未授权的跳转页面
        bean.setLoginUrl("/toLogin");


        bean.setFilterChainDefinitionMap(filterMap);

        return bean;
    }

    //cookie
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        //记住我cookie生效时间,单位是秒
        simpleCookie.setMaxAge(600);
        return simpleCookie;
    }

    /**
     * cookie管理器;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie());
        return cookieRememberMeManager;
    }

    //整合shiroDialect:shiro-thymeleaf
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }



}

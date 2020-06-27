package com.tfswx.service.impl;

import com.tfswx.template.Templates;
import com.tfswx.dao.PermissionDao;
import com.tfswx.factory.PageTemplateFactory;
import com.tfswx.pojo.Dep;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.Role;
import com.tfswx.pojo.User;
import com.tfswx.service.PermissionService;
import com.tfswx.template.AbstractPageTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<Dep> searchDeps() {
        return permissionDao.listDeps();
    }

    @Override
    public PageBean searchManagerPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.MEMBER_PAGE);
        return template.run(pageBean,permissionDao);
    }

    @Override
    public User searchOneManager(Integer id) {
        User manager = permissionDao.getUserById(id);
        if(manager!=null){
            return manager;
        }else {
            return null;
        }

    }

    @Override
    public void resetPassword(Integer id) {
        User user = permissionDao.getUserById(id);
        permissionDao.updatePassword(user.getUsername(),"11111111");

    }

    @Override
    public void addManager(String username, String password, Integer depid, Integer roleid) {
        Date date = new Date();
        permissionDao.insertUser(username,password,depid,date);

        List<User> users = permissionDao.getUserByName(username);

        if(users.size()==1){
            permissionDao.insertUserRole(users.get(0).getId(),roleid);
        }
    }

    @Override
    public void deleteManager(Integer id) {
        permissionDao.deleteManager(id);
    }


    @Override
    public Role searchOneRole(Integer id) {
        return permissionDao.getRoleById(id);
    }

    @Override
    public List<Role> searchRoles() {
        return permissionDao.listRoles();
    }

    @Override
    public PageBean searchRolesPage(PageBean pageBean) {
        AbstractPageTemplate template = PageTemplateFactory.createTemplate(Templates.ROLE_PAGE);
        return template.run(pageBean,permissionDao);
    }

    @Override
    public void updateUserRole(Integer userid, Integer roleid,Integer depid) {
        permissionDao.updateUserRole(userid, roleid);
        permissionDao.updateUserDep(userid,depid);
    }

    @Override
    public void updateRole(Integer roleid, String rolename, String description) {
        permissionDao.updateRole(roleid,rolename,description);
    }

    @Override
    public void insertRole(String name,String description) {
        permissionDao.insertRole(name,description);
    }

    @Override
    public void deleteRole(Integer roleid) {
        permissionDao.deleteRole(roleid);
    }

    @Override
    public void updatePassword(String name, String password) {
        permissionDao.updatePassword(name,password);
    }

    @Override
    public Boolean checkName(String username) {
        List<User> users = permissionDao.getUserByName(username);
        if(users.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

}

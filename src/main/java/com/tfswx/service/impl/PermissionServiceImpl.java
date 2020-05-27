package com.tfswx.service.impl;

import com.tfswx.common.PageTemplateType;
import com.tfswx.dao.PermissionDao;
import com.tfswx.factory.PageTemplateFactory;
import com.tfswx.pojo.Dep;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.Role;
import com.tfswx.pojo.User;
import com.tfswx.service.PermissionService;
import com.tfswx.template.PageSearchTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    PermissionDao permissionDao;

    @Override
    public List<Dep> searchDeps() {
        return permissionDao.listDeps();
    }

    /**
     * 查询管理员数据集
     * @param pageBean
     * @return
     */
    @Override
    public PageBean searchManagerPage(PageBean pageBean) {
        PageSearchTemplate template = PageTemplateFactory.createTemplate(PageTemplateType.MEMBER_PAGE);
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
    public Boolean resetPassword(Integer id) {
        User user = permissionDao.getUserById(id);
        Integer integer = permissionDao.updatePassword(user.getUsername(),"11111111");
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String addManager(String username, String password, Integer depid, Integer roleid) {
        Date date = new Date();
        permissionDao.insertUser(username,password,depid,date);
        List<User> users = permissionDao.getUserByName(username);

        if(users.size()==1){
            Integer integer = permissionDao.insertUserRole(users.get(0).getId(),roleid);
            if(integer==1){
                return "yes";
            }else {
                return "no";
            }
        }else{
            return "no";
        }
    }

    @Override
    public Boolean deleteManager(Integer id) {
        Integer integer = permissionDao.deleteManager(id);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public Role searchUserRoles(Integer id) {
        return permissionDao.getUserRole(id);
    }

    @Override
    public Role searchOneRole(Integer id) {
        return permissionDao.getRoleById(id);
    }

    @Override
    public List<Role> searchRoles() {
        List<Role> roles = permissionDao.listRoles();
        return roles;
    }

    @Override
    public PageBean searchRolesPage(PageBean pageBean) {
        PageSearchTemplate template = PageTemplateFactory.createTemplate(PageTemplateType.ROLE_PAGE);
        return template.run(pageBean,permissionDao);
    }

    /**
     * 更新用户角色
     * @param userid
     * @param roleid
     * @return
     */
    @Override
    public String updateUserRole(Integer userid, Integer roleid,Integer depid) {
        Integer integer = permissionDao.updateUserRole(userid, roleid);
        permissionDao.updateUserDep(userid,depid);
        if(integer==0){
            return "更新失败!";
        }else{
            return "更新成功!";
        }
    }

    @Override
    public Boolean updateRole(Integer roleid, String rolename, String description) {
        Integer integer = permissionDao.updateRole(roleid,rolename,description);
        if(integer==1){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 删除管理员
     * @param userid
     * @return
     */
    @Override
    public String deleteUser(Integer userid) {
        Integer integer = permissionDao.deleteManager(userid);
        if(integer>0){
            return "删除成功！";
        }else {
            return "删除失败！";
        }
    }

    @Override
    public String insertRole(String name,String description) {
        Integer integer = permissionDao.insertRole(name,description);
        if(integer>0){
            return "添加成功！";
        }else {
            return "添加失败！";
        }
    }

    @Override
    public Boolean deleteRole(Integer roleid) {
        Integer integer = permissionDao.deleteRole(roleid);
        if(integer>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String updatePassword(String name, String password) {
        Integer integer = permissionDao.updatePassword(name,password);
        if(integer==1){
            return "yes";
        }else{
            return "no";
        }
    }

    @Override
    public Boolean checkName(String username) {
        List<User> users = permissionDao.getUserByName(username);
        if(users.size()==0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询单用户信息
     * @param id
     * @return
     */
    @Override
    public User searchOneUser(Integer id) {
        return permissionDao.getUserById(id);
    }
}

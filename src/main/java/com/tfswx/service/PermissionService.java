package com.tfswx.service;

import com.tfswx.pojo.Dep;
import com.tfswx.pojo.PageBean;
import com.tfswx.pojo.Role;
import com.tfswx.pojo.User;

import java.util.List;

public interface PermissionService {

    /**
     * 查询部门信息
     * @return
     */
    List<Dep> searchDeps();

    /**
     * 查询管理员页面数据
     * @param pageBean
     * @return
     */
    PageBean searchManagerPage(PageBean pageBean);

    /**
     * 查询单管理员信息
     * @param id
     * @return
     */
    User searchOneManager(Integer id);

    /**
     * 重置密码
     * @param id
     * @return
     */
    void resetPassword(Integer id);

    /**
     * 添加管理员
     * @param username
     * @param password
     * @param depid
     * @param roleid
     */
    void addManager(String username,String password,Integer depid,Integer roleid);

    /**
     * 移出管理员
     * @param id
     */
    void deleteManager(Integer id);

    /**
     * 查询单用户信息
     * @param id
     * @return
     */
    Role searchOneRole(Integer id);

    /**
     * 查询角色信息
     * @return
     */
    List<Role> searchRoles();

    /**
     * 查询角色页面信息
     * @param pageBean
     * @return
     */
    PageBean searchRolesPage(PageBean pageBean);

    /**
     * 更新用户角色
     * @param userid
     * @param roleid
     * @return
     */
    void updateUserRole(Integer userid,Integer roleid,Integer depid);

    /**
     * 更新角色信息
     * @param roleid
     * @param rolename
     * @return
     */
    void updateRole(Integer roleid,String rolename,String description);

    /**
     * 添加角色
     * @param name
     * @return
     */
    void insertRole(String name,String description);

    /**
     * 删除角色
     * @param roleid
     * @return
     */
    void deleteRole(Integer roleid);

    /**
     * 修改密码
     * @param name
     * @param password
     * @return
     */
    void updatePassword(String name,String password);

    /**
     * 检查重名
     * @param username
     * @return
     */
    Boolean checkName(String username);


}

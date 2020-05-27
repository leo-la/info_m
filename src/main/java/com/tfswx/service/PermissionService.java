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
     * 查询单用户信息
     * @param id
     * @return
     */
    User searchOneUser(Integer id);


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
    Boolean resetPassword(Integer id);


    String addManager(String username,String password,Integer depid,Integer roleid);

    Boolean deleteManager(Integer id);

    Role searchUserRoles(Integer id);

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
    String updateUserRole(Integer userid,Integer roleid,Integer depid);

    /**
     * 更新角色信息
     * @param roleid
     * @param rolename
     * @return
     */
    Boolean updateRole(Integer roleid,String rolename,String description);


    /**
     * 删除单用户
     * @param userid
     * @return
     */
    String deleteUser(Integer userid);

    /**
     * 添加角色
     * @param name
     * @return
     */
    String insertRole(String name,String description);

    /**
     * 删除角色
     * @param roleid
     * @return
     */
    Boolean deleteRole(Integer roleid);

    /**
     * 修改密码
     * @param name
     * @param password
     * @return
     */
    String updatePassword(String name,String password);

    /**
     * 检查重名
     * @param username
     * @return
     */
    Boolean checkName(String username);


}

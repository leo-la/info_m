package com.tfswx.dao;

import com.tfswx.pojo.Dep;
import com.tfswx.pojo.Role;
import com.tfswx.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
@Mapper
@Repository
public interface PermissionDao extends BaseDao{

    /**
     * 查询部门信息
     * @return
     */
    List<Dep> listDeps();
    /**
     * 查询用户集
     * @return
     */
    List<User> listUsers();

    /**
     * 查询用户（id）
     * @param id
     * @return
     */
    User getUserById(Integer id);

    /**
     * 查询用户（name）
     * @param name
     * @return
     */
    List<User> getUserByName(String name);

    /**
     * 查询用户角色集-返回角色名称
     * @param id
     * @return
     */
    List<String> listUserRoles(Integer id);

    /**
     * 查询用户角色集-返回角色对象
     * @param id
     * @return
     */
    Role getUserRole(Integer id);

    /**
     * 查询单角色信息
     * @param id
     * @return
     */
    Role getRoleById(Integer id);

    /**
     * 查询所有角色集
     * @return
     */
    List<Role> listRoles();

    /**
     * 统计用户数
     * @return
     */
    Integer countUsers();

    /**
     * 统计管理员数
     * @return
     */
    Integer countManagers();

    /**
     * 统计角色数
     * @return
     */
    Integer countRoles();

    /**
     * 统计条件查询用户数
     * @param condition
     * @return
     */
    Integer countUsersByCondition(String condition);

    /**
     * 查询用户页面信息集
     * @param start
     * @param size
     * @return
     */
    List<User> listUserPage(Integer start,Integer size);

    /**
     * 查询管理员页面信息集
     * @param start
     * @param size
     * @return
     */
    List<User> listManagerPage(Integer start, Integer size);

    /**
     * 查询角色页面信息集
     * @param start
     * @param size
     * @return
     */
    List<Role> listRolesPage(Integer start, Integer size);

    /**
     * 查询条件查询用户页面信息集
     * @param start
     * @param size
     * @param condition
     * @return
     */
    List<User> listUserPageByCondition (Integer start,Integer size,String condition);

    /**
     * 更新用户类型
     * @param id
     * @param usertype
     * @return
     */
    Integer updateUserType(Integer id,String usertype);

    /**
     * 更新用户角色
     * @param userid
     * @param roleid
     * @return
     */
    Integer updateUserRole(Integer userid,Integer roleid);

    /**
     * 更新用户部门
     * @param userid
     * @param depid
     * @return
     */
    Integer updateUserDep(Integer userid, Integer depid);

    /**
     * 添加管理员
     * @param username
     * @param password
     * @param depid
     * @param date
     * @return
     */
    Integer insertUser(String username, String password, Integer depid, Date date);

    /**
     * 更新角色信息
     * @param roleid
     * @param rolename
     * @return
     */
    Integer updateRole(Integer roleid,String rolename,String description);

    /**
     * 添加角色
     * @param name
     * @return
     */
    Integer insertRole(String name,String description);

    /**
     * 删除角色
     * @param roleid
     * @return
     */
    Integer deleteRole(Integer roleid);

    /**
     * 删除用户角色
     * @param id
     * @return
     */
    Integer deleteUserRoles(Integer id);

    /**
     * 删除单用户
     * @param userid
     * @return
     */
    Integer deleteManager(Integer userid);

    /**
     * 添加用户角色
     * @param id
     * @param roleid
     * @return
     */
    Integer insertUserRole(Integer id,Integer roleid);

    /**
     * 修改密码
     * @param username
     * @param password
     * @return
     */
    Integer updatePassword(String username,String password);


}

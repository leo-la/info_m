package com.tfswx.controller.backcontroller.permission;

import com.tfswx.pojo.*;
import com.tfswx.service.PermissionService;
import com.tfswx.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 后台权限控制器
 */
@Controller
@RequestMapping("permission")
public class PermissionController {
    /**
     * 权限服务
     */
    @Autowired
    PermissionService permissionService;

    /**
     * 查询单用户信息
     * @param id
     * @return
     */
    @RequestMapping("user/searchOneUserInfo/{id}")
    @ResponseBody
    public User searchOneUserInfo(@PathVariable String id){
        int id2 = CommonUtils.restFulConverter(id);
        return permissionService.searchOneUser(id2);
    }

    /**
     * 查询角色信息
     * @return
     */
    @RequestMapping("role/searchRoles")
    @ResponseBody
    public List<Role> searchRoles(){
        return permissionService.searchRoles();
    }

    /**
     * 查询部门信息
     * @return
     */
    @RequestMapping("searchDeps")
    @ResponseBody
    public List<Dep> searchDeps(){
        return permissionService.searchDeps();
    }


    @RequestMapping("user/deleteUser/{userid}")
    @ResponseBody
    public String deleteUser(@PathVariable String userid){
        return permissionService.deleteUser(CommonUtils.restFulConverter(userid));
    }

    /**
     * 查询管理员页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("manager/searchManagerPageInfo")
    @ResponseBody
    public PageBean searchManagerPageInfo(@RequestBody PageBean pageBean){
        pageBean = permissionService.searchManagerPage(pageBean);
        return pageBean;
    }


    /**
     * 查询管理员信息
     * @param map
     * @return
     */
    @RequestMapping("manager/searchOneManagerPageInfo")
    @ResponseBody
    public User searchOneManagerPageInfo(@RequestBody Map<String,Integer> map){
        return permissionService.searchOneManager(map.get("id"));
    }

    /**
     * 更新管理员权限
     * @param userid
     * @return
     */
    @RequestMapping("manager/updateManagerPermission")
    public String updateManagerPermission(Integer userid,Integer roleid,Integer depid){
        permissionService.updateUserRole(userid, roleid,depid);
        return "permissionPage/managers";
    }

    /**
     * 添加管理员
     * @param map
     * @return
     */
    @RequestMapping("manager/addManager")
    @ResponseBody
    public ErrorMsg addManager(@RequestBody Map<String,String> map, ErrorMsg errorMsg){
        Boolean flag = permissionService.checkName(map.get("username"));

        if(flag){
            errorMsg.setName(permissionService.addManager(map.get("username"),map.get("password"),Integer.parseInt(map.get("depid")),Integer.parseInt(map.get("roleid"))));
        }else{
            errorMsg.setName("用户名已存在");
        }

        return errorMsg;
    }

    @RequestMapping("manager/resetPassword")
    @ResponseBody
    public Boolean resetPassword(@RequestBody Map<String,String> map){
        return permissionService.resetPassword(Integer.parseInt(map.get("id")));
    }

    /**
     * 移出管理员
     * @param map
     * @return
     */
    @RequestMapping("manager/deleteManager")
    @ResponseBody
    public Boolean deleteManager(@RequestBody Map<String,Integer> map){
        return permissionService.deleteManager(map.get("id"));
    }

    /**
     * 查询角色页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("role/searchRolePageInfo")
    @ResponseBody
    public PageBean searchRolePageInfo(@RequestBody PageBean pageBean){
        return permissionService.searchRolesPage(pageBean);
    }

    /**
     * 查询单角色信息
     * @param map
     * @return
     */
    @RequestMapping("role/searchOneRolePageInfo")
    @ResponseBody
    public Role searchOneRolePageInfo(@RequestBody Map<String,Integer> map ){
        Integer roleid = map.get("id");
        return permissionService.searchOneRole(roleid);
    }

    /**
     * 更新角色信息
     * @param id
     * @return
     */
    @RequestMapping("role/updateRoleInfo")
    public String updateRoleInfo(Integer id,String name,String description){
        permissionService.updateRole(id,name,description);
        return "permissionPage/roles";
    }

    /**
     * 添加角色
     * @param name
     * @return
     */
    @RequestMapping("role/addRole")
    public String addRole(String name,String description){
        permissionService.insertRole(name,description);
        return "permissionPage/roles";
    }

    @RequestMapping("role/deleteRole")
    @ResponseBody
    public Boolean deleteRole(@RequestBody Map<String,String> map ){
        Integer roleid = Integer.parseInt(map.get("id"));
        return permissionService.deleteRole(roleid);
    }


}

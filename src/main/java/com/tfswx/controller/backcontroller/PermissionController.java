package com.tfswx.controller.backcontroller;

import com.tfswx.exception.ResultBody;
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
     * 查询角色信息
     * @return
     */
    @RequestMapping("role/searchRoles")
    @ResponseBody
    public ResultBody searchRoles(){
        return ResultBody.success(permissionService.searchRoles());
    }

    /**
     * 查询部门信息
     * @return
     */
    @RequestMapping("searchDeps")
    @ResponseBody
    public ResultBody searchDeps(){
        return ResultBody.success(permissionService.searchDeps());
    }


    /**
     * 查询管理员页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("manager/searchManagerPageInfo")
    @ResponseBody
    public ResultBody searchManagerPageInfo(@RequestBody PageBean pageBean){
        return ResultBody.success(permissionService.searchManagerPage(pageBean));
    }


    /**
     * 查询管理员信息
     * @param map
     * @return
     */
    @RequestMapping("manager/searchOneManagerPageInfo")
    @ResponseBody
    public ResultBody searchOneManagerPageInfo(@RequestBody Map<String,Integer> map){
        return ResultBody.success(permissionService.searchOneManager(map.get("id")));
    }

    /**
     * 更新管理员权限
     * @param map
     * @return
     */
    @RequestMapping("manager/updateManagerPermission")
    @ResponseBody
    public Boolean updateManagerPermission(@RequestBody Map<String,Integer> map){
        permissionService.updateUserRole(map.get("userid"), map.get("roleid"),map.get("depid"));
        return true;
    }

    /**
     * 添加管理员
     * @param map
     * @return
     */
    @RequestMapping("manager/addManager")
    @ResponseBody
    public ResultBody addManager(@RequestBody Map<String,String> map){

        if(permissionService.checkName(map.get("username"))){
            permissionService.addManager(map.get("username"),map.get("password"),Integer.parseInt(map.get("depid")),Integer.parseInt(map.get("roleid")));
            return ResultBody.success();
        }else{
            return ResultBody.error("400","用户名已存在");
        }


    }

    @RequestMapping("manager/resetPassword")
    @ResponseBody
    public Boolean resetPassword(@RequestBody Map<String,String> map){
        permissionService.resetPassword(Integer.parseInt(map.get("id")));
        return true;
    }

    /**
     * 移出管理员
     * @param map
     * @return
     */
    @RequestMapping("manager/deleteManager")
    @ResponseBody
    public Boolean deleteManager(@RequestBody Map<String,Integer> map){
        permissionService.deleteManager(map.get("id"));
        return true;
    }

    /**
     * 查询角色页面信息
     * @param pageBean
     * @return
     */
    @RequestMapping("role/searchRolePageInfo")
    @ResponseBody
    public ResultBody searchRolePageInfo(@RequestBody PageBean pageBean){
        return ResultBody.success(permissionService.searchRolesPage(pageBean));
    }

    /**
     * 查询单角色信息
     * @param map
     * @return
     */
    @RequestMapping("role/searchOneRolePageInfo")
    @ResponseBody
    public ResultBody searchOneRolePageInfo(@RequestBody Map<String,Integer> map ){
        return ResultBody.success(permissionService.searchOneRole(map.get("id")));
    }

    /**
     * 更新角色信息
     * @param map
     * @return
     */
    @RequestMapping("role/updateRoleInfo")
    @ResponseBody
    public Boolean updateRoleInfo(@RequestBody Map<String,String> map){
        permissionService.updateRole(Integer.parseInt(map.get("id")),map.get("name"),map.get("description"));
        return true;
    }

    /**
     * 添加角色
     * @param map
     * @return
     */
    @RequestMapping("role/addRole")
    @ResponseBody
    public Boolean addRole(@RequestBody Map<String,String> map){
        permissionService.insertRole(map.get("name"),map.get("description"));
        return true;
    }

    @RequestMapping("role/deleteRole")
    @ResponseBody
    public Boolean deleteRole(@RequestBody Map<String,Integer> map ){
        permissionService.deleteRole(map.get("id"));
        return true;
    }


}

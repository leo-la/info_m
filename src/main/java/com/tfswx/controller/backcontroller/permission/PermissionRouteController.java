package com.tfswx.controller.backcontroller.permission;

import com.tfswx.utils.CommonUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("route")
@Controller
public class PermissionRouteController {

    @RequestMapping("toLogin")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("toIndex")
    public String toMain(){
        return "index";
    }

    @RequestMapping("permission/users")
    public String toUser(){
        return "permissionPage/users";
    }

    @RequestMapping("permission/managers")
    public String toManagers(){
        return "permissionPage/managers";
    }

    @RequestMapping("permission/roles")
    public String toRoles(){
        return "permissionPage/roles";
    }

    @RequestMapping("permission/updateManager/{id}")
    public String toUpdateRoles(@PathVariable String id,Model model){
        model.addAttribute("managerid",CommonUtils.restFulConverter(id));
        return "permissionPage/updateManager";
    }

    @RequestMapping("permission/updateRole/{id}")
    public String toUpdateRole(@PathVariable String id,Model model){
        model.addAttribute("roleid",CommonUtils.restFulConverter(id));
        return "permissionPage/updateRole";
    }

    @RequestMapping("permission/addManager")
    public String toAddManager(){
        return "permissionPage/addManager";
    }

    @RequestMapping("permission/addRole")
    public String toAddRole(){
        return "permissionPage/addRole";
    }

}

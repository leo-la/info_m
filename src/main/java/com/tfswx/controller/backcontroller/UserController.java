package com.tfswx.controller.backcontroller;

import com.tfswx.pojo.ErrorMsg;
import com.tfswx.pojo.User;
import com.tfswx.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;


@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    PermissionService permissionService;


    @RequestMapping("/login")
    public String login(User user, Model model,Boolean rememberMe){
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        if(null==rememberMe){
            rememberMe = false;
        }
        //封装用户的登陆数据
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword(),rememberMe);
        try{
            subject.login(token);
            return "index";
        }catch (UnknownAccountException e){
            model.addAttribute("login_msg","用户名错误");
            return "login";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("login_msg", "密码错误");
            return "login";
        }
    }
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject!=null){
            SecurityUtils.getSubject().logout();
        }
        return "redirect:/route/toIndex";
    }


    @RequestMapping("/updatePassword")
    @ResponseBody
    public ErrorMsg updatePassword(@RequestBody Map<String,String> map, ErrorMsg errorMsg){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        System.out.println(user.getPassword());
        System.out.println(map.get("oldPassword"));
        if(!user.getPassword().equals(map.get("oldPassword"))){
            errorMsg.setName("原始密码错误");
            return errorMsg;
        }else{
            errorMsg.setName(permissionService.updatePassword(user.getUsername(),map.get("newPassword")));
            return errorMsg;
        }
    }
}

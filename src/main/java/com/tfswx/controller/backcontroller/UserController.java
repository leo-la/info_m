package com.tfswx.controller.backcontroller;

import com.tfswx.exception.ResultBody;
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

    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

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
            log.info("用户 [{}] 登录了系统",token.getUsername());
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
    public ResultBody updatePassword(@RequestBody Map<String,String> map){
        User user = (User) SecurityUtils.getSubject().getPrincipal();

        if(!user.getPassword().equals(map.get("oldPassword"))){
            return ResultBody.error("400","原始密码错误");
        }else{
            permissionService.updatePassword(user.getUsername(),map.get("newPassword"));
            return ResultBody.success();
        }
    }
}

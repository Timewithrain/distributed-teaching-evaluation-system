package com.watermelon.controller;

import com.watermelon.api.entity.Query;
import com.watermelon.api.entity.User;
import com.watermelon.api.service.UserService;
import com.watermelon.api.util.ResultUtil;
import com.watermelon.api.util.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Object login(@RequestBody User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        User u = userService.getUserByName(user.getName());
        if (u!=null&&u.getPassword().equals(user.getPassword())){
            session.setAttribute("id",user.getId());
            session.setAttribute("username",user.getName());
            return ResultUtil.success();
        }else {
            //登陆失败
            return ResultUtil.error(StatusCode.LOGIN_FAIL);
        }
    }

    @GetMapping("/logout")
    public Object logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("id");
        session.removeAttribute("username");
        return ResultUtil.success();
    }

    //找回密码
    @PostMapping("/findPWD")
    Map<String,String> findPWD(@RequestBody(required = false)Query query){
        String idnumber = query.getIdnumber();
        String username = query.getUsername();
        String newpwd = query.getNewpwd();
        String comfirmpwd = query.getComfirmpwd();
        User u = userService.getUserByName(username);
        Map<String,String> map = new HashMap<>();
        if(idnumber.equals(u.getIdNumber())&&newpwd.equals(comfirmpwd)){
            u.setPassword(newpwd);
            userService.updateUser(u);
            map.put("status","200");
            map.put("username",u.getName());
            return map;
        }else if(!idnumber.equals(u.getIdNumber())){
            map.put("status","401");
            map.put("massage","身份证不匹配");
            return map;
        }else if(!newpwd.equals(comfirmpwd)){
            map.put("status","401");
            map.put("massage","密码不匹配");
            return map;
        }else{
            map.put("status","401");
            map.put("massage","其他错误");
            return map;
        }
    }

    //修改密码
    @PostMapping("/updatePWD")
    Map<String,String> updatePWD(@RequestBody(required = false)Query query){
        String oldpwd = query.getOldpwd();
        String username = query.getUsername();
        String newpwd = query.getNewpwd();
        String comfirmpwd = query.getComfirmpwd();
        User u = userService.getUserByName(username);
        Map<String,String> map = new HashMap<>();
        if(oldpwd.equals(u.getPassword())&&newpwd.equals(comfirmpwd)){
            u.setPassword(newpwd);
            userService.updateUser(u);
            map.put("status","200");
            map.put("username",u.getName());
            return map;
        }else if(!newpwd.equals(u.getPassword())){
            map.put("status","401");
            map.put("massage","旧密码不正确");
            return map;
        }else if(!newpwd.equals(comfirmpwd)){
            map.put("status","401");
            map.put("massage","密码不匹配");
            return map;
        }else{
            map.put("status","401");
            map.put("massage","其他错误");
            return map;
        }
    }

    @GetMapping("/getUserByName")
    public User getUserByName(String name){
        return userService.getUserByName(name);
    }

}
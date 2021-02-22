package com.watermelon.controller;

import com.watermelon.api.entity.Query;
import com.watermelon.api.entity.User;
import com.watermelon.api.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String PROVIDER_URL = "http://distribute-user-manage-provider/user";

    @PostMapping("/login")
    public Object login(@RequestBody User user){
        String url = PROVIDER_URL + "/login";
        ResponseEntity<ResultUtil> entity = restTemplate.postForEntity(url,user,ResultUtil.class);
        return entity.getBody();
    }

    @GetMapping("/logout")
    public Object logout(){
        String url = PROVIDER_URL + "/logout";
        ResponseEntity<ResultUtil> entity = restTemplate.getForEntity(url,ResultUtil.class);
        return entity.getBody();
    }

    //找回密码
    @PostMapping("/findPWD")
    Map<String,String> findPWD(@RequestBody(required = false)Query query){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/findPWD",query,Map.class);
        return entity.getBody();
    }

    //修改密码
    @PostMapping("/updatePWD")
    Map<String,String> updatePWD(@RequestBody(required = false)Query query){
        ResponseEntity<Map> entity = restTemplate.postForEntity(PROVIDER_URL+"/updatePWD",query,Map.class);
        return entity.getBody();
    }
}
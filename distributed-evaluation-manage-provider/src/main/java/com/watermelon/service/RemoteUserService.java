package com.watermelon.service;

import com.watermelon.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteUserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-user-manage-provider/user";

    public User getUserByName(String name){
        return restTemplate.getForObject(DEPENDENCY_URL+"/getUserByName?name="+name,User.class);
    }

}

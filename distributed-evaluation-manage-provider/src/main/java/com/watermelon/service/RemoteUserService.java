package com.watermelon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.watermelon.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RemoteUserService {

    @Autowired
    private RestTemplate restTemplate;

    private static final String DEPENDENCY_URL = "http://distribute-user-manage-provider/user";

    /**
     * 依据学生姓名获取学生基本信息，该方法需要依赖distribute-user-manage-provider服务
     * <p>当distribute-user-manage-provider依赖出现故障时触发熔断机制
     * <p>熔断依赖：User getUserByNameFallback(String name)
     * @param name 学生姓名String
     * @return User
     */
    @HystrixCommand(fallbackMethod = "getUserByNameFallback")
    public User getUserByName(String name){
        return restTemplate.getForObject(DEPENDENCY_URL+"/getUserByName?name="+name,User.class);
    }

    /**
     * 当distribute-user-manage-provider依赖出现故障时调用该方法避免服务占用
     * <p>该方法返回一个null值，在不影响的系统整体正常运行情况下，提供有损的服务
     * @param name 学生姓名String
     * @return null
     */
    public User getUserByNameFallback(String name){
        return null;
    }

}

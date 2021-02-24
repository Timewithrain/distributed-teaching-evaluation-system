package com.watermelon.api.service;

import com.watermelon.api.entity.User;
import java.util.List;

public interface UserService {

    int registerUser(String username,String password);

    User getUserById(int id);

    User getUserByName(String name);

    User getUserDetailsByName(String name);

    String getNameById(int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    List<User> listUser(int startPage, int pageSize);

    int getMaxUserId();

    //暂不开启密码加密
//    String encodeMD5(String str);

}

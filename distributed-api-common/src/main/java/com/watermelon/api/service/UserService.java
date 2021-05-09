package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.User;
import java.util.List;

public interface UserService {

    User login(User user);

    int registerUser(String username,String password);

    User getUserByIdNumber(String idNumber);

    User getUserById(int id);

    List<User> getUserByName(String name);

    User getUserDetailsByName(String name);

    String getNameById(int id);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    List<User> listUser(int startPage, int pageSize);

    IPage<User> searchUser(int startPage, int pageSize, String str);

    int getMaxUserId();

    int getUserNumber();

    //暂不开启密码加密
//    String encodeMD5(String str);

}

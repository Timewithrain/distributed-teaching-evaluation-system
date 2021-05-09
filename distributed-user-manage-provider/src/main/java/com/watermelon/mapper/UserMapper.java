package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {

    List<User> listUser(Page<User> page);

    IPage<User> searchUser(Page<User> page, String str);

    int getUserNumber();

    User getUserById(int id);

    List<User> getUserByName(String name);

    User getUserByIdNumber(String idNumber);

    int addUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    int getMaxUserId();

}

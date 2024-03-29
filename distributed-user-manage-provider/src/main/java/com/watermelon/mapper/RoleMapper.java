package com.watermelon.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface RoleMapper {

    Role getRoleById(int id);

    Role getRoleByName(String name);

    List<Integer> getRolesPermissionsId(int id);

    IPage<Role> listRole(Page<Role> page);

    List<Role> listRole();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int id);

    void addRolePermission(int roleId,int permsId);

    void deleteRolePermission(int roleId,int permsId);

    void deleteAllRolePermission(int roleId);

    Integer getRoleNumber();

    int getMaxRoleId();
}
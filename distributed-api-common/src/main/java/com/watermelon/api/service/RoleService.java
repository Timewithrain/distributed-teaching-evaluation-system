package com.watermelon.api.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.watermelon.api.entity.Role;
import com.watermelon.api.entity.Permission;

import java.util.List;

public interface RoleService {

    Role getRoleById(int id);

    Role getRoleByName(String name);

    List<Integer> getPermissionsId(int id);

    List<Permission> getRolesPermissions(int id);

    IPage<Role> listRole(int startPage, int pageSize);

    List<Role> listRole();

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int id);

    void addRolePermission(int roleId,int permsId);

    void updateRolePermissions(int roleId,String perms);

    void deleteRolePermission(int roleId,int permsId);

    int getRoleNumber();

    int getMaxRoleId();
}

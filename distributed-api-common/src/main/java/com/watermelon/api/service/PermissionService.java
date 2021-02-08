package com.watermelon.api.service;

import com.watermelon.api.entity.Permission;

import java.util.List;

public interface PermissionService {

    Permission getPermissionById(int id);

    Permission getPermissionByName(String name);

    List<Permission> listPermission(int startPage, int pageSize);

    void addPermission(Permission permission);

    void updatePermission(Permission permission);

    void deletePermission(int id);

    int getMaxPermissionId();
}

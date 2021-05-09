package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Permission;
import com.watermelon.api.entity.Role;
import com.watermelon.api.service.PermissionService;
import com.watermelon.api.service.RoleService;
import com.watermelon.mapper.PermissionMapper;
import com.watermelon.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public Role getRoleById(int id) {
        Role role = roleMapper.getRoleById(id);
        role.setPermissions((ArrayList<Permission>) getRolesPermissions(id));
        return role;
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleMapper.getRoleByName(name);
        role.setPermissions((ArrayList<Permission>) getRolesPermissions(role.getId()));
        return role;
    }

    @Override
    public List<Integer> getPermissionsId(int id) {
        return roleMapper.getRolesPermissionsId(id);
    }

    @Override
    public List<Permission> getRolesPermissions(int id) {
        List<Integer> permsIds = roleMapper.getRolesPermissionsId(id);
        List<Permission> list = new ArrayList<>();
        for (Integer i : permsIds){
            list.add(permissionMapper.getPermissionById(i));
        }
        return list;
    }

    @Override
    public IPage<Role> listRole(int startPage, int pageSize) {
        Page<Role> page = new Page<>(startPage,pageSize);
        IPage<Role> list = roleMapper.listRole(page);
        for (Role role : list.getRecords()){
            role.setPermissions((ArrayList<Permission>) getRolesPermissions(role.getId()));
        }
        return list;
    }

    @Override
    public List<Role> listRole() {
        List<Role> list = roleMapper.listRole();
        for (Role role : list){
            role.setPermissions((ArrayList<Permission>) getRolesPermissions(role.getId()));
        }
        return list;
    }

    @Override
    public void addRole(Role role) {
        int number = roleMapper.getMaxRoleId();
        role.setId(number+1);
        roleMapper.addRole(role);
        List<Permission> list = role.getPermissions();
        if (list!=null){
            for (Permission perms : list){
                roleMapper.addRolePermission(role.getId(),perms.getId());
            }
        }
    }

    @Override
    public void updateRole(Role role) {
        Role r = roleMapper.getRoleById(role.getId());
        if (r!=null){
            roleMapper.updateRole(role);
            //删除role_permission表中此角色的所有权限并重新添加
            roleMapper.deleteAllRolePermission(role.getId());
            List<Permission> list = role.getPermissions();
            if (list!=null){
                for (Permission perms : list){
                    roleMapper.addRolePermission(role.getId(),perms.getId());
                }
            }
        }
    }

    @Override
    public void deleteRole(int id) {
        List<Integer> permsIds = roleMapper.getRolesPermissionsId(id);
        for (int i : permsIds){
            roleMapper.deleteRolePermission(id,i);
        }
        roleMapper.deleteRole(id);
    }

    @Override
    public void addRolePermission(int roleId, int permsId) {
        roleMapper.addRolePermission(roleId,permsId);
    }

    @Override
    public void updateRolePermissions(int roleId, String permStr) {
        String[] permStrArray = permStr.split(",");
        //获取新权限id集合
        Set<Integer> newPermsSet = new HashSet<>();
        for (int i=0; i<permStrArray.length; i++) {
            newPermsSet.add(Integer.parseInt(permStrArray[i]));
        }
        //获取旧权限id集合
        List<Permission> permissionList = getRoleById(roleId).getPermissions();
        Set<Integer> oldPermsSet = new HashSet<>();
        for (Permission perm : permissionList) {
            oldPermsSet.add(perm.getId());
        }
        //获取待添加的权限Id
        List<Integer> addPermsList = new ArrayList<>();
        for (int permId : newPermsSet) {
            //若以往含有的权限中不包含该新的权限Id则加入addPermsList
            if (!oldPermsSet.contains(permId)) {
                addPermsList.add(permId);
            }
        }
        //获取待删除的权限ID
        List<Integer> deletePermsList = new ArrayList<>();
        for (int permId : oldPermsSet) {
            //若以往含有的权限中不包含该新的权限Id则加入addPermsList
            if (!newPermsSet.contains(permId)) {
                deletePermsList.add(permId);
            }
        }
        //执行添加权限操作
        for(int addPerm : addPermsList) {
            addRolePermission(roleId,addPerm);
        }
        //执行删除权限操作
        for(int deletePerm : deletePermsList) {
            deleteRolePermission(roleId,deletePerm);
        }
    }

    @Override
    public void deleteRolePermission(int roleId,int permsId) {
        roleMapper.deleteRolePermission(roleId,permsId);
    }

    @Override
    public int getRoleNumber() {
        return roleMapper.getRoleNumber();
    }

    @Override
    public int getMaxRoleId() {
        return roleMapper.getMaxRoleId();
    }
}
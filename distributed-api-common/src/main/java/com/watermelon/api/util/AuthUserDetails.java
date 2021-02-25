package com.watermelon.api.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.watermelon.api.entity.Permission;
import com.watermelon.api.entity.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class AuthUserDetails implements UserDetails {

    private String username;

    private String password;

    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //将用户的角色权限转化为Spring-Security可识别的权限集合并返回
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        List<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions){
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+permission.getPerms());
            grantedAuthorities.add(grantedAuthority);
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<Permission> getPermissions(){
        return role.getPermissions();
    }

}

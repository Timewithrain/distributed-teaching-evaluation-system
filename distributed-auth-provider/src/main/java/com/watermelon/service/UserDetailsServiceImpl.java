package com.watermelon.service;

import com.watermelon.api.util.AuthUserDetails;
import com.watermelon.api.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //伪造测试权限数据，user及admin
        if (username.equals("user")){
            List<Permission> userPerms = new ArrayList<>();
            Permission userPerm = new Permission();
            userPerm.setPerms("user");
            userPerm.setUrl("/user/*");
            userPerms.add(userPerm);

            AuthUserDetails userDetails = new AuthUserDetails();
            userDetails.setUsername("user");
            userDetails.setPassword(passwordEncoder.encode("user"));
            userDetails.setPerms(userPerms);
            return userDetails;
        }

        if (username.equals("admin")){
            List<Permission> adminPerms = new ArrayList<>();
            Permission adminPerm = new Permission();
            adminPerm.setPerms("admin");
            adminPerm.setUrl("/admin/*");
            adminPerms.add(adminPerm);

            AuthUserDetails adminDetails = new AuthUserDetails();
            adminDetails.setUsername("admin");
            adminDetails.setPassword(passwordEncoder.encode("admin"));
            adminDetails.setPerms(adminPerms);
        }
        return null;
    }

}

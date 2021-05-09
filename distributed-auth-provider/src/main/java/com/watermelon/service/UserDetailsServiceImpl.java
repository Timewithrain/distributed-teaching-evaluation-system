//package com.watermelon.service;
//
//import com.watermelon.api.entity.User;
//import com.watermelon.api.util.AuthUserDetails;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private RemoteUserService userService;
//
//
//    /**
//     * 根据用户名获取用户的角色、权限等信息放入UserDetails中并返回
//     * 该方法需要依赖User-Manage-Module，通过RemoteUserService远程获取用户信息资源
//     * @param username
//     * @return AuthUserDetails
//     * @throws UsernameNotFoundException
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //伪造测试权限数据，user及admin
////        if (username.equals("user")){
////            List<Permission> userPerms = new ArrayList<>();
////            Permission userPerm = new Permission();
////            userPerm.setPerms("user");
////            userPerm.setUrl("/user/*");
////            userPerms.add(userPerm);
////
////            AuthUserDetails userDetails = new AuthUserDetails();
////            userDetails.setUsername("user");
////            userDetails.setPassword(passwordEncoder.encode("user"));
////            Role role = new Role();
////            role.setPermissions(userPerms);
////            userDetails.setRole(role);
////            return userDetails;
////        }
//
//        User user = userService.getUserDetailsByName(username);
//        AuthUserDetails userDetails = new AuthUserDetails();
//        userDetails.setUsername(user.getName());
//        userDetails.setPassword(user.getPassword());
//        userDetails.setRole(user.getRole());
//        return userDetails;
//    }
//
//}

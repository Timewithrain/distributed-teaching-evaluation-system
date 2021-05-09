package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.*;
import com.watermelon.api.service.RoleService;
import com.watermelon.api.service.UserService;
import com.watermelon.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private SupervisorMapper supervisorMapper;

    public User login(User user) {
        User userByIdNumber = getUserByIdNumber(user.getIdNumber());
        //通过idNumber进行匹配，若查询成功则返回用户
        if (userByIdNumber!=null&&user.getPassword().equals(userByIdNumber.getPassword())) return userByIdNumber;
        //通过用户名进行匹配，用户可能重名，循环匹配用户密码信息
        List<User> userList = getUserByName(user.getName());
        for (User u : userList) {
            if (user.getPassword().equals(u.getPassword())) return u;
        }
        //用户名及IdNumber均匹配失败则登陆失败
        return null;
    }

    @Override
    public int registerUser(String username,String password){
        int count = userMapper.getMaxUserId();
        User user = new User();
        user.setId(count+1);
        user.setName(username);
        user.setPassword(password);
        //暂不开启密码加密
//        user.setPassword(encodeMD5(password));
        return addUser(user);
    }

    @Override
    public User getUserById(int id) {
        User user = userMapper.getUserById(id);
        if (user!=null) {
            user.setRole(roleMapper.getRoleById(user.getRoleId()));
        }
        return user;
    }

    public User getUserByIdNumber(String idNumber) {
        User user = userMapper.getUserByIdNumber(idNumber);
        if (user!=null) {
            user.setRole(roleMapper.getRoleById(user.getRoleId()));
        }
        return user;
    }

    @Override
    public List<User> getUserByName(String name) {
        List<User> userList = userMapper.getUserByName(name);
        for (User u : userList) {
            if (u!=null){
                u.setRole(roleMapper.getRoleById(u.getRoleId()));
            }
        }
        return userList;
    }

    @Override
    public User getUserDetailsByName(String name) {
        List<User> userList = userMapper.getUserByName(name);
        User user = userList.get(0);
        user.setRole(roleService.getRoleById(user.getRoleId()));
        user.setIdNumber(null);
        return user;
    }

    @Override
    public String getNameById(int id) {
        String name = "";
        User user = userMapper.getUserById(id);
        switch (user.getRoleId()) {
            case 1:
                name = adminMapper.getAdminById(id).getName();
                break;
            case 2:
                name = teacherMapper.getTeacherById(id).getName();
                break;
            case 3:
                name = studentMapper.getStudentById(id).getName();
                break;
            case 4:
                name = supervisorMapper.getSupervisorById(id).getName();
                break;
        }
        return name;
    }

    @Override
    public int addUser(User user) {
        user.setRoleId(user.getRole().getId());
        user.setId(userMapper.getMaxUserId()+1);
        return userMapper.addUser(user);
    }

    @Override
    public int updateUser(User user) {
        User user1 = userMapper.getUserById(user.getId());
        if (user1==null){
            return -1;
        }
        //当检查到用户角色改变时修改数据库中用户的身份
        updateUserInfo(user1, user);
        return userMapper.updateUser(user);
    }

    @Override
    public int deleteUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public List<User> listUser(int startPage, int pageSize) {
        Page<User> page = new Page<>(startPage,pageSize);
        List<User> list = userMapper.listUser(page);
        for (User user : list){
            Role role = roleService.getRoleById(user.getRoleId());
            user.setRole(role);
        }
        return list;
    }

    @Override
    public IPage<User> searchUser(int startPage, int pageSize, String str) {
        Page<User> page = new Page<>(startPage,pageSize);
        IPage<User> list = userMapper.searchUser(page, str);
        for (User user : list.getRecords()){
            Role role = roleService.getRoleById(user.getRoleId());
            user.setRole(role);
        }
        return list;
    }

    /**
     * 获取用户id的最大值
     * @return id
     */
    @Override
    public int getMaxUserId() {
        return userMapper.getMaxUserId();
    }

    /**
     * 获取用户的总数
     * @return number
     */
    @Override
    public int getUserNumber() {
        return userMapper.getUserNumber();
    }

    /**
     * 当新的用户角色和原本角色不同时，修改角色的数据库信息（有待完善级联删除）
     * user1为根据user.id从数据库读取的原有用户数据
     * user为从用户端回传的更新的user数据
     * 当user.role.id与user1.role.id(roleId)不同时表示用户的角色已被更改，需要重新添加用户信息
     * @param user1 数据库原本保留的用户身份信息
     * @param user  回传的新的用户身份信息
     */
    private void updateUserInfo(User user1,User user){
        //当前端回传的角色id与系统已有角色id不同时，则更新用户角色，否则仅更新用户基本信息
        if (user1.getRoleId()!=user.getRole().getId()){
            //删除原有用户信息
            switch (user1.getRoleId()){
                case 1:
                    adminMapper.deleteAdmin(user1.getId());
                    break;
                case 2:
                    teacherMapper.deleteTeacher(user1.getId());
                    break;
                case 3:
                    studentMapper.deleteStudent(user1.getId());
                    break;
                case 4:
                    supervisorMapper.deleteSupervisor(user1.getId());
                    break;
            }
            //添加新的用户信息
            switch (user.getRole().getId()){
                case 1:
                    Admin admin = new Admin(user);
                    adminMapper.addAdmin(admin);
                    break;
                case 2:
                    Teacher teacher = new Teacher(user);
                    teacherMapper.addTeacher(teacher);
                    break;
                case 3:
                    Student student = new Student(user);
                    studentMapper.addStudent(student);
                    break;
                case 4:
                    Supervisor supervisor = new Supervisor(user);
                    supervisorMapper.addSupervisor(supervisor);
                    break;
            }
        }else{
            //当回传的用户角色id与系统id一致时，仅更新用户基本信息，此处更新用户对应角色表中的信息
            switch (user1.getRoleId()){
                case 1:
                    Admin admin = new Admin(user);
                    adminMapper.updateAdmin(admin);
                    break;
                case 2:
                    Teacher teacher = new Teacher(user);
                    teacherMapper.updateTeacher(teacher);
                    break;
                case 3:
                    Student student = new Student(user);
                    studentMapper.updateStudent(student);
                    break;
                case 4:
                    Supervisor supervisor = new Supervisor(user);
                    supervisorMapper.updateSupervisor(supervisor);
                    break;
            }
        }
    }

    /**
     * 使用Shiro提供的Md5Hash类对密码进行加密
     * @param password
     * @return
     */
//    public String encodeMD5(String password){
//        return  new Md5Hash(password).toHex();
//    }

    /**
     * 使用Shiro提供的Md5Hash类对密码进行盐值加密,此处盐值为username
     * @param password
     * @param salt
     * @return
     */
//    public String encodeMD5Salt(String password,String salt){
//        return  new Md5Hash(password,salt,1).toHex();
//    }

}

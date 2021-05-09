package com.watermelon.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.watermelon.api.entity.Class;
import com.watermelon.api.entity.Course;
import com.watermelon.api.entity.Department;
import com.watermelon.api.service.ClassService;
import com.watermelon.api.service.CourseService;
import com.watermelon.api.service.DepartmentService;
import com.watermelon.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ClassService classService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private RemoteUserServiceImpl remoteUserService;

    @Override
    public void addDepartment(Department department) {
        department.setId(departmentMapper.getMaxDepartmentId()+1);
        departmentMapper.addDepartment(department);
    }

    @Override
    public IPage<Department> listDepartment(int startPage, int pageSize) {
        Page<Department> page = new Page<>(startPage,pageSize);
        return departmentMapper.listDepartment(page);
    }

    @Override
    public List<Department> listAllDepartment(){
        return departmentMapper.listDepartment();
    }

    @Override
    public IPage<Department> searchDepartment(int startPage, int pageSize, String str) {
        Page<Department> page = new Page<>(startPage,pageSize);
        IPage<Department> pageList = departmentMapper.searchDepartment(page, str);
        //获取班级、课程、教师、学生的数量
        for (Department department : pageList.getRecords()) {
            addNumberInfo(department);
        }
        return pageList;
    }

    @Override
    public void updateDepartment(Department department) {
        //修改学院信息以及课程中的course_dep学院名称字段信息
        departmentMapper.updateDepartment(department);
    }

    @Override
    public void deleteDepartment(int id) {
        //删除班级同时删除学生
        List<Class> classList = classService.listClassByDepartmentId(id);
        for (Class aClass : classList){
            classService.deleteClass(aClass.getId());
        }
        //删除课程
        courseService.deleteCourseByDepartmentId(id);
        //删除教师
        remoteUserService.deleteTeacherByDepartmentId(id);
        //最后删除学院
        departmentMapper.deleteDepartment(id);
    }

    @Override
    public int getMaxDepartmentId() {
        return departmentMapper.getMaxDepartmentId();
    }

    @Override
    public int getDepartmentNumber() {
        return departmentMapper.getDepartmentNumber();
    }

    /**
     * 获取学院的课程、班级、教师、学生数量
     * @param department
     */
    private void addNumberInfo(Department department) {
        int courseNumber = departmentMapper.getCourseNumberByDepartmentId(department.getId());
        int classNumber = departmentMapper.getClassNumberByDepartmentId(department.getId());
        int teacherNumber = departmentMapper.getTeacherNumberByDepartmentId(department.getId());
        int studentNumber = departmentMapper.getStudentNumberByDepartmentId(department.getId());
        department.setClassNumber(classNumber);
        department.setCourseNumber(courseNumber);
        department.setTeacherNumber(teacherNumber);
        department.setStudentNumber(studentNumber);
    }
}

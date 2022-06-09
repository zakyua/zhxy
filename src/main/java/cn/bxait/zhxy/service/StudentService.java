package cn.bxait.zhxy.service;

import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.pojo.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chen
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-06-08 20:03:00
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Student getAdminById(int intValue);

    IPage<Student> getStudentByOpr(Page<Student> page, Student student);
}

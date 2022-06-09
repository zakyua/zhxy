package cn.bxait.zhxy.service;

import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.pojo.Student;
import cn.bxait.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chen
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-06-08 20:03:09
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher getAdminById(int intValue);

    IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher);
}

package cn.bxait.zhxy.service.impl;

import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.util.MD5;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bxait.zhxy.pojo.Student;
import cn.bxait.zhxy.service.StudentService;
import cn.bxait.zhxy.mapper.StudentMapper;
import org.springframework.stereotype.Service;

/**
* @author chen
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-06-08 20:03:00
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{


    /**
     *  学生登录的请求
     * @param loginForm
     * @return
     */
    @Override
    public Student login(LoginForm loginForm) {

        // 封装查询条件
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        String passWord = MD5.encrypt(loginForm.getPassword());
        queryWrapper.eq("password",passWord);
        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }


    /**
     *  查询学生
     * @param intValue
     * @return
     */
    @Override
    public Student getAdminById(int intValue) {

        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",intValue);

        Student student = baseMapper.selectOne(queryWrapper);
        return student;
    }


    /**
     *  分页带条件查询学生
     * @param page
     * @param student
     * @return
     */
    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        if(null != student){

            String name = student.getName();
            if(!StringUtils.isEmpty(name)){
                queryWrapper.like("name",name);
            }

            String clazzName = student.getClazz_name();
            if(!StringUtils.isEmpty(clazzName)){
                queryWrapper.eq("clazz_name",clazzName);
            }

            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");

        }
        Page<Student> pages = baseMapper.selectPage(page, queryWrapper);
        return pages;
    }
}





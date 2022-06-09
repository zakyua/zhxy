package cn.bxait.zhxy.service.impl;

import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.util.MD5;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bxait.zhxy.pojo.Teacher;
import cn.bxait.zhxy.service.TeacherService;
import cn.bxait.zhxy.mapper.TeacherMapper;
import org.springframework.stereotype.Service;

import java.awt.desktop.QuitEvent;

/**
* @author chen
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-06-08 20:03:09
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    /**
     *  老师登录的方法
     * @param loginForm
     * @return
     */
    @Override
    public Teacher login(LoginForm loginForm) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        String passWord = MD5.encrypt(loginForm.getPassword());
        queryWrapper.eq("password",passWord);
        Teacher teacher = baseMapper.selectOne(queryWrapper);

        return teacher;
    }

    /**
     *  根据id查询老师的方法
     * @param intValue
     * @return
     */
    @Override
    public Teacher getAdminById(int intValue) {
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",intValue);
        Teacher teacher = baseMapper.selectOne(queryWrapper);
        return teacher;
    }

    /**
     * 分页带条件查询
     * @param page
     * @param teacher
     * @return
     */
    @Override
    public IPage<Teacher> getTeachersByOpr(Page<Teacher> page, Teacher teacher) {

        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        if(teacher != null){
            // 教师名称的条件
            String name = teacher.getName();
            if(!StringUtils.isEmpty(name)){
                queryWrapper.like("name",name);
            }
            // 教师班级的条件
            String clazzName = teacher.getClazz_name();
            if(! StringUtils.isEmpty(clazzName)){
                queryWrapper.eq("clazz_name",clazzName);
            }
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");

        }

        Page<Teacher> teacherPage = baseMapper.selectPage(page, queryWrapper);
        return teacherPage;
    }
}





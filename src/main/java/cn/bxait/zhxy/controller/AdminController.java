package cn.bxait.zhxy.controller;

import cn.bxait.zhxy.pojo.Admin;
import cn.bxait.zhxy.pojo.Student;
import cn.bxait.zhxy.pojo.Teacher;
import cn.bxait.zhxy.service.AdminService;
import cn.bxait.zhxy.service.StudentService;
import cn.bxait.zhxy.service.TeacherService;
import cn.bxait.zhxy.util.JwtHelper;
import cn.bxait.zhxy.util.MD5;
import cn.bxait.zhxy.util.Result;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChenCheng
 * @create 2022-06-09 13:40
 */
@Api(tags = "系统管理员控制器")
@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;


    @ApiOperation("修改密码")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public Result updatePwd(@RequestHeader("token") String token,
                            @PathVariable("oldPwd") String oldPwd,
                            @PathVariable("newPwd") String newPwd){
        // 1.验证token是否过期
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            // taken失效
            return Result.fail().message("token失效!");
        }
        // 2.获取当前用户的id
        Long userId = JwtHelper.getUserId(token);
        // 3.获取用户的类型
        Integer userType = JwtHelper.getUserType(token);
        // 4.将明文的密码转换为暗文
        oldPwd = MD5.encrypt(oldPwd);
        newPwd = MD5.encrypt(newPwd);

        // 5.根据用户的类型来判断旧密码是否正确
        if(userType == 1){  // 这是一个管理员用户
            // 5.1封装查询的条件
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Admin admin = adminService.getOne(queryWrapper);
            // 5.1.1查询失败
            if(admin == null){
                return Result.fail().message("原密码输入有误");
            }
            // 5.1.2查询成功,更新这个用户
            admin.setPassword(newPwd);
            adminService.saveOrUpdate(admin);
        }else if(userType == 2){  // 这是一个学生用户
            // 5.2封装查询的条件
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Student student = studentService.getOne(queryWrapper);
            // 5.2.1查询失败
            if(student == null){
                return Result.fail().message("原密码输入有误");
            }
            // 5.2.2查询成功，更新这个用户
            student.setPassword(newPwd);
            studentService.saveOrUpdate(student);
        }else if(userType == 3){   // 这是一个老师用户

            // 5.3封装查询条件
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("id",userId.intValue()).eq("password",oldPwd);
            Teacher teacher = teacherService.getOne(queryWrapper);
            // 5.3.1查询失败
            if(teacher == null){
                return Result.fail().message("原密码输入有误");
            }
            // 5.3.2查询成功，更新用户
            teacher.setPassword(newPwd);
            teacherService.saveOrUpdate(teacher);
        }
        return Result.ok();
    }






    @ApiOperation("分页获取所有Admin信息【带条件】")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              String adminName){
        Page<Admin> page = new Page<>(pageNo, pageSize);
        IPage<Admin> iPage = adminService.getAdmins(page,adminName);
        return Result.ok(iPage);
    }

    @ApiOperation("添加或修改Admin信息")
    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateAdmin(@RequestBody Admin admin){
        // 直接调用服务层的方法
        adminService.saveOrUpdate(admin);
        return Result.ok();
    }

    @ApiOperation("删除Admin信息")
    @DeleteMapping("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids){

        // 直接调用服务层的方法
        adminService.removeByIds(ids);
        return Result.ok();
    }




}

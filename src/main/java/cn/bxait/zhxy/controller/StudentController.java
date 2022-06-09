package cn.bxait.zhxy.controller;

import cn.bxait.zhxy.pojo.Student;
import cn.bxait.zhxy.service.StudentService;
import cn.bxait.zhxy.util.MD5;
import cn.bxait.zhxy.util.Result;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ChenCheng
 * @create 2022-06-09 11:24
 */
@Api(tags = "学生控制器")
@RestController
@RequestMapping("/sms/studentController")
public class StudentController {


    @Autowired
    private StudentService studentService;


    @ApiOperation("增加学生信息")
    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student){
        // 1.对学生的密码进行加密
        String password = student.getPassword();
        if(!StringUtils.isEmpty(password)){
            String encrypt = MD5.encrypt(password);
            student.setPassword(encrypt);
        }
        // 2.添加学生信息,保存到数据库
        studentService.saveOrUpdate(student);
        return Result.ok();

    }


    @ApiOperation("删除一个或者多个学生信息")
    @DeleteMapping("/delStudentById")
    public Result delStudentById(
            @ApiParam("多个学生id的JSON") @RequestBody List<Integer> ids
    ){

        // 1.调用服务层方法
        studentService.removeByIds(ids);
        return Result.ok();
    }

    @ApiOperation("查询学生信息,分页带条件")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentsByOpr(
            @ApiParam("页码数") @PathVariable("pageNo")Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize")Integer pageSize,
            @ApiParam("查询条件转换后端数据模型") Student student
    ){

        // 1.准备分页的条件
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> iPage =  studentService.getStudentByOpr(page,student);
        return Result.ok(iPage);
    }






}

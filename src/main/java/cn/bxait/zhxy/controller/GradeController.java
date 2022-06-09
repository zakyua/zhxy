package cn.bxait.zhxy.controller;

import cn.bxait.zhxy.pojo.Clazz;
import cn.bxait.zhxy.pojo.Grade;
import cn.bxait.zhxy.service.GradeService;
import cn.bxait.zhxy.util.Result;
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
 * @create 2022-06-09 10:36
 */
@Api(tags = "年级控制器")
@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("查询年级信息,分页带条件")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradeByOpr(
            @ApiParam("分页查询页码数") @PathVariable(value = "pageNo") Integer pageNo, // 页码数
            @ApiParam("分页查询页大小") @PathVariable(value = "pageSize")Integer pageSize, // 页大小
           @ApiParam("分页查询模糊匹配班级名") String gradeName) // 模糊查询条件)
    {

        // 1.设置分页的信息
        Page<Grade> page = new Page<>(pageNo,pageSize);
        // 2. 调用服务层方法,传入分页信息,和查询的条件
        IPage<Grade> pageRs = gradeService.getGradeByOpr(page, gradeName);
        return Result.ok(pageRs);
    }

    @ApiOperation("添加或者修改年级信息")
    @PostMapping("/saveOrUpdateGrade")
    public  Result saveOrUpdateGrade(
            @ApiParam("JSON的grade对象转换后台数据模型")@RequestBody Grade grade
    ){
        // 直接调用服务层的方法，有id则插入，没有id则更新
        gradeService.saveOrUpdate(grade);
        return Result.ok();
    }



    @ApiOperation("删除一个或者多个grade信息")
    @DeleteMapping("/deleteGrade")
    public Result deleteGradeById(
            @ApiParam("JSON的年级id集合,映射为后台List<Integer>")@RequestBody List<Integer> ids
    ){

        // 直接调用服务层的方法来批量删除id
        gradeService.removeByIds(ids);
        return null;
    }

    @ApiOperation("获取所有Grade信息")
    @GetMapping("/getGrades")
    public Result getGrades(){

    // 1.查询所有的年级
     List<Grade> grades =   gradeService.getGrades();
     return Result.ok(grades);
    }





}

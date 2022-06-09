package cn.bxait.zhxy.controller;

import cn.bxait.zhxy.pojo.Admin;
import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.pojo.Student;
import cn.bxait.zhxy.pojo.Teacher;
import cn.bxait.zhxy.service.AdminService;
import cn.bxait.zhxy.service.StudentService;
import cn.bxait.zhxy.service.TeacherService;
import cn.bxait.zhxy.util.CreateVerifiCodeImage;
import cn.bxait.zhxy.util.JwtHelper;
import cn.bxait.zhxy.util.Result;
import cn.bxait.zhxy.util.ResultCodeEnum;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.print.attribute.standard.MediaSize;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * @author ChenCheng
 * @create 2022-06-08 18:45
 */
@RestController
@RequestMapping("/sms/system")
public class SystemController {



    @Autowired
    private AdminService adminService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("获取验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifiCodeImage(HttpServletRequest request, HttpServletResponse response){


        // 1.获取图片的验证码
        BufferedImage verifiCodeImage =
                CreateVerifiCodeImage.getVerifiCodeImage();

        // 2.获取验证码的字符串
        /* 方式一:
        char[] verifiCode = CreateVerifiCodeImage.getVerifiCode();
        String code = new String(verifiCode);
        */
        // 方式二：
        String verifiCode = String.valueOf(CreateVerifiCodeImage.getVerifiCode());

        // 3.将验证码的字符串放入请求域
        request.getSession().setAttribute("verifiCode",verifiCode);

        // 4.将验证码图片响应出去
        try {
            ImageIO.write(verifiCodeImage,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    @ApiOperation("登录请求验证")
    @PostMapping("/login")
    public Result login(@RequestBody LoginForm loginForm, HttpServletRequest request){


        // 1.获取请求域中的验证码和表单上的验证码
        String systemVerifiCode = (String) request.getSession().getAttribute("verifiCode");
        String loginVerifiCode = loginForm.getVerifiCode();
        if("".equals(systemVerifiCode)){
        // 2.session中的验证码失效
            return Result.fail().message("验证码失效,请刷新后重试");
        }
        // 3.比较用户提交的验证码和session中保存的验证码
        if(! systemVerifiCode.equalsIgnoreCase(loginVerifiCode)){
            return Result.fail().message("验证码有误,请刷新后重新输入");
        }
        // 4.验证码比较完成，移除session中的验证码信息
        request.getSession().removeAttribute("verifiCode");

        // 5.准备一个Map集合,用户存放响应的信息
        Map<String,Object> map = new HashMap<>();
        // 6.获取用户身份,验证登录的用户信息
        Integer userType = loginForm.getUserType();

        switch (userType){
            case 1: // 管理员身份
                try {
                    Admin login = adminService.longin(loginForm);
                    if(null != login){
                        // 登录成功，将用户id和用户类型转换为token口令,作信息响应给前端
                        String token = JwtHelper.createToken(login.getId().longValue(), 1);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码有误!");
                    }
                    // 成功将map返回
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 失败返回错误信息
                    return Result.fail().message(e.getMessage());
                }

            case 2: // 学生身份
                try {
                    Student login =  studentService.login(loginForm);
                    if(null != login ){
                        // 登录成功 将用户id和用户类型转换为token口令,作信息响应给前端
                        String token = JwtHelper.createToken(login.getId().longValue(), 2);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码有误!");
                    }
                    // 成功将map返回
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 失败返回错误信息
                    return Result.fail().message(e.getMessage());
                }

            case 3: // 教师身份
                try {
                    Teacher login =  teacherService.login(loginForm);
                    if(null != login ){
                        // 登录成功 将用户id和用户类型转换为token口令,作信息响应给前端
                        String token = JwtHelper.createToken(login.getId().longValue(), 3);
                        map.put("token",token);
                    }else {
                        throw  new RuntimeException("用户名或者密码有误!");
                    }
                    // 成功将map返回
                    return Result.ok(map);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 失败返回错误信息
                    return Result.fail().message(e.getMessage());
                }


        }
        // 7.查无此用户,响应失败
        return Result.fail().message("查无此用户");

    }


    @ApiOperation("通过token获取用户信息")
    @GetMapping("/getInfo")
    public Result getUserInfoByToken(HttpServletRequest request, @RequestHeader("token")String token){

        // 1.获取用户请求的token，判断是否失效
        boolean expiration = JwtHelper.isExpiration(token);
        if(expiration){
            // 已过期，响应失败的信息
            return Result.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // 2.从token取出用户的id和用户的类型
        Long userId = JwtHelper.getUserId(token);
        Integer userType = JwtHelper.getUserType(token);
        // 3.准备一个map存放响应的数据
        Map<String,Object> map = new HashMap<>();
        switch (userType){
            case 1: // 管理员
                Admin admin = adminService.getAdminById(userId.intValue());
                map.put("user",admin);
                map.put("userType",1);
                break;
            case 2: // 学生
                Student student = studentService.getAdminById(userId.intValue());
                map.put("user",student);
                map.put("userType",2);
                break;
            case 3: // 老师
                Teacher teacher = teacherService.getAdminById(userId.intValue());
                map.put("user",teacher);
                map.put("userType",3);
                break;
        }
        // 4.将map响应出去
        return Result.ok(map);
    }



    @ApiOperation("头像上传统一入口")
    @PostMapping("/headerImgUpload")
    public Result headerImgUpload(
            @ApiParam("文件二进制数据") @RequestPart("multipartFile") MultipartFile multipartFile
    ){

        // 1.使用uuid随机生成文件名
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        // 2.生成新的文件名字
        String name = multipartFile.getOriginalFilename();
        String filename = uuid.concat(name);
        // 3.生成照片的保存地址
        String portraitPath = "D:/IdeaProjects/zhxy/target/classes/public/upload/".concat(filename);
        // 4.上传文件
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 5.在数据库中保存这个地址
        String headerImg ="upload/"+filename;
        return Result.ok(headerImg);
    }





}

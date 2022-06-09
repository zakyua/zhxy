package cn.bxait.zhxy.service;

import cn.bxait.zhxy.pojo.Admin;
import cn.bxait.zhxy.pojo.LoginForm;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author chen
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-06-08 20:00:35
*/
public interface AdminService extends IService<Admin> {

    Admin longin(LoginForm loginForm);

    Admin getAdminById(int intValue);

    IPage<Admin> getAdmins(Page<Admin> page, String adminName);
}

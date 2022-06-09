package cn.bxait.zhxy.service.impl;

import cn.bxait.zhxy.pojo.LoginForm;
import cn.bxait.zhxy.util.MD5;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bxait.zhxy.pojo.Admin;
import cn.bxait.zhxy.service.AdminService;
import cn.bxait.zhxy.mapper.AdminMapper;
import org.springframework.stereotype.Service;

/**
* @author chen
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-06-08 20:00:35
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{


    /**
     *  管理员的登录
     * @param loginForm
     * @return
     */
    @Override
    public Admin longin(LoginForm loginForm) {

        // 封装queryWrapper
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name",loginForm.getUsername());
        // 将铭文密码加密成密文
        String passWord = MD5.encrypt(loginForm.getPassword());
        queryWrapper.eq("password",passWord);
        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     *  根据id查询出管理员
     * @param intValue
     * @return
     */
    @Override
    public Admin getAdminById(int intValue) {

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",intValue);

        Admin admin = baseMapper.selectOne(queryWrapper);
        return admin;
    }

    /**
     *  分页带条件查询管理员信息
     * @param page
     * @param adminName
     * @return
     */
    @Override
    public IPage<Admin> getAdmins(Page<Admin> page, String adminName) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(adminName)){
            queryWrapper.eq("name",adminName);
        }
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        Page<Admin> adminPage = baseMapper.selectPage(page, queryWrapper);
        return adminPage;
    }
}





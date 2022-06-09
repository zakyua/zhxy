package cn.bxait.zhxy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bxait.zhxy.pojo.Grade;
import cn.bxait.zhxy.service.GradeService;
import cn.bxait.zhxy.mapper.GradeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chen
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-06-08 20:02:33
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(gradeName)){
            queryWrapper.like("name",gradeName);
        }
        // 设置排序规则
        queryWrapper.orderByDesc("id");
        queryWrapper.orderByAsc("name");
        baseMapper.selectPage(page,queryWrapper);
        return null;
    }

    @Override
    public List<Grade> getGrades() {

        // 查询所有的班级不需要条件
        List<Grade> grades = baseMapper.selectList(null);
        return grades;
    }
}





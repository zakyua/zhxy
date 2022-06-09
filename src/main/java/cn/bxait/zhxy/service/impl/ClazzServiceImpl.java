package cn.bxait.zhxy.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.bxait.zhxy.pojo.Clazz;
import cn.bxait.zhxy.service.ClazzService;
import cn.bxait.zhxy.mapper.ClazzMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author chen
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-06-08 20:02:23
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{


    /**
     * 分页带条件查询
     * @param page
     * @param clazz
     * @return
     */
    @Override
    public IPage<Clazz> getClazzsByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();

        if(clazz != null){
            // 1.查询出这个班级的年级名称
            String gradeName = clazz.getGradeName();
            if(!StringUtils.isEmpty(gradeName)){
                queryWrapper.eq("grade_name",gradeName);
            }
            // 2.查询出这个班级的名称
            String clazzName = clazz.getName();
            if(!StringUtils.isEmpty(clazzName)){
                queryWrapper.like("name",clazzName);
            }
            // 3.定义排序的规则
            queryWrapper.orderByDesc("id");
            queryWrapper.orderByAsc("name");
        }

        Page<Clazz> clazzPage = baseMapper.selectPage(page, queryWrapper);
        return clazzPage;
    }

    @Override
    public List<Clazz> getClazzs() {
        List<Clazz> list = baseMapper.selectList(null);
        return list;
    }
}





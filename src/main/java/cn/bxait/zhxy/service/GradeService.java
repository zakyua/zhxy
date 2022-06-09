package cn.bxait.zhxy.service;

import cn.bxait.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author chen
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-06-08 20:02:33
*/
public interface GradeService extends IService<Grade> {

    IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName);

    List<Grade> getGrades();
}

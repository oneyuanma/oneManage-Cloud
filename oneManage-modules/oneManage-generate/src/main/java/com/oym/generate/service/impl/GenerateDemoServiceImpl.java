package com.oym.generate.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.generate.ctrl.vo.GenerateDemoVO;
import com.oym.generate.dao.GenerateDemoDao;
import com.oym.generate.domain.bo.GenerateDemoSaveBO;
import com.oym.generate.domain.bo.GetGenerateDemoPageListBO;
import com.oym.generate.domain.dataobject.GenerateDemoDO;
import com.oym.generate.service.GenerateDemoService;
import com.oym.generate.transform.GenerateDemoTransform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 代码生成工具演示demoservice实现
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Service
public class GenerateDemoServiceImpl extends ServiceImpl<GenerateDemoDao, GenerateDemoDO> implements GenerateDemoService {

    private static final Logger logger = LoggerFactory.getLogger(GenerateDemoServiceImpl.class);

    @Autowired
    private GenerateDemoDao generateDemoDao;

    /**
    * 分页获取代码生成工具演示demo列表
    *
    * @param bo
    * @return
    */
    @Override
    public PageInfo<GenerateDemoVO> pageList(GetGenerateDemoPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 代码生成工具演示demo列表
        GenerateDemoDO generateDemoDO = new GenerateDemoDO();
        generateDemoDO.setDescription(bo.getDescription());
        generateDemoDO.setTime(bo.getTime());
        generateDemoDO.setTitle(bo.getTitle());
        List<GenerateDemoDO> generateDemoDOS = generateDemoDao.list(generateDemoDO);
        // 构建返回分页对象
        PageInfo pageInfo = new PageInfo(generateDemoDOS);
        pageInfo.setList(CollectionUtils.convert(generateDemoDOS, GenerateDemoTransform.INS::transfer));
        return pageInfo;
    }

    /**
     * 代码生成工具演示demo保存
     *
     * @param bo
     * @return
    */
    @Override
    public OymResult<Boolean> save(GenerateDemoSaveBO bo) {
        // 修改
        if (Argument.isNotNull(bo.getId())) {
            return OymResult.success(generateDemoDao.updateById(GenerateDemoTransform.INS.transfer(bo)) > 0);
        }
        // 新增
        return OymResult.success(generateDemoDao.insert(GenerateDemoTransform.INS.transfer(bo)) > 0);
    }

    /**
     * 代码生成工具演示demo删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        return generateDemoDao.deleteById(id) > 0;
    }

}

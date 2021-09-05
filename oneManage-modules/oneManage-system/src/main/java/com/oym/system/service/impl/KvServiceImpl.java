package com.oym.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.system.ctrl.vo.KvVO;
import com.oym.system.dao.KvDao;
import com.oym.system.domain.bo.GetKvPageListBO;
import com.oym.system.domain.bo.KvSaveBO;
import com.oym.system.domain.dataobject.KvDO;
import com.oym.system.service.KvService;
import com.oym.system.transform.KvTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统键值对service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class KvServiceImpl extends ServiceImpl<KvDao, KvDO> implements KvService {

    @Autowired
    private KvDao kvDao;

    /**
     * 空字符
     */
    private static final String NULL_CHARACTRE = "";

    /**
     * 分页获取键值对列表
     *
     * @param bo
     * @return
     */
    @Override
    public PageInfo<KvVO> pageList(GetKvPageListBO bo) {
        // 分页
        PageHelper.startPage(bo.getNowPageIndex(), bo.getPageSize());
        // 键值对列表
        List<KvDO> kvDOS = kvDao.list(new KvDO(bo.getK(), bo.getV()));
        // 构建返回分页对象
        PageInfo pageInfo = new PageInfo(kvDOS);
        pageInfo.setList(CollectionUtils.convert(kvDOS, KvTransform.INS::transfer));
        return pageInfo;
    }

    /**
     * 键值对保存
     *
     * @param bo
     * @return
     */
    @Override
    public OymResult<Boolean> save(KvSaveBO bo) {
        // 修改
        if (Argument.isNotNull(bo.getId())) {
            return OymResult.success(kvDao.updateById(KvTransform.INS.transfer(bo)) > 0);
        }
        // 新增
        return OymResult.success(kvDao.insert(KvTransform.INS.transfer(bo)) > 0);
    }

    /**
     * 键值对删除
     *
     * @param id
     * @return
     */
    @Override
    public Boolean delete(Long id) {
        return kvDao.deleteById(id) > 0;
    }

    /**
     * 根据key获取value
     *
     * @param key
     * @return
     */
    @Override
    public String getValueByKey(String key) {
        QueryWrapper<KvDO> queryWrapper = Wrappers.query();
        queryWrapper.eq("k", key);
        KvDO kvDO = kvDao.selectOne(queryWrapper);
        if (Argument.isNull(kvDO)) {
            return NULL_CHARACTRE;
        }
        return kvDO.getV();
    }
}

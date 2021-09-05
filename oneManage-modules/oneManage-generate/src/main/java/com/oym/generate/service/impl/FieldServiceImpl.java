package com.oym.generate.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oym.commons.base.response.OymResult;
import com.oym.commons.utils.Argument;
import com.oym.commons.utils.CollectionUtils;
import com.oym.generate.ctrl.vo.FieldVO;
import com.oym.generate.dao.FieldDao;
import com.oym.generate.dao.TableFieldDao;
import com.oym.generate.domain.dataobject.FieldDO;
import com.oym.generate.domain.dataobject.TableFieldDO;
import com.oym.generate.domain.dto.FieldDTO;
import com.oym.generate.service.FieldService;
import com.oym.generate.transform.FieldTransform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 字段描述信息service实现
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
@Service
public class FieldServiceImpl extends ServiceImpl<FieldDao, FieldDO> implements FieldService {

    @Autowired
    private TableFieldDao tableFieldDao;
    @Autowired
    private FieldDao fieldDao;

    /**
     * 字段信息描述详情
     *
     * @param tableId
     * @return
     */
    @Override
    public List<FieldVO> detail(Long tableId) {
        List<FieldVO> fieldVOS = new ArrayList<>();
        List<TableFieldDO> tableFieldDOS = tableFieldDao.getByTableId(tableId);
        if (Argument.isEmpty(tableFieldDOS)) {
            return fieldVOS;
        }
        List<FieldDO> fieldDOS = new ArrayList<>();
        // 遍历查询字段信息
        tableFieldDOS.forEach(tableFieldDO -> {
            fieldDOS.add(fieldDao.selectById(tableFieldDO.getFieldId()));
        });
        // 类型转换
        fieldVOS = CollectionUtils.convert(fieldDOS, FieldTransform.INS::transfer);
        // 排序
        fieldVOS = fieldVOS.stream().sorted(Comparator.comparing(FieldVO::getPosition)).collect(Collectors.toList());
        return fieldVOS;
    }

    /**
     * 字段信息描述保存
     *
     * @param fieldDTOList
     * @return
     */
    @Override
    public OymResult<Boolean> save(List<FieldDTO> fieldDTOList) {

        for (FieldDTO field : fieldDTOList) {
            fieldDao.updateById(FieldTransform.INS.transfer(field));
        }
        return OymResult.success(true);
    }

}

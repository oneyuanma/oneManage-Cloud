package com.oym.generate.service;


import com.oym.commons.base.response.OymResult;
import com.oym.generate.ctrl.vo.FieldVO;
import com.oym.generate.domain.dto.FieldDTO;

import java.util.List;

/**
 * 字段描述信息service
 *
 * @author oneyuanma
 * @date 2021/07/17
 */
public interface FieldService {

    /**
     * 字段信息描述详情
     *
     * @param tableId 表描述信息id
     * @return 字段信息列表
     */
    List<FieldVO> detail(Long tableId);

    /**
     * 字段信息描述保存
     *
     * @param fieldDTOList 字段信息列表
     * @return 保存结果
     */
    OymResult<Boolean> save(List<FieldDTO> fieldDTOList);
}

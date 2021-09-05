package com.oym.${mouldName}.service;


import com.github.pagehelper.PageInfo;
import com.oym.commons.base.response.OymResult;
import com.oym.${mouldName}.ctrl.vo.${className}VO;
import com.oym.${mouldName}.domain.bo.Get${className}PageListBO;
import com.oym.${mouldName}.domain.bo.${className}SaveBO;

import java.util.List;


/**
 * ${functionComment}service
 *
 * @author ${author}
 * @Date ${date}
 */
public interface ${className}Service {

    /**
     * 分页获取${functionComment}列表
     *
     * @param bo
     * @return
     */
    PageInfo<${className}VO> pageList(Get${className}PageListBO bo);

    /**
     * ${functionComment}保存
     *
     * @param bo
     * @return
     */
    OymResult<Boolean> save(${className}SaveBO bo);

    /**
     * ${functionComment}删除
     *
     * @param id
     * @return
     */
    Boolean delete(Long id);


}

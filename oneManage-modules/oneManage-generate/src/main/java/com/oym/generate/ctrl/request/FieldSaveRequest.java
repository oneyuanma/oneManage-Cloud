package com.oym.generate.ctrl.request;

import com.oym.commons.base.request.PageAbstractQuery;
import com.oym.generate.domain.dto.FieldDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 字段描述信息保存 request
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
@ApiModel(description = "字段描述信息保存 Request")
public class FieldSaveRequest extends PageAbstractQuery {

    private static final long serialVersionUID = 8218658927355870227L;

    @ApiModelProperty(value = "字段信息集合")
    private List<FieldDTO> fieldDTOList;

}

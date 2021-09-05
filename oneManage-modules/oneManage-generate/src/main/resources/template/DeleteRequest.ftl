package com.oym.${mouldName}.ctrl.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ${functionComment}删除 request
 *
 * @author ${author}
 * @Date ${date}
 */
@Data
@ApiModel(description = "${functionComment}删除 Request")
public class ${className}DeleteRequest {

    private static final long serialVersionUID = 2755995279191171360L;

    @ApiModelProperty(value = "id")
    private Long id;

}

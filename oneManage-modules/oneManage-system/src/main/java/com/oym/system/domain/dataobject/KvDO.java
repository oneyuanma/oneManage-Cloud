package com.oym.system.domain.dataobject;

import com.baomidou.mybatisplus.annotation.TableName;
import com.oym.commons.base.dataobject.BaseDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 键值对实体类
 *
 * @author oneyuanma
 * @date 2021/06/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_kv")
@AllArgsConstructor
@NoArgsConstructor
public class KvDO extends BaseDO implements Serializable {

    private static final long serialVersionUID = -908257093621902519L;

    /**
     * 键
     */
    private String k;
    /**
     * 值
     */
    private String v;

}

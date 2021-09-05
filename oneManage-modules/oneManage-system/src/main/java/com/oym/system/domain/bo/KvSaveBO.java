package com.oym.system.domain.bo;

import com.oym.commons.base.request.AbstractQuery;
import lombok.Data;

/**
 * 键值对保存 BO
 *
 * @author oneyuanma
 * @date 2020/06/25
 */
@Data
public class KvSaveBO extends AbstractQuery {

    private static final long serialVersionUID = 1883605514721169292L;

    private Long id;

    private String k;

    private String v;

    private String remarks;
}

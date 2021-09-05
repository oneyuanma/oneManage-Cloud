package com.oym.commons.base.request;

import lombok.Data;

/**
 * @author oneyuanma
 * @date 2021/06/22
 */
@Data
public abstract class PageAbstractQuery extends AbstractQuery {

    /**
     * 页码,默认从第1页开始计数
     */
    public int nowPageIndex = 1;

    /**
     * 每页记录数,默认:30条
     */
    public int pageSize = 30;

}

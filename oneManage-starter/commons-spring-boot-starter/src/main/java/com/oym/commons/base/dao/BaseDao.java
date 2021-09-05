package com.oym.commons.base.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @author oneyuanma
 * @Date 2021-06-17
 * @Description dao层基础类
 */
@Repository
public interface BaseDao<T> extends BaseMapper<T> {

}

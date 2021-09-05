package com.oym.generate.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.generate.domain.dataobject.GenerateDemoDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 代码生成工具演示demo dao
 *
 * @author oneyuanma
 * @Date 2021/08/12
 */
@Repository
public interface GenerateDemoDao extends BaseDao<GenerateDemoDO> {

	/**
	 * 代码生成工具演示demo列表
	 * @param generateDemo
	 * @return
	 */
	List<GenerateDemoDO> list(GenerateDemoDO generateDemo);

}

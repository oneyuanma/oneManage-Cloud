package com.oym.${mouldName}.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.generate.domain.dataobject.${className}DO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ${functionComment} dao
 *
 * @author ${author}
 * @Date ${date}
 */
@Repository
public interface ${className}Dao extends BaseDao<${className}DO> {

	/**
	 * ${functionComment}列表
	 * @param ${classNameParam}
	 * @return
	 */
	List<${className}DO> list(${className}DO ${classNameParam});

}

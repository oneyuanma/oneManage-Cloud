package com.oym.generate.dao;

import com.oym.commons.base.dao.BaseDao;
import com.oym.generate.domain.dataobject.FieldInfoDO;
import com.oym.generate.domain.dataobject.TableDO;
import com.oym.generate.domain.dataobject.TableInfoDO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 表描述信息dao层
 *
 * @author oneyuanma
 * @date 2020/04/17
 */
@Repository
public interface TableDao extends BaseDao<TableDO> {

    /**
     * 表描述信息列表
     *
     * @param tableDO
     * @return
     */
    List<TableDO> list(TableDO tableDO);

    /**
     * 获取单个表描述信息
     *
     * @param tableDO
     * @return
     */
    TableDO getOne(TableDO tableDO);


    /**
     * 根据数据库名称获取所有表信息
     *
     * @param schema 数据库
     * @return 表信息
     */
    @Select("select table_name, table_comment from information_schema.`TABLES` where TABLE_SCHEMA = #{schema}")
    List<TableInfoDO> getTableInfoBySchema(@Param("schema") String schema);

    /**
     * 根据数据库名称和表名获取表信息
     *
     * @param schema 数据库
     * @return 表信息
     */
    @Select("select table_name, table_comment from information_schema.`TABLES` where TABLE_SCHEMA = #{schema} AND TABLE_NAME = #{table}")
    TableInfoDO getTableInfoBySchemaAndTable(@Param("schema") String schema, @Param("table") String table);

    /**
     * 根据库名和表名获取字段信息
     *
     * @param schema
     * @param table
     * @return
     */
    @Select("select * from information_schema.`COLUMNS` where TABLE_SCHEMA = #{schema} and table_name = #{table}")
    List<FieldInfoDO> getFieldInfoByTable(@Param("schema") String schema, @Param("table") String table);
}

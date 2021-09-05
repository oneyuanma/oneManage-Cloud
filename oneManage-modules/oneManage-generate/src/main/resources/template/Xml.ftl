<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.oym.${mouldName}.dao.${className}Dao">
    <resultMap id="BaseResultMap" type="com.oym.${mouldName}.domain.dataobject.${className}DO">
        <result column="id" jdbcType="BIGINT" property="id" javaType="java.lang.Long"/>
        <result column="status" jdbcType="VARCHAR" property="status" javaType="java.lang.String"/>
        <result column="del_flag" jdbcType="VARCHAR" property="delFlag" javaType="java.lang.String"/>
        <result column="remarks" jdbcType="VARCHAR" property="remarks" javaType="java.lang.String"/>
        <result column="create_date" jdbcType="TIMESTAMP" property="createDate" javaType="java.time.LocalDateTime"/>
        <result column="update_date" jdbcType="TIMESTAMP" property="updateDate" javaType="java.time.LocalDateTime"/>
<#list columns as c>
    <#if c.ifQuery == "true">
        <#if c.columnType == "String">
        <result column="${c.fileName}" jdbcType="VARCHAR" property="${c.doColumnName}" javaType="java.lang.String"/>
        </#if>
        <#if c.columnType == "Integer">
        <result column="${c.fileName}" jdbcType="INTEGER" property="${c.doColumnName}" javaType="java.lang.Integer"/>
        </#if>
        <#if c.columnType == "Long">
        <result column="${c.fileName}" jdbcType="BIGINT" property="${c.doColumnName}" javaType="java.lang.Long"/>
        </#if>
        <#if c.columnType == "Double">
        <result column="${c.fileName}" jdbcType="DOUBLE" property="${c.doColumnName}" javaType="java.lang.Double"/>
        </#if>
        <#if c.columnType == "LocalDateTime">
        <result column="${c.fileName}" jdbcType="TIMESTAMP" property="${c.doColumnName}" javaType="java.time.LocalDateTime"/>
        </#if>
        <#if c.columnType == "LocalDate">
        <result column="${c.fileName}" jdbcType="TIMESTAMP" property="${c.doColumnName}" javaType="java.time.LocalDate"/>
        </#if>
    </#if>
</#list>
    </resultMap>

    <sql id="Base_Column_List">
        create_date, update_date, remarks, del_flag, id <#list columns as c><#if c.ifQuery == "true">,${c.fileName}</#if></#list>
    </sql>

    <!-- 查询条件语句  -->
    <sql id="where_fragment">
        <where>
            1 = 1
            <if test="createDate != null">
                AND create_date = ${r"#{createDate}"}
            </if>
            <if test="id != null and id != ''">
                AND id = ${r"#{id}"}
            </if>
            <#list columns as c>
            <#if c.ifQuery == "true">
                <#if c.queryType == "equal">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} = ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "no_equal">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} != ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "more">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} > ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "more_equal">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} >= ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "less">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} < ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "less_equal">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                AND ${c.fileName} <= ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
                <#if c.queryType == "like">
            <if test="${c.doColumnName} != null and ${c.doColumnName} != ''">
                <bind name="${c.doColumnName}" value="'%' + ${c.doColumnName} + '%'"/>
                AND ${c.fileName} like ${r"#{"}${c.doColumnName}${r"}"}
            </if>
                </#if>
            </#if>
            </#list>
            AND del_flag = 0
        </where>
    </sql>

    <!-- 分页查询 -->
    <select id="list" parameterType="com.oym.${mouldName}.domain.dataobject.${className}DO" resultMap="BaseResultMap">
        SELECT
        <include refid="Base_Column_List"/>
        FROM
        ${tableName}
        <!-- 查询条件 -->
        <include refid="where_fragment"/>
    </select>

</mapper>

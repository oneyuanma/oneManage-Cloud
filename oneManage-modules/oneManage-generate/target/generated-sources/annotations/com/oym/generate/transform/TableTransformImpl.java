package com.oym.generate.transform;

import com.oym.generate.ctrl.request.GetTablePageListRequest;
import com.oym.generate.ctrl.request.TableSaveRequest;
import com.oym.generate.ctrl.vo.TableVO;
import com.oym.generate.domain.bo.GetTablePageListBO;
import com.oym.generate.domain.bo.TableSaveBO;
import com.oym.generate.domain.dataobject.TableDO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T14:34:17+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class TableTransformImpl implements TableTransform {

    @Override
    public GetTablePageListBO transfer(GetTablePageListRequest request) {
        if ( request == null ) {
            return null;
        }

        GetTablePageListBO getTablePageListBO = new GetTablePageListBO();

        getTablePageListBO.setNowPageIndex( request.getNowPageIndex() );
        getTablePageListBO.setPageSize( request.getPageSize() );
        getTablePageListBO.setTableName( request.getTableName() );
        getTablePageListBO.setTableDescription( request.getTableDescription() );
        getTablePageListBO.setAuthor( request.getAuthor() );

        return getTablePageListBO;
    }

    @Override
    public TableDO transfer(TableSaveBO tableSaveBO) {
        if ( tableSaveBO == null ) {
            return null;
        }

        TableDO tableDO = new TableDO();

        tableDO.setId( tableSaveBO.getId() );
        tableDO.setTableName( tableSaveBO.getTableName() );
        tableDO.setTableDescription( tableSaveBO.getTableDescription() );
        tableDO.setAuthor( tableSaveBO.getAuthor() );
        tableDO.setClassName( tableSaveBO.getClassName() );
        tableDO.setModuleName( tableSaveBO.getModuleName() );
        tableDO.setPackagePath( tableSaveBO.getPackagePath() );
        tableDO.setFunctionName( tableSaveBO.getFunctionName() );
        tableDO.setPath( tableSaveBO.getPath() );

        return tableDO;
    }

    @Override
    public TableVO transfer(TableDO tableDO) {
        if ( tableDO == null ) {
            return null;
        }

        TableVO tableVO = new TableVO();

        tableVO.setId( tableDO.getId() );
        tableVO.setTableName( tableDO.getTableName() );
        tableVO.setTableDescription( tableDO.getTableDescription() );
        tableVO.setAuthor( tableDO.getAuthor() );
        tableVO.setClassName( tableDO.getClassName() );
        tableVO.setModuleName( tableDO.getModuleName() );
        tableVO.setPackagePath( tableDO.getPackagePath() );
        tableVO.setFunctionName( tableDO.getFunctionName() );
        tableVO.setPath( tableDO.getPath() );

        return tableVO;
    }

    @Override
    public TableSaveBO transfer(TableSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        TableSaveBO tableSaveBO = new TableSaveBO();

        tableSaveBO.setId( request.getId() );
        tableSaveBO.setTableName( request.getTableName() );
        tableSaveBO.setTableDescription( request.getTableDescription() );
        tableSaveBO.setAuthor( request.getAuthor() );
        tableSaveBO.setClassName( request.getClassName() );
        tableSaveBO.setModuleName( request.getModuleName() );
        tableSaveBO.setPackagePath( request.getPackagePath() );
        tableSaveBO.setFunctionName( request.getFunctionName() );
        tableSaveBO.setPath( request.getPath() );

        return tableSaveBO;
    }
}

package com.oym.generate.transform;

import com.oym.generate.ctrl.request.FieldSaveRequest;
import com.oym.generate.ctrl.vo.FieldVO;
import com.oym.generate.domain.bo.FieldSaveBO;
import com.oym.generate.domain.dataobject.FieldDO;
import com.oym.generate.domain.dto.FieldDTO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T14:34:17+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class FieldTransformImpl implements FieldTransform {

    @Override
    public FieldDO transfer(FieldSaveBO fieldSaveBO) {
        if ( fieldSaveBO == null ) {
            return null;
        }

        FieldDO fieldDO = new FieldDO();

        fieldDO.setId( fieldSaveBO.getId() );
        fieldDO.setFieldName( fieldSaveBO.getFieldName() );
        fieldDO.setFieldDescription( fieldSaveBO.getFieldDescription() );
        fieldDO.setDataType( fieldSaveBO.getDataType() );
        fieldDO.setFieldType( fieldSaveBO.getFieldType() );
        fieldDO.setFieldProperty( fieldSaveBO.getFieldProperty() );
        if ( fieldSaveBO.getIfList() != null ) {
            fieldDO.setIfList( String.valueOf( fieldSaveBO.getIfList() ) );
        }
        if ( fieldSaveBO.getIfEdit() != null ) {
            fieldDO.setIfEdit( String.valueOf( fieldSaveBO.getIfEdit() ) );
        }
        if ( fieldSaveBO.getIfQuery() != null ) {
            fieldDO.setIfQuery( String.valueOf( fieldSaveBO.getIfQuery() ) );
        }
        if ( fieldSaveBO.getIfNull() != null ) {
            fieldDO.setIfNull( String.valueOf( fieldSaveBO.getIfNull() ) );
        }
        fieldDO.setQueryType( fieldSaveBO.getQueryType() );
        fieldDO.setViewType( fieldSaveBO.getViewType() );
        fieldDO.setPosition( fieldSaveBO.getPosition() );

        return fieldDO;
    }

    @Override
    public FieldVO transfer(FieldDO fieldDO) {
        if ( fieldDO == null ) {
            return null;
        }

        FieldVO fieldVO = new FieldVO();

        fieldVO.setId( fieldDO.getId() );
        fieldVO.setFieldName( fieldDO.getFieldName() );
        fieldVO.setFieldDescription( fieldDO.getFieldDescription() );
        fieldVO.setDataType( fieldDO.getDataType() );
        fieldVO.setFieldType( fieldDO.getFieldType() );
        fieldVO.setFieldProperty( fieldDO.getFieldProperty() );
        if ( fieldDO.getIfList() != null ) {
            fieldVO.setIfList( Boolean.parseBoolean( fieldDO.getIfList() ) );
        }
        if ( fieldDO.getIfEdit() != null ) {
            fieldVO.setIfEdit( Boolean.parseBoolean( fieldDO.getIfEdit() ) );
        }
        if ( fieldDO.getIfQuery() != null ) {
            fieldVO.setIfQuery( Boolean.parseBoolean( fieldDO.getIfQuery() ) );
        }
        if ( fieldDO.getIfNull() != null ) {
            fieldVO.setIfNull( Boolean.parseBoolean( fieldDO.getIfNull() ) );
        }
        fieldVO.setQueryType( fieldDO.getQueryType() );
        fieldVO.setViewType( fieldDO.getViewType() );
        fieldVO.setPosition( fieldDO.getPosition() );

        return fieldVO;
    }

    @Override
    public FieldSaveBO transfer(FieldSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        FieldSaveBO fieldSaveBO = new FieldSaveBO();

        return fieldSaveBO;
    }

    @Override
    public FieldDO transfer(FieldDTO fieldDTO) {
        if ( fieldDTO == null ) {
            return null;
        }

        FieldDO fieldDO = new FieldDO();

        fieldDO.setId( fieldDTO.getId() );
        fieldDO.setCreateDate( fieldDTO.getCreateDate() );
        fieldDO.setUpdateDate( fieldDTO.getUpdateDate() );
        fieldDO.setDelFlag( fieldDTO.getDelFlag() );
        fieldDO.setRemarks( fieldDTO.getRemarks() );
        fieldDO.setFieldName( fieldDTO.getFieldName() );
        fieldDO.setFieldDescription( fieldDTO.getFieldDescription() );
        fieldDO.setDataType( fieldDTO.getDataType() );
        fieldDO.setFieldType( fieldDTO.getFieldType() );
        fieldDO.setFieldProperty( fieldDTO.getFieldProperty() );
        if ( fieldDTO.getIfList() != null ) {
            fieldDO.setIfList( String.valueOf( fieldDTO.getIfList() ) );
        }
        if ( fieldDTO.getIfEdit() != null ) {
            fieldDO.setIfEdit( String.valueOf( fieldDTO.getIfEdit() ) );
        }
        if ( fieldDTO.getIfQuery() != null ) {
            fieldDO.setIfQuery( String.valueOf( fieldDTO.getIfQuery() ) );
        }
        if ( fieldDTO.getIfNull() != null ) {
            fieldDO.setIfNull( String.valueOf( fieldDTO.getIfNull() ) );
        }
        fieldDO.setQueryType( fieldDTO.getQueryType() );
        fieldDO.setViewType( fieldDTO.getViewType() );
        fieldDO.setLength( fieldDTO.getLength() );
        fieldDO.setPosition( fieldDTO.getPosition() );

        return fieldDO;
    }
}

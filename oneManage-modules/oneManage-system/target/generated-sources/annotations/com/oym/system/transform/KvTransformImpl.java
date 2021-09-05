package com.oym.system.transform;

import com.oym.system.ctrl.request.KvSaveRequest;
import com.oym.system.ctrl.vo.KvVO;
import com.oym.system.domain.bo.KvSaveBO;
import com.oym.system.domain.dataobject.KvDO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T15:16:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class KvTransformImpl implements KvTransform {

    @Override
    public KvDO transfer(KvSaveBO kvSaveBO) {
        if ( kvSaveBO == null ) {
            return null;
        }

        KvDO kvDO = new KvDO();

        kvDO.setId( kvSaveBO.getId() );
        kvDO.setRemarks( kvSaveBO.getRemarks() );
        kvDO.setK( kvSaveBO.getK() );
        kvDO.setV( kvSaveBO.getV() );

        return kvDO;
    }

    @Override
    public KvVO transfer(KvDO kvDO) {
        if ( kvDO == null ) {
            return null;
        }

        KvVO kvVO = new KvVO();

        kvVO.setId( kvDO.getId() );
        kvVO.setK( kvDO.getK() );
        kvVO.setV( kvDO.getV() );
        kvVO.setRemarks( kvDO.getRemarks() );

        return kvVO;
    }

    @Override
    public KvSaveBO transfer(KvSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        KvSaveBO kvSaveBO = new KvSaveBO();

        kvSaveBO.setId( request.getId() );
        kvSaveBO.setK( request.getK() );
        kvSaveBO.setV( request.getV() );
        kvSaveBO.setRemarks( request.getRemarks() );

        return kvSaveBO;
    }
}

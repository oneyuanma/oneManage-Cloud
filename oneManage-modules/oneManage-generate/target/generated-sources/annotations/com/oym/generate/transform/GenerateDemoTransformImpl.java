package com.oym.generate.transform;

import com.oym.generate.ctrl.request.GenerateDemoSaveRequest;
import com.oym.generate.ctrl.request.GetGenerateDemoPageListRequest;
import com.oym.generate.ctrl.vo.GenerateDemoVO;
import com.oym.generate.domain.bo.GenerateDemoSaveBO;
import com.oym.generate.domain.bo.GetGenerateDemoPageListBO;
import com.oym.generate.domain.dataobject.GenerateDemoDO;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T14:34:17+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class GenerateDemoTransformImpl implements GenerateDemoTransform {

    @Override
    public GetGenerateDemoPageListBO transfer(GetGenerateDemoPageListRequest request) {
        if ( request == null ) {
            return null;
        }

        GetGenerateDemoPageListBO getGenerateDemoPageListBO = new GetGenerateDemoPageListBO();

        getGenerateDemoPageListBO.setNowPageIndex( request.getNowPageIndex() );
        getGenerateDemoPageListBO.setPageSize( request.getPageSize() );
        getGenerateDemoPageListBO.setDescription( request.getDescription() );
        getGenerateDemoPageListBO.setTime( request.getTime() );
        getGenerateDemoPageListBO.setTitle( request.getTitle() );

        return getGenerateDemoPageListBO;
    }

    @Override
    public GenerateDemoDO transfer(GenerateDemoSaveBO generateDemoSaveBO) {
        if ( generateDemoSaveBO == null ) {
            return null;
        }

        GenerateDemoDO generateDemoDO = new GenerateDemoDO();

        generateDemoDO.setId( generateDemoSaveBO.getId() );
        generateDemoDO.setDescription( generateDemoSaveBO.getDescription() );
        generateDemoDO.setTime( generateDemoSaveBO.getTime() );
        generateDemoDO.setTitle( generateDemoSaveBO.getTitle() );

        return generateDemoDO;
    }

    @Override
    public GenerateDemoVO transfer(GenerateDemoDO generateDemoDO) {
        if ( generateDemoDO == null ) {
            return null;
        }

        GenerateDemoVO generateDemoVO = new GenerateDemoVO();

        generateDemoVO.setId( generateDemoDO.getId() );
        generateDemoVO.setDescription( generateDemoDO.getDescription() );
        generateDemoVO.setTime( generateDemoDO.getTime() );
        generateDemoVO.setTitle( generateDemoDO.getTitle() );

        return generateDemoVO;
    }

    @Override
    public GenerateDemoSaveBO transfer(GenerateDemoSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        GenerateDemoSaveBO generateDemoSaveBO = new GenerateDemoSaveBO();

        generateDemoSaveBO.setDescription( request.getDescription() );
        generateDemoSaveBO.setTime( request.getTime() );
        generateDemoSaveBO.setTitle( request.getTitle() );

        return generateDemoSaveBO;
    }
}

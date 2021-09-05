package com.oym.system.transform;

import com.oym.system.ctrl.request.ResourceSaveRequest;
import com.oym.system.ctrl.vo.ResourceAssignmentTreeVO;
import com.oym.system.ctrl.vo.ResourceSelectTreeVO;
import com.oym.system.ctrl.vo.ResourceTreeVO;
import com.oym.system.domain.bo.ResourceSaveBO;
import com.oym.system.domain.dataobject.ResourceDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-09-05T15:16:47+0800",
    comments = "version: 1.2.0.Final, compiler: javac, environment: Java 1.8.0_211 (Oracle Corporation)"
)
@Component
public class ResourceTransformImpl implements ResourceTransform {

    @Override
    public ResourceTreeVO transfer(ResourceDO resourceDO) {
        if ( resourceDO == null ) {
            return null;
        }

        ResourceTreeVO resourceTreeVO = new ResourceTreeVO();

        resourceTreeVO.setId( resourceDO.getId() );
        resourceTreeVO.setParentId( resourceDO.getParentId() );
        resourceTreeVO.setPath( resourceDO.getPath() );
        resourceTreeVO.setUrl( resourceDO.getUrl() );
        resourceTreeVO.setComponent( resourceDO.getComponent() );
        resourceTreeVO.setCode( resourceDO.getCode() );
        resourceTreeVO.setTitle( resourceDO.getTitle() );
        resourceTreeVO.setLevel( resourceDO.getLevel() );
        resourceTreeVO.setSortNo( resourceDO.getSortNo() );
        resourceTreeVO.setIcon( resourceDO.getIcon() );
        resourceTreeVO.setType( resourceDO.getType() );

        return resourceTreeVO;
    }

    @Override
    public ResourceSaveBO transfer(ResourceSaveRequest request) {
        if ( request == null ) {
            return null;
        }

        ResourceSaveBO resourceSaveBO = new ResourceSaveBO();

        resourceSaveBO.setId( request.getId() );
        resourceSaveBO.setParentId( request.getParentId() );
        resourceSaveBO.setPath( request.getPath() );
        resourceSaveBO.setUrl( request.getUrl() );
        resourceSaveBO.setComponent( request.getComponent() );
        resourceSaveBO.setCode( request.getCode() );
        resourceSaveBO.setTitle( request.getTitle() );
        resourceSaveBO.setLevel( request.getLevel() );
        resourceSaveBO.setSortNo( request.getSortNo() );
        resourceSaveBO.setIcon( request.getIcon() );
        resourceSaveBO.setType( request.getType() );

        return resourceSaveBO;
    }

    @Override
    public ResourceDO transfer(ResourceSaveBO bo) {
        if ( bo == null ) {
            return null;
        }

        ResourceDO resourceDO = new ResourceDO();

        resourceDO.setId( bo.getId() );
        resourceDO.setParentId( bo.getParentId() );
        resourceDO.setPath( bo.getPath() );
        resourceDO.setUrl( bo.getUrl() );
        resourceDO.setComponent( bo.getComponent() );
        resourceDO.setCode( bo.getCode() );
        resourceDO.setTitle( bo.getTitle() );
        resourceDO.setLevel( bo.getLevel() );
        resourceDO.setSortNo( bo.getSortNo() );
        resourceDO.setIcon( bo.getIcon() );
        resourceDO.setType( bo.getType() );

        return resourceDO;
    }

    @Override
    public ResourceTreeVO transfer2TreeVO(ResourceDO resourceDO) {
        if ( resourceDO == null ) {
            return null;
        }

        ResourceTreeVO resourceTreeVO = new ResourceTreeVO();

        resourceTreeVO.setId( resourceDO.getId() );
        resourceTreeVO.setParentId( resourceDO.getParentId() );
        resourceTreeVO.setPath( resourceDO.getPath() );
        resourceTreeVO.setUrl( resourceDO.getUrl() );
        resourceTreeVO.setComponent( resourceDO.getComponent() );
        resourceTreeVO.setCode( resourceDO.getCode() );
        resourceTreeVO.setTitle( resourceDO.getTitle() );
        resourceTreeVO.setLevel( resourceDO.getLevel() );
        resourceTreeVO.setSortNo( resourceDO.getSortNo() );
        resourceTreeVO.setIcon( resourceDO.getIcon() );
        resourceTreeVO.setType( resourceDO.getType() );

        return resourceTreeVO;
    }

    @Override
    public ResourceSelectTreeVO transfer(ResourceTreeVO vo) {
        if ( vo == null ) {
            return null;
        }

        ResourceSelectTreeVO resourceSelectTreeVO = new ResourceSelectTreeVO();

        resourceSelectTreeVO.setName( vo.getTitle() );
        resourceSelectTreeVO.setResId( vo.getId() );
        resourceSelectTreeVO.setParentId( vo.getParentId() );
        resourceSelectTreeVO.setChildren( resourceTreeVOListToResourceSelectTreeVOList( vo.getChildren() ) );

        return resourceSelectTreeVO;
    }

    @Override
    public ResourceAssignmentTreeVO transfer2TreeVO(ResourceTreeVO vo) {
        if ( vo == null ) {
            return null;
        }

        ResourceAssignmentTreeVO resourceAssignmentTreeVO = new ResourceAssignmentTreeVO();

        resourceAssignmentTreeVO.setId( vo.getId() );
        resourceAssignmentTreeVO.setLabel( vo.getTitle() );
        resourceAssignmentTreeVO.setChildren( resourceTreeVOListToResourceAssignmentTreeVOList( vo.getChildren() ) );

        return resourceAssignmentTreeVO;
    }

    protected List<ResourceSelectTreeVO> resourceTreeVOListToResourceSelectTreeVOList(List<ResourceTreeVO> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceSelectTreeVO> list1 = new ArrayList<ResourceSelectTreeVO>( list.size() );
        for ( ResourceTreeVO resourceTreeVO : list ) {
            list1.add( transfer( resourceTreeVO ) );
        }

        return list1;
    }

    protected List<ResourceAssignmentTreeVO> resourceTreeVOListToResourceAssignmentTreeVOList(List<ResourceTreeVO> list) {
        if ( list == null ) {
            return null;
        }

        List<ResourceAssignmentTreeVO> list1 = new ArrayList<ResourceAssignmentTreeVO>( list.size() );
        for ( ResourceTreeVO resourceTreeVO : list ) {
            list1.add( transfer2TreeVO( resourceTreeVO ) );
        }

        return list1;
    }
}

package com.oym.commons.base.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * mybatis plus 默认字段自动填充配置
 *
 * @author oneyuanma
 * @date 2020/07/03
 */
@Component
public class OymMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "createDate", LocalDateTime.now());
        this.fillStrategy(metaObject, "updateDate", LocalDateTime.now());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "updateDate", LocalDateTime.now());
    }
}

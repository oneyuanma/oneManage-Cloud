package com.oym.commons.config;

import com.oym.commons.utils.Argument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * 自定义环境处理，在运行SpringApplication之前加载任意配置文件到Environment环境中
 *
 * @author oneyuanma
 * @date 2021/07/15
 */
@Component
public class OymEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private Logger logger = LoggerFactory.getLogger(OymEnvironmentPostProcessor.class);

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String[] profiles = {
                "config/application-cloud.yml",
                "config/application-db.yml",
                "config/application-kafka.yml",
                "config/application-common.yml",
        };

        for (String profile : profiles) {
            try {
                environment.getPropertySources().addLast(loadProfiles(profile));
            } catch (Exception ex) {
                logger.error("加载配置文件失败" + profile, ex);
            }
        }

    }

    /**
     * 加载单个配置文件
     *
     * @param profile
     * @return
     */
    private PropertiesPropertySource loadProfiles(String profile) {
        if (Argument.isBlank(profile)) {
            logger.error("配置文件：" + profile + "不存在");
        }
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        ClassPathResource classPathResource = new ClassPathResource(profile);
        yaml.setResources(classPathResource);
        Properties properties = yaml.getObject();
        return new PropertiesPropertySource(profile, properties);
    }

}

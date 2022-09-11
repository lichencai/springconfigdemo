package lcc.config.starter.config;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.bootstrap.config.PropertySourceLocator;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.Collection;

@Slf4j
@Order(0)
public class MyPropertySourceLocator implements PropertySourceLocator {

    @Override
    public PropertySource<?> locate(Environment environment) {


        log.info("获取到profile的配置为:{}, 应用名称为:{}",
                JSONUtil.toJsonStr(environment.getActiveProfiles()), environment.getProperty("spring.application.name"));

        return null;
    }

    @Override
    public Collection<PropertySource<?>> locateCollection(Environment environment) {
        return PropertySourceLocator.super.locateCollection(environment);
    }
}

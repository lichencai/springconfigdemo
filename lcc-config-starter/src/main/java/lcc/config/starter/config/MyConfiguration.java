package lcc.config.starter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class MyConfiguration {

    @Bean
    public MyPropertySourceLocator myPropertySourceLocator(){
        return new MyPropertySourceLocator();
    }

}

package lcc.config.starter.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MyConfigurationVo {


    @Value("${syncApiAddress}")
    private String syncApiAddress;   // 如果跟application-uat配置相同则会进行覆盖


    @Value("${spring.kafka.consumer.properties.group.id}")
    private String myEnvProfile;
}

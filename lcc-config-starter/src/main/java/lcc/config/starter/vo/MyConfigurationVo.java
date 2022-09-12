package lcc.config.starter.vo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class MyConfigurationVo {


    @Value("${my.env.test}")
    private String myEnvTest;   // 如果跟application-uat配置相同则会进行覆盖


    @Value("${my.env.profile}")
    private String myEnvProfile;
}

package lcc.config.starter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    @Value("${my.env.test}")
    private String myEnvTest;   // 如果跟application-uat配置相同则会进行覆盖

    @Value("${my.profile.test}")
    private String myProfileTest;

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("读取配置文件env.properties:{}", myEnvTest);
        log.info("读取配置文件application-uat.yml:{}", myProfileTest);
    }
}

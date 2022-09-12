package lcc.config.starter;

import cn.hutool.json.JSONUtil;
import lcc.config.starter.vo.MyConfigurationVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class App implements CommandLineRunner {

    @Autowired
    private MyConfigurationVo myConfigurationVo;

    public static void main(String[] args){
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("加载完成后的配置文件:{}", JSONUtil.toJsonStr(myConfigurationVo));
    }
}

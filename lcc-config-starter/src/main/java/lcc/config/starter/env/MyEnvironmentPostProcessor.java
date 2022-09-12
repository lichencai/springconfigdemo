package lcc.config.starter.env;

import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Properties;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor, ApplicationListener<ApplicationEvent> {

    /**
     * 这个时候Log系统还没有初始化  使用DeferredLog来记录  并在onApplicationEvent进行回放
     * */
    private static final DeferredLog log = new DeferredLog();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("读取到application.name:" + environment.getProperty("spring.application.name")
                        + " 和 profiles.active:" + environment.getProperty("spring.profiles.active"));

        String name = environment.getProperty("spring.application.name");
        String active = environment.getProperty("spring.profiles.active");

        new LoadFromGitService().load(name, active);

        String[] profiles = {"remote-application-uat.yml", "env.properties"};
        for (String profile : profiles) {
            //从classpath路径下面查找文件
            Resource resource = new ClassPathResource(profile);
            //加载成PropertySource对象，并添加到Environment环境中
            environment.getPropertySources().addFirst(loadProfiles(resource));      // 先加载先有效
        }
    }

    //加载单个配置文件
    private PropertySource<?> loadProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("资源" + resource + "不存在");
        }
        if(resource.getFilename().contains(".yml")){
            return loadYaml(resource);
        } else {
            return loadProperty(resource);
        }
    }

    /**
     * 加载properties格式的配置文件
     * @param resource
     * @return
     */
    private PropertySource loadProperty(Resource resource){
        try {
            //从输入流中加载一个Properties对象
            Properties properties = new Properties();
            properties.load(resource.getInputStream());
            return new PropertiesPropertySource(resource.getFilename(), properties);
        }catch (Exception ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }

    /**
     * 加载yml格式的配置文件
     * @param resource
     * @return
     */
    private PropertySource loadYaml(Resource resource){
        try {
            YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
            factory.setResources(resource);
            //从输入流中加载一个Properties对象
            Properties properties = factory.getObject();
            return new PropertiesPropertySource(resource.getFilename(), properties);
        }catch (Exception ex) {
            throw new IllegalStateException("加载配置文件失败" + resource, ex);
        }
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        log.replayTo(MyEnvironmentPostProcessor.class);
    }
}

package lcc.config.starter.env;

import cn.hutool.core.io.FileUtil;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor, ApplicationListener<ApplicationEvent> {

    /**
     * 这个时候Log系统还没有初始化  使用DeferredLog来记录  并在onApplicationEvent进行回放
     * */
    private static final DeferredLog log = new DeferredLog();

    private String cache = ".cache/";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        log.info("读取到system.id:" + environment.getProperty("system.id")
                        + " 和 profiles.active:" + environment.getProperty("spring.profiles.active"));

        String name = environment.getProperty("system.id");
        String active = environment.getProperty("spring.profiles.active");
        try{
            new LoadFromGitService().load(name, active);
        }catch (Exception e){
            log.error("加载远程配置文件失败,实行加载本地缓存配置", e);
        }

        List<String> fileList = findLoadFile(name, active);
        for (String filePath : fileList){
            //从classpath路径下面查找文件
            Resource resource = new ClassPathResource(filePath);
            //加载成PropertySource对象，并添加到Environment环境中
            environment.getPropertySources().addFirst(loadProfiles(resource));      // 先加载先有效
        }
    }

    private List<String> findLoadFile(String systemId, String active){
        List<String> fileList = new ArrayList<>();
        String filePath1 = cache + systemId + ".yml";
        if(FileUtil.exist(filePath1)){
            fileList.add(filePath1);
        }
        String filePath = cache + systemId + ".properties";
        if(FileUtil.exist(filePath)){
            fileList.add(filePath);
        }
        String filePath3 = cache + systemId + "-" + active + ".yml";
        if(FileUtil.exist(filePath3)){
            fileList.add(filePath3);
        }
        String filePath2 = cache + systemId + "-" + active + ".properties";
        if(FileUtil.exist(filePath2)){
            fileList.add(filePath2);
        }

        return fileList;
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

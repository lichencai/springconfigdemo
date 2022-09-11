package lcc.config.starter.config;

import org.springframework.core.env.MapPropertySource;

import java.util.Map;

public class MyMapPropertySource extends MapPropertySource {
    public MyMapPropertySource(String name, Map<String, Object> source) {
        super(name, source);
    }
}

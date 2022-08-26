package lcc.test.config.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @author lichencai
 * @date 2022/7/26 16:39
 */
@Data
@Component
@RefreshScope
public class GitConfig {

    @Value("${data.env}")
    private String env;

    @Value("${data.default}")
    private String defaultValue;

    @Value("${data.local}")
    private String dataLocal;

    @Override
    public String toString() {
        return "GitConfig{" +
                "env='" + env + '\'' +
                ", defaultValue='" + defaultValue + '\'' +
                ", dataLocal='" + dataLocal + '\'' +
                '}';
    }
}

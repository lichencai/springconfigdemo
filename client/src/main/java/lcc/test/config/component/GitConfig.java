package lcc.test.config.component;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lichencai
 * @date 2022/7/26 16:39
 */
@Data
@Component
public class GitConfig {

    @Value("${data.env}")
    private String env;

}

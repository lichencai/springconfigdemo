package lcc.test.config.controller;

import cn.hutool.json.JSONUtil;
import lcc.test.config.component.GitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lichencai
 * @date 2022/7/26 16:43
 */
@RestController
public class TestController {

    @Autowired
    private GitConfig gitConfig;

    @GetMapping(value = "show")
    public String show(){
        return JSONUtil.toJsonStr(gitConfig);
    }


}

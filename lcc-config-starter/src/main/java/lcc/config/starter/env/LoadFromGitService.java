package lcc.config.starter.env;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.core.io.Resource;

import java.nio.charset.Charset;

public class LoadFromGitService {


    private String baseUrl = "http://10.2.35.11:6000/";
    private String label = "main";
    private String cache = ".cache/";

    public Resource load(String name, String active){

        String reqUrl = baseUrl + name + "/" + active + "/" + label;

        String result = HttpUtil.get(reqUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray jsonArray = jsonObject.getJSONArray("propertySources");
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject each = JSONUtil.parseObj(jsonArray.get(i));
            String fileName = each.get("name").toString();
            String resource = each.get("source").toString();

            String cacheFileName = fileName.substring(fileName.lastIndexOf('/') + 1);

            FileUtil.writeString(resource, cache + cacheFileName, Charset.defaultCharset());

        }


        return null;


    }


}

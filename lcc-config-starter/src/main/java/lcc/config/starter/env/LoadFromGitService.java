package lcc.config.starter.env;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class LoadFromGitService {


    private String baseUrl = "http://localhost:6000/myenv/getFile";
    private String gitBaseUrl = "http://localhost:6000/myenv/env/";
    private String label = "main";
    private String cache = ".cache/";

    public void load(String name, String active) throws UnsupportedEncodingException {

        List<String> filePaths = requestGitRepository(name, active, label);
        for(String filePath : filePaths){
            String url = baseUrl + "?filePath=" + URLEncoder.encode(filePath, "UTF-8");;
            HttpRequest httpRequest = HttpUtil.createGet(url);
            HttpResponse httpResponse = httpRequest.execute();
            httpResponse.bodyStream();
            String cacheFilePath = cache + filePath.substring(filePath.lastIndexOf('\\'));
            FileUtil.writeFromStream(httpResponse.bodyStream(), cacheFilePath);
        }

    }



    public List<String> requestGitRepository(String systemId, String profile, String label){

        String reqUrl = gitBaseUrl + systemId + "/" + profile + "/" + label;
        String result = HttpUtil.get(reqUrl);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        JSONArray jsonArray = jsonObject.getJSONArray("propertySources");
        List<String> files = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++){
            JSONObject each = JSONUtil.parseObj(jsonArray.get(i));
            String fileName = each.get("name").toString();
            String cacheFileName = fileName.substring(fileName.lastIndexOf("file:") + 5);
            files.add(cacheFileName);
        }

        return files;
    }




}

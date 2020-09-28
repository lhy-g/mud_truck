package com.tc.core.handler;

import cn.hutool.core.io.resource.InputStreamResource;
import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.tc.core.constant.FileBody;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FileHandler {

	//private String UPLOAD_PATH = "http://101.200.155.232:8080/group1/upload";
    private String UPLOAD_PATH = "http://localhost:8080/group1/upload";

    public FileBody upload(InputStreamResource inputStreamResource){
        Map<String, Object> params = new HashMap<>();
        params.put("file", inputStreamResource);
        params.put("path", "image");
        params.put("output", "json");
        String resp = HttpUtil.post(UPLOAD_PATH, params);
        Console.log("resp: {}", resp);
        return JSONObject.parseObject(resp, FileBody.class);
    }
}

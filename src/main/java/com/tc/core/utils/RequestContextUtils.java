package com.tc.core.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * @author
 * @date 2019/11/7
 */
@Slf4j
public class RequestContextUtils {

    /**
     * @return 当前Request对象
     */
    public static HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    public static Page getPage() {
        return new Page(Long.valueOf(getCurrentRequest().getParameter("current")), Long.valueOf(getCurrentRequest().getParameter("size")));
    }

    public static JSONObject getBody() {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try {
            br = getCurrentRequest().getReader();
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
            br.close();
        } catch (IOException e) {
            log.error("getBody error", e);
        } finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    log.error("getBody error", e);
                }
            }
        }
        return JSONObject.parseObject(sb.toString());
    }

}

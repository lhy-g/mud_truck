package com.tc.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.tc.core.annotation.OpenMapping;
import com.tc.core.constant.JwtConst;
import com.tc.core.constant.ResEnum;
import com.tc.core.exception.BizException;
import com.tc.core.utils.AdminContextHolder;
import com.tc.core.utils.JwtUtils;
import com.tc.core.web.responce.AdminVO;
import com.tc.core.web.responce.R;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

@Component
public class AdminAuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(OpenMapping.class)) {
            OpenMapping openMapping = method.getAnnotation(OpenMapping.class);
            if (openMapping.required()) {
                return true;
            }
        }

        String token = request.getHeader(JwtConst.HTTP_HEADER_KEY);
        if (StringUtils.isEmpty(token)) {
            response.getWriter().print(JSON.toJSONString(R.fail(ResEnum.UNAUTHORIZED)));
            return false;
        }
        Claims claims = JwtUtils.getClaimByToken(token);
        if(claims == null){
            throw new BizException(ResEnum.UNAUTHORIZED);
        }
        AdminVO admin = JSON.parseObject(claims.get("admin").toString(), AdminVO.class);
        AdminContextHolder.set(admin);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}

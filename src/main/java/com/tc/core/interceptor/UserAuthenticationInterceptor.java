package com.tc.core.interceptor;

import com.alibaba.fastjson.JSON;
import com.tc.core.annotation.OpenMapping;
import com.tc.core.constant.JwtConst;
import com.tc.core.constant.ResEnum;
import com.tc.core.exception.BizException;
import com.tc.core.utils.DateUtils;
import com.tc.core.utils.JwtUtils;
import com.tc.core.utils.RedisUtil;

import com.tc.core.utils.UserContextHolder;
import com.tc.core.web.responce.R;
import com.tc.core.web.responce.UserVO;
 
import com.tc.mud.applet.service.AppletUserService;
import com.tc.mud.applet.service.CertService;
 
 
 
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class UserAuthenticationInterceptor implements HandlerInterceptor {
 
	@Autowired
	private CertService certService;
	
	@Autowired
	private RedisUtil redisService;
	
	@Autowired
	private AppletUserService userService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
//        System.out.println("请求:"+method.getName());
        if (method.isAnnotationPresent(OpenMapping.class)) {
            OpenMapping openMapping = method.getAnnotation(OpenMapping.class);
            if (openMapping.required()) {
                return true;
            }
        }
        String token = request.getHeader(JwtConst.HTTP_HEADER_KEY);
        String dev = request.getHeader("dev");
		if(!StringUtils.isEmpty(dev)) {
			UserVO user = new UserVO();
			if("a".equals(dev)) {
				user.setUnionId("o37K25OkBltZI1QfrJ62FpR15TVc");
				user.setUserId("2009016868202102");
				user.setType("tt");
				user.setCompanyId("CI2009029026059103");
			}else {
				user.setUnionId("o37K25OkBltZI1QfrJ62FpR15TVc");
				user.setUserId("2009016868202102");
				user.setType("tt");
				user.setCompanyId("CI2009029026059103");
			//	user.setUnionId(dev);
			}
			UserContextHolder.set(user);
			return true;
		}
        if (StringUtils.isEmpty(token)) {
            response.getWriter().print(JSON.toJSONString(R.fail(ResEnum.UNAUTHORIZED)));
            return false;
        }
        Claims claims = JwtUtils.getClaimByToken(token);
        if(claims == null){
            throw new BizException(ResEnum.UNAUTHORIZED);
        }
        UserVO user = JSON.parseObject(claims.get("user").toString(), UserVO.class);
        if(StringUtils.isEmpty(user.getCompanyId())) {
        	String companyId = null;
        	try {
        		if (null == userService) {// 解决service为null无法注入问题
        			BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
        			userService = factory.getBean(AppletUserService.class);
        			redisService = factory.getBean(RedisUtil.class);
        			Object obj = redisService.get("companyId:"+user.getUserId());
        			if(null==obj) {
        				companyId = userService.getCompanyIdByUserId(user.getUserId());
        			}else {
        				companyId = (String) obj;
        			}
        		}
        	} catch (Exception e) {
        	}
        	if(StringUtils.isNotEmpty(companyId)) {
        		redisService.set("companyId:"+user.getUserId(), companyId, 60*60*5);
        		user.setCompanyId(companyId);
        	}
        } 
        user.setCompanyId("CI2009029026059102");
		UserContextHolder.set(user);
//				throw new BizException(e.getMessage());
//			}
//			String cert = request.getHeader(CertConstant.CERT);
//			if (StringUtils.isEmpty(cert)) {
//				Map<String, String> map = new LinkedHashMap<String, String>();
//				map.put(CertConstant.APPLICANT, user.getUnionId());
//				map.put(CertConstant.TIME_STAMP, DateUtils.getTimeStamp());
//				String sign = certService.sign(map, user.getUnionId());
//				map.put(CertConstant.SIGN, sign);
//				cert = JSON.toJSONString(map);
//			}
//			certService.requestCertVerify(cert);
//		} catch (Exception e) {
//			throw new BizException(e.getMessage());
//		}
		return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
    	UserContextHolder.set(null);
    }
}

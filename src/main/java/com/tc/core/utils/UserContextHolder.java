package com.tc.core.utils;

import com.tc.core.web.responce.UserVO;
 
 
import org.springframework.core.NamedThreadLocal;

/**
 * @author
 * @date 2019/11/7
 */
public class UserContextHolder {

    private  static final ThreadLocal<UserVO> userContextHolder = new NamedThreadLocal("User");

    public static UserVO get() {
        return userContextHolder.get();
    }
    
    public static String getUnionId() {
        return userContextHolder.get().getUnionId();
    }
    
    public static String getUserId() {
        return userContextHolder.get().getUserId();
    }
    
    public static String getCompanyId() {
        return userContextHolder.get().getCompanyId();
    }

    public static void set(UserVO userVo) {
        userContextHolder.set(userVo);
    }

    public static void remove() {
        userContextHolder.remove();
    }

}

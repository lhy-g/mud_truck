package com.tc.core.utils;

import com.tc.core.web.responce.AdminVO;
 

import org.springframework.core.NamedThreadLocal;

/**
 * @author
 * @date 2019/11/7
 */
public class AdminContextHolder {

    private static final ThreadLocal<AdminVO> userContextHolder = new NamedThreadLocal("Admin");

    public static AdminVO get() {
        return userContextHolder.get();
    }

    public static void set(AdminVO adminVo) {
        userContextHolder.set(adminVo);
    }

    public static void remove() {
        userContextHolder.remove();
    }

}

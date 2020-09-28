package com.tc.core.annotation;

import java.lang.annotation.*;

/**
 * @author master
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OpenMapping {

    boolean required() default true;
}

package me.zeyuan.adapter_nonrestful;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Make the request`s response is Non-RESTful.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface NonRESTful {
    Class value() default DefaultResponseWrapper.class;  //ResponseWrapper
}

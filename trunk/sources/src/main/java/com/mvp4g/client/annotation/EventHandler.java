package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {

    String name() default "";
    
    boolean multiple() default false;
}

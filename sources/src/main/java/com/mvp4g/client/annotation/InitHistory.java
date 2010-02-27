package com.mvp4g.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation indicates what event should be by Place Service in case the token is empty or
 * doesn't correspond to an event type.<br/>
 * <br/>
 * It must be used only on interface that has the annotation <code>Events</code>.<br/>
 * <br/>
 * You must not have more than one <code>InitHistory</code> annotation in a class.<br/>
 * <br/>
 * This annotation replaces the following line in the Mvp4g XML configuration file:<br/> {@code <history
 * ... initEvent="init">}
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface InitHistory {

}

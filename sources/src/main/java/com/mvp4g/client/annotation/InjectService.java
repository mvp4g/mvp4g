package com.mvp4g.client.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used on method to indicate that this method must be used to inject a service.<br/>
 * <br/>
 * This annotation must be used only in classes that have a <code>Presenter</code> or
 * <code>History</code> annotation.<br/>
 * <br/>
 * When a service needs to be injected, the framework will search an instance of a service with the
 * same class. If it doesn't found one, it will create one.<br/>
 * This means that only one instance of each service class will be created.<br/>
 * <br/>
 * You can also specify to inject a particular instance of a service thanks to its name with the attribute <i>serviceName</i>.<br/>
 * <br/>
 * This annotation replaces the following lines in the Mvp4g XML configuration file:<br/>
 * {@code <presenter ... services="userService" />}
 * {@code <converter ... services="userService" />} 
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface InjectService {

	String serviceName() default "";

}

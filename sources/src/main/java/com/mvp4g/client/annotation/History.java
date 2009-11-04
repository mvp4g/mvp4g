package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows to define an history converter for the framework.<br/>
 * <br/> 
 * You can define the name of the history converter thanks to the optional attribute <i>name</i>. If you don't give a name, the framework will generate one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * This annotation replaces the following line in the Mvp4g XML configuration file:<br/>
 * {@code <converter name="dealConverter" class="DealHistoryConverter" />} 
 * 
 * @author plcoirier
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface History {
	
	String name() default "";	

}

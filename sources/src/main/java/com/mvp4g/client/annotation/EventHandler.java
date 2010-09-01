package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows to define an event handler for the framework.<br/>
 * <br/>
 * You can define the name of the event handler thanks to the optional attribute <i>name</i>. If you
 * don't give a name, the framework will generate one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * You can also activate the multiple feature. This feature allows you to create several instance of
 * the same handler.
 * 
 * 
 * @since 27.04.2010
 * @author Dan Persa
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface EventHandler {

	String name() default "";

	boolean multiple() default false;
}

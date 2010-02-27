package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows to define a specific URL for the Service Entry Point thanks to the
 * attribute <i>path</i>.<br/>
 * <br/>
 * There is no need to use this annotation to indicate to the framework that this class is a
 * service.<br/>
 * <br/>
 * You can also define a name for the service thanks to the optional attribute <i>name</i>. If you
 * don't give a name, the framework will generate one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * <br/>
 * This annotation replaces the following line in the Mvp4g XML configuration file:<br/> {@code <service
 * name="userService" class="UserService" path="/rpc/service" />}<br/>
 * 
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Service {

	String name() default "";

	String path() default "";

	Class<?> generatedClass() default Void.class;

}

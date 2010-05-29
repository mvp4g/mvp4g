package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows to define a presenter for the framework.<br/>
 * <br/>
 * To define the presenter, you need to indicate the class of the view to inject in the presenter
 * thanks to the attribute <i>view</i>. The framework will automatically create one instance of the
 * view for each presenter.<br/>
 * <br/>
 * You can define the names of the presenter and/or the presenter view thanks to the optional
 * attribute <i>name</i> and <i>viewName</i>. If you don't give a name, the framework will generate
 * one.<br/>
 * It is recommended to affect a name only if needed.<br/>
 * 
 * @author plcoirier
 * 
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface Presenter {

	String name() default "";

	Class<?> view();

	String viewName() default "";
	
	boolean multiple() default false;

}

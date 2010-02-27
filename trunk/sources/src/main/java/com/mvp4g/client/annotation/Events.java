package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import com.mvp4g.client.Mvp4gModule;

/**
 * This annotation indicates that the interface should be used to define events of the module.<br/>
 * This annotation must be used on interface that extends <code>EventBus</code>. <br/>
 * When using this annotation, the attribute <i>startView</i> must be defined. This attribute
 * represents the class of the view to load at start. The framework will automatically find the
 * instance of this class. However an instance of this class must have been defined previously.<br/>
 * <br/>
 * You can also precise the name of this instance directly thanks to the optional attribute
 * <i>startViewName</i>.<br/>
 * <br/>
 * You can also indicate if history should be fired at start thanks to the optional attribute
 * <i>historyOnStart</i>. By Default its value is false.<br/>
 * <br/>
 * This annotation replace the following line in Mvp4g XML configuration file:<br/>
 * <br/> {@code <start view="rootTemplateView" history="false" />}
 * 
 * 
 * @author plcoirier
 * 
 */

@Retention( RetentionPolicy.RUNTIME )
public @interface Events {

	String startViewName() default "";

	Class<?> startView();

	Class<? extends Mvp4gModule> module() default Mvp4gModule.class;

	boolean historyOnStart() default false;

	boolean debug() default false;

}

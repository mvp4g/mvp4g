package com.mvp4g.processor;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

public class ProcessorUtil {

	final public static String CLEAR_HISTORY = "com.mvp4g.client.history.ClearHistory";
	final public static String STRING = String.class.getCanonicalName();
	final public static String EVENT = "com.mvp4g.client.annotation.Event";
	final public static String EVENTS = "com.mvp4g.client.annotation.Events";
	final public static String PRESENTER = "com.mvp4g.client.annotation.Presenter";
	final public static String EVENT_HANDLER = "com.mvp4g.client.annotation.EventHandler";
	final public static String HISTORY = "com.mvp4g.client.annotation.History";
	final public static String CHILD_MODULES = "com.mvp4g.client.annotation.module.ChildModules";

	final public static String HISTORY_CONVERTER_TYPE_NONE = "NONE";
	final public static String HISTORY_CONVERTER_TYPE_SIMPLE = "SIMPLE";
	final public static String HISTORY_CONVERTER_TYPE_DEFAULT = "DEFAULT";

	final public static String ATTRIBUTE_MODULE = "module";
	final public static String ATTRIBUTE_MODULE_CLASS = "moduleClass";
	final public static String ATTRIBUTE_HANDLERS = "handlers";
	final public static String ATTRIBUTE_CALLED_METHOD = "calledMethod";
	final public static String ATTRIBUTE_HISTORY_CONVERTER = "historyConverter";
	final public static String ATTRIBUTE_MODULES_TO_LOAD = "modulesToLoad";
	final public static String ATTRIBUTE_FORWARD_TO_PARENT = "forwardToParent";
	final public static String ATTRIBUTE_VALUE = "value";

	public static boolean sameParameters( List<? extends Element> expected, List<? extends Element> given, Element e ) {
		boolean same = ( expected.size() == given.size() );
		if ( same ) {
			for ( int i = 0; ( i < expected.size() ) && same; i++ ) {
				same = expected.get( i ).asType().toString().equals( given.get( i ).asType().toString() );
			}
		}
		return same;
	}

	public static String getMethodName( String methodName, List<? extends Element> parameters ) {
		int parameterSize = parameters.size();
		StringBuilder builder = new StringBuilder( parameterSize * 20 + 50 );
		builder.append( methodName );
		builder.append( "(" );
		if ( parameterSize > 0 ) {
			builder.append( parameters.get( 0 ).asType().toString() );
			for ( int i = 1; i < parameterSize; i++ ) {
				builder.append( "," );
				builder.append( parameters.get( i ).asType().toString() );
			}
		}
		builder.append( ")" );
		return builder.toString();
	}

	public static AnnotationMirror getAnnotationMirror( String annotationName, TypeElement element ) {
		if ( null == element || null == annotationName || annotationName.length() == 0 ) {
			return null;
		}

		for ( AnnotationMirror a : element.getAnnotationMirrors() ) {
			if ( annotationName.equals( a.getAnnotationType().toString() ) ) {
				return a;
			}
		}
		return null;
	}

	public static AnnotationValue getAnnotationValue( String elementName, AnnotationMirror annotation ) {
		if ( null == elementName || elementName.length() == 0 || null == annotation ) {
			return null;
		}
		String keyName;
		for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotation.getElementValues().entrySet() ) {
			keyName = entry.getKey().getSimpleName().toString();
			if ( elementName.equals( keyName ) ) {
				return entry.getValue();
			}
		}
		return null;
	}

}

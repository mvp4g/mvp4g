package com.mvp4g.processor;

import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.history.ClearHistory;

public class ProcessorUtil {

	public static String CLEAR_HISTORY = ClearHistory.class.getCanonicalName();
	public static String STRING = String.class.getCanonicalName();
	public static String EVENT = Event.class.getName();
	public static String PRESENTER = Presenter.class.getName();

	public static boolean sameParameters( List<? extends VariableElement> expected, List<? extends VariableElement> given, Element e ) {
		boolean same = ( expected.size() == given.size() );
		if ( same ) {
			for ( int i = 0; ( i < expected.size() ) && same; i++ ) {
				same = expected.get( i ).asType().toString().equals( given.get( i ).asType().toString() );
			}
		}
		return same;
	}

	public static String getMethodName( String methodName, List<? extends VariableElement> parameters ) {
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

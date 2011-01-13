package com.mvp4g.processor.controls;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.processor.Messages;
import com.mvp4g.processor.ProcessorUtil;

public class HandlerControl {

	public void control( ProcessingEnvironment processingEnv, ExecutableElement e, String methodName, List<? extends AnnotationValue> handlers ) {

		if ( handlers != null ) {

			if ( methodName == null ) {
				methodName = "on" + e.getSimpleName().toString().substring( 0, 1 ).toUpperCase() + e.getSimpleName().toString().substring( 1 );
			}

			TypeElement eventBusElement = (TypeElement)e.getEnclosingElement();
			TypeMirror eventBus = e.getEnclosingElement().asType();
			boolean withEvents = ( ProcessorUtil.getAnnotationMirror( ProcessorUtil.EVENTS, eventBusElement ) != null );
			TypeMirror handlerEventBus;

			String mName;
			Types types = processingEnv.getTypeUtils();
			for ( AnnotationValue value : handlers ) {
				TypeElement type = (TypeElement)( (DeclaredType)value.getValue() ).asElement();

				boolean found = false;
				for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( type ) ) ) {
					mName = method.getSimpleName().toString();
					if ( !found && mName.toString().equals( methodName ) ) {
						if ( method.getModifiers().contains( Modifier.PUBLIC ) ) {
							found = ProcessorUtil.sameParameters( e.getParameters(), method.getParameters(), e );
						}
					} else if ( "getEventBus".equals( mName ) ) {
						handlerEventBus = method.getReturnType();
						if ( !types.isSubtype( eventBus, handlerEventBus ) ) {
							//in this event part of the parent event bus
							if ( withEvents || !types.isSubtype( handlerEventBus, eventBus ) ) {
								processingEnv.getMessager().printMessage( Kind.ERROR,
										String.format( Messages.INVALID_EVENT_BUS, eventBus, type.getSimpleName(), eventBus, handlerEventBus ), e );
							}
						}
					}
				}
				if ( ProcessorUtil.getAnnotationMirror( ProcessorUtil.PRESENTER, type ) == null ) {
					if ( ProcessorUtil.getAnnotationMirror( ProcessorUtil.EVENT_HANDLER, type ) == null ) {
						processingEnv.getMessager().printMessage(
								Kind.ERROR,
								String.format( Messages.MISSING_ANNOTATION_OR, type.getSimpleName(), ProcessorUtil.PRESENTER,
										ProcessorUtil.EVENT_HANDLER ), e );
					}
				}
				if ( !found ) {
					processingEnv.getMessager().printMessage(
							Kind.ERROR,
							String.format( Messages.MISSING_METHOD, type.getSimpleName(),
									ProcessorUtil.getMethodName( methodName, e.getParameters() ), "void" ), e );
				}
			}
		}
	}

}

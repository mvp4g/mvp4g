package com.mvp4g.processor.controls;

import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.processor.Messages;
import com.mvp4g.processor.ProcessorUtil;

public class HandlerControl {

	public void control( ProcessingEnvironment processingEnv, ExecutableElement e, String methodName, List<? extends AnnotationValue> handlers ) {

		if ( handlers != null ) {

			if ( methodName == null ) {
				methodName = "on" + e.getSimpleName().toString().substring( 0, 1 ).toUpperCase() + e.getSimpleName().toString().substring( 1 );
			}

			TypeMirror eventBus = e.getEnclosingElement().asType();
			TypeMirror handlerEventBus;
			TypeMirror view;
			String mName;
			for ( AnnotationValue value : handlers ) {
				TypeElement type = (TypeElement)( (DeclaredType)value.getValue() ).asElement();
				view = null;
				boolean found = false;
				for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( type ) ) ) {
					mName = method.getSimpleName().toString();
					if ( !found && mName.toString().equals( methodName ) ) {
						if ( method.getModifiers().contains( Modifier.PUBLIC ) ) {
							found = ProcessorUtil.sameParameters( e.getParameters(), method.getParameters(), e );
						}
					} else if ( "getEventBus".equals( mName ) ) {
						handlerEventBus = method.getReturnType();
						if ( !processingEnv.getTypeUtils().isSubtype( eventBus, handlerEventBus ) ) {
							processingEnv.getMessager().printMessage( Kind.ERROR,
									String.format( Messages.INVALID_EVENT_BUS, eventBus, type.getSimpleName(), eventBus, handlerEventBus ), e );
						}
					} else if ( "getView".equals( mName ) ) {
						view = method.getReturnType();
					}
				}
				if ( type.getAnnotation( Presenter.class ) == null ) {
					if ( type.getAnnotation( EventHandler.class ) == null ) {
						processingEnv.getMessager().printMessage(
								Kind.ERROR,
								String.format( Messages.MISSING_ANNOTATION_OR, type.getSimpleName(), Presenter.class.getSimpleName(),
										EventHandler.class.getSimpleName() ), e );
					}
				} else {
					checkView( processingEnv, type, view );
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

	private void checkView( ProcessingEnvironment processingEnv, TypeElement presenter, TypeMirror view ) {
		AnnotationMirror a = ProcessorUtil.getAnnotationMirror( ProcessorUtil.PRESENTER, presenter );
		if ( a != null ) {
			AnnotationValue v = ProcessorUtil.getAnnotationValue( "view", a );
			if ( v != null ) {
				DeclaredType injectedView = (DeclaredType)v.getValue();
				if ( !processingEnv.getTypeUtils().isSubtype( injectedView, view ) ) {
					processingEnv.getMessager().printMessage( Kind.ERROR,
							String.format( Messages.INVALID_VIEW, injectedView, presenter.getSimpleName(), injectedView, view ), presenter );
				}
			}
		}
	}
}

package com.mvp4g.processor.controls;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.processor.Messages;
import com.mvp4g.processor.ProcessorUtil;

public class PresenterControl {

	public void control( ProcessingEnvironment processingEnv, TypeElement handler ) {
		if ( handler != null ) {
			TypeMirror view = null;
			for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( handler ) ) ) {
				if ( "getView".equals( method.getSimpleName().toString() ) ) {
					view = method.getReturnType();
				}
			}
			checkView( processingEnv, handler, view );
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

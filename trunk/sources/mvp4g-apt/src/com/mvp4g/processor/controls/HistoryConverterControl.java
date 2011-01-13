package com.mvp4g.processor.controls;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
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

public class HistoryConverterControl {

	public void control( ProcessingEnvironment processingEnv, ExecutableElement e, String methodName, AnnotationValue historyConverter ) {

		if ( historyConverter != null ) {
			DeclaredType type = (DeclaredType)historyConverter.getValue();
			if ( !ProcessorUtil.CLEAR_HISTORY.equals( type.toString() ) ) {
				if ( methodName == null ) {
					methodName = "on" + e.getSimpleName().toString().substring( 0, 1 ).toUpperCase() + e.getSimpleName().toString().substring( 1 );
				}
				String mName;
				TypeMirror eventBus = e.getEnclosingElement().asType();
				TypeMirror handlerEventBus;

				TypeElement element = (TypeElement)( (DeclaredType)historyConverter.getValue() ).asElement();
				boolean found = false;
				Types types = processingEnv.getTypeUtils();
				for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( element ) ) ) {
					mName = method.getSimpleName().toString();
					if ( mName.toString().equals( methodName ) ) {
						if ( method.getModifiers().contains( Modifier.PUBLIC ) && ProcessorUtil.STRING.equals( method.getReturnType().toString() ) ) {
							found = ProcessorUtil.sameParameters( e.getParameters(), method.getParameters(), e );
							break;
						}
					} else if ( "convertFromToken".equals( mName ) ) {
						handlerEventBus = method.getParameters().get( 2 ).asType();
						if ( !types.isSubtype( eventBus, handlerEventBus ) ) {
							processingEnv.getMessager().printMessage( Kind.ERROR,
									String.format( Messages.INVALID_EVENT_BUS, eventBus, type, eventBus, handlerEventBus ), e );
						}
					}
				}
				AnnotationMirror history = ProcessorUtil.getAnnotationMirror( ProcessorUtil.HISTORY, element );
				if ( history == null ) {
					processingEnv.getMessager().printMessage( Kind.ERROR,
							String.format( Messages.MISSING_ANNOTATION, element.getSimpleName(), ProcessorUtil.HISTORY ), e );
				} else  {
					boolean withParams = true;
					AnnotationValue value = ProcessorUtil.getAnnotationValue( "convertParams", history );
					if(value != null){
						withParams = ((Boolean) value.getValue()).booleanValue();
					}
					if ( withParams && !found ) {
						processingEnv.getMessager().printMessage(
								Kind.ERROR,
								String.format( Messages.MISSING_METHOD, element.getSimpleName(), ProcessorUtil.getMethodName( methodName, e
										.getParameters() ), "String" ), e );
					}
				}
			}

		}

	}

}

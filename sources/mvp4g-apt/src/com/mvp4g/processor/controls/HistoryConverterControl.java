package com.mvp4g.processor.controls;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
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

				TypeElement element = (TypeElement)( (DeclaredType)historyConverter.getValue() ).asElement();
				AnnotationMirror history = ProcessorUtil.getAnnotationMirror( ProcessorUtil.HISTORY, element );
				if ( history == null ) {
					processingEnv.getMessager().printMessage( Kind.ERROR,
							String.format( Messages.MISSING_ANNOTATION, element.getSimpleName(), ProcessorUtil.HISTORY ), e );
				} else {
					AnnotationValue value = ProcessorUtil.getAnnotationValue( "type", history );

					boolean found = false;
					String convertMetodName = null;
					String valueStr = ( ( value == null ) || ( value.getValue() == null ) ) ? ProcessorUtil.HISTORY_CONVERTER_TYPE_DEFAULT : value
							.getValue().toString();

					List<Element> parameters = new ArrayList<Element>();
					if ( ProcessorUtil.HISTORY_CONVERTER_TYPE_NONE.equals( valueStr ) ) {
						found = true;
					} else if ( ProcessorUtil.HISTORY_CONVERTER_TYPE_SIMPLE.equals( valueStr ) ) {
						convertMetodName = "convertToToken";
						Element v = processingEnv.getElementUtils().getTypeElement( ProcessorUtil.STRING );
						parameters.add( v );
						parameters.addAll( e.getParameters() );
					} else {
						convertMetodName = methodName;
						if ( convertMetodName == null ) {
							convertMetodName = "on" + e.getSimpleName().toString().substring( 0, 1 ).toUpperCase()
									+ e.getSimpleName().toString().substring( 1 );
						}
						parameters.addAll( e.getParameters() );
					}

					Types types = processingEnv.getTypeUtils();
					String mName;
					TypeMirror eventBus = e.getEnclosingElement().asType();
					TypeMirror handlerEventBus;
					
					for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( element ) ) ) {
						mName = method.getSimpleName().toString();
						if ( !found && mName.toString().equals( convertMetodName ) ) {
							if ( method.getModifiers().contains( Modifier.PUBLIC ) && ProcessorUtil.STRING.equals( method.getReturnType().toString() ) ) {
								found = ProcessorUtil.sameParameters( parameters, method.getParameters(), e );
							}
						} else if ( "convertFromToken".equals( mName ) ) {
							handlerEventBus = method.getParameters().get( 2 ).asType();
							if ( !types.isSubtype( eventBus, handlerEventBus ) ) {
								processingEnv.getMessager().printMessage( Kind.ERROR,
										String.format( Messages.INVALID_EVENT_BUS, eventBus, type, eventBus, handlerEventBus ), e );
							}
						}
					}

					if ( !found ) {
						processingEnv.getMessager().printMessage(
								Kind.ERROR,
								String.format( Messages.MISSING_METHOD, element.getSimpleName(), ProcessorUtil.getMethodName( convertMetodName, parameters ), "String" ), e );
					}

				}

			}

		}

	}
}

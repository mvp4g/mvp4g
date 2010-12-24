package com.mvp4g.processor;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.mvp4g.processor.controls.HandlerControl;
import com.mvp4g.processor.controls.HistoryConverterControl;

@SupportedAnnotationTypes( "com.mvp4g.client.annotation.Event" )
@SupportedSourceVersion( SourceVersion.RELEASE_6 )
public class EventAnnotationProcessor extends AbstractProcessor {

	@SuppressWarnings( "unchecked" )
	@Override
	public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {

		

		String keyName;
		
		HandlerControl handlerControl = new HandlerControl();
		HistoryConverterControl historyConverterControl = new HistoryConverterControl();

		List<? extends AnnotationValue> handlers;
		String calledMethod;
		AnnotationValue historyConverter;
		for ( TypeElement element : annotations ) {

			for ( ExecutableElement e : ElementFilter.methodsIn( roundEnv.getElementsAnnotatedWith( element ) ) ) {
				for ( AnnotationMirror m : e.getAnnotationMirrors() ) {
					if ( ProcessorUtil.EVENT.equals( m.getAnnotationType().toString() ) ) {
						handlers = null;
						calledMethod = null;
						historyConverter = null;
						for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : m.getElementValues().entrySet() ) {
							keyName = entry.getKey().getSimpleName().toString();
							if ( "handlers".equals( keyName ) ) {
								handlers = (List<? extends AnnotationValue>)entry.getValue().getValue();
							} else if ( "calledMethod".equals( keyName ) ) {
								calledMethod = (String)entry.getValue().getValue();
							} else if ( "historyConverter".equals( keyName ) ) {
								historyConverter = entry.getValue();
							}
						}
						handlerControl.control( processingEnv, e, calledMethod, handlers );
						historyConverterControl.control( processingEnv, e, calledMethod, historyConverter );
					}
				}
			}
		}
		return true;
	}

}

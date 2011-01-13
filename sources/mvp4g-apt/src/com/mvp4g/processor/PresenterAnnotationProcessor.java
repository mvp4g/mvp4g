package com.mvp4g.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import com.mvp4g.processor.controls.PresenterControl;

@SupportedAnnotationTypes( "com.mvp4g.client.annotation.Presenter" )
@SupportedSourceVersion( SourceVersion.RELEASE_6 )
public class PresenterAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {
		PresenterControl presenterControl = new PresenterControl();
		for ( TypeElement element : annotations ) {
			for ( TypeElement e : ElementFilter.typesIn( roundEnv.getElementsAnnotatedWith( element ) ) ) {
				presenterControl.control( processingEnv, e );
			}
		}
		return true;
	}

}

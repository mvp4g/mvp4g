package com.mvp4g.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.processor.bean.ApplicationInfo;
import com.mvp4g.processor.bean.ModuleInfo;
import com.mvp4g.processor.controls.ChildEventControl;
import com.mvp4g.processor.controls.HandlerControl;
import com.mvp4g.processor.controls.HistoryConverterControl;
import com.mvp4g.processor.controls.ParentEventControl;

@SupportedAnnotationTypes( { ProcessorUtil.EVENTS } )
@SupportedSourceVersion( SourceVersion.RELEASE_6 )
public class EventAnnotationProcessor extends AbstractProcessor {

	@Override
	public boolean process( Set<? extends TypeElement> annotations, RoundEnvironment roundEnv ) {

		for ( TypeElement element : annotations ) {
			if ( ProcessorUtil.EVENTS.equals( element.asType().toString() ) ) {
				List<TypeElement> eventBus = getAllEventBus( element, roundEnv );
				ApplicationInfo applicationInfo = loadChildModules( eventBus );
				for ( TypeElement e : eventBus ) {
					processEvent( e, applicationInfo );
				}
			}
		}

		//getAllEventBus( events, roundEnv );
		//processEvent( event, roundEnv, loadChildModules( events, roundEnv ), events );

		return true;
	}

	@SuppressWarnings( "unchecked" )
	private void processEvent( TypeElement element, ApplicationInfo applicationInfo ) {
		String keyName;

		HandlerControl handlerControl = new HandlerControl();
		HistoryConverterControl historyConverterControl = new HistoryConverterControl();
		ChildEventControl childEventControl = new ChildEventControl();
		ParentEventControl parentEventControl = new ParentEventControl();

		List<? extends AnnotationValue> handlers, modulesToLoad;
		String calledMethod;
		AnnotationValue historyConverter;
		boolean forwardToParent;
		for ( ExecutableElement e : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( element ) ) ) {
			for ( AnnotationMirror m : e.getAnnotationMirrors() ) {
				if ( ProcessorUtil.EVENT.equals( m.getAnnotationType().toString() ) ) {
					handlers = null;
					calledMethod = null;
					historyConverter = null;
					modulesToLoad = null;
					forwardToParent = false;
					for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : m.getElementValues().entrySet() ) {
						keyName = entry.getKey().getSimpleName().toString();
						if ( ProcessorUtil.ATTRIBUTE_HANDLERS.equals( keyName ) ) {
							handlers = (List<? extends AnnotationValue>)entry.getValue().getValue();
						} else if ( ProcessorUtil.ATTRIBUTE_CALLED_METHOD.equals( keyName ) ) {
							calledMethod = (String)entry.getValue().getValue();
						} else if ( ProcessorUtil.ATTRIBUTE_HISTORY_CONVERTER.equals( keyName ) ) {
							historyConverter = entry.getValue();
						} else if ( ProcessorUtil.ATTRIBUTE_MODULES_TO_LOAD.equals( keyName ) ) {
							modulesToLoad = (List<? extends AnnotationValue>)entry.getValue().getValue();
						} else if ( ProcessorUtil.ATTRIBUTE_FORWARD_TO_PARENT.equals( keyName ) ) {
							forwardToParent = (Boolean)entry.getValue().getValue();
						}
					}
					handlerControl.control( processingEnv, e, calledMethod, handlers );
					historyConverterControl.control( processingEnv, e, calledMethod, historyConverter );
					childEventControl.control( processingEnv, e, element, modulesToLoad, applicationInfo );
					parentEventControl.control( processingEnv, e, element, forwardToParent, applicationInfo );
				}
			}
		}
	}

	private List<TypeElement> getAllEventBus( TypeElement element, RoundEnvironment roundEnv ) {
		List<TypeElement> eventBus = new ArrayList<TypeElement>();

		try {
			//create this object to get the file path
			FileObject temp = processingEnv.getFiler().createResource( StandardLocation.SOURCE_OUTPUT, "", "evenbus.properties" );

			File file = new File( temp.toUri() );
			if ( !file.exists() ) {
				file.createNewFile();
			} else {
				//get previous known event bus
				BufferedReader reader = new BufferedReader( new FileReader( file ) );
				String line = reader.readLine();
				if ( line != null ) {
					String[] tab = line.split( "," );

					TypeElement type;
					Elements elements = processingEnv.getElementUtils();
					for ( int i = 0; i < tab.length; i++ ) {
						type = elements.getTypeElement( tab[i] );
						if ( ( type != null ) && ( !eventBus.contains( type ) ) ) {
							eventBus.add( type );
						}
					}
				}
				reader.close();
			}

			for ( TypeElement type : ElementFilter.typesIn( roundEnv.getElementsAnnotatedWith( element ) ) ) {
				if ( !eventBus.contains( type ) ) {
					eventBus.add( type );
				}
			}

			//save event bus type name in a file for next iteration
			FileWriter writer = new FileWriter( file );
			for ( TypeElement type : eventBus ) {
				writer.append( type.getQualifiedName() );
				writer.append( "," );
			}
			writer.close();
		} catch ( IOException e ) {
			throw new RuntimeException( e.getMessage() );
		}

		return eventBus;
	}

	@SuppressWarnings( "unchecked" )
	private ApplicationInfo loadChildModules( List<TypeElement> eventBus ) {
		Map<String, ModuleInfo> childModules = new HashMap<String, ModuleInfo>();
		Map<String, String> eventBusMap = new HashMap<String, String>();
		AnnotationValue value;
		String module;
		ModuleInfo info;
		List<AnnotationValue> children;
		AnnotationMirror child;
		for ( TypeElement e : eventBus ) {
			for ( AnnotationMirror annotation : e.getAnnotationMirrors() ) {
				if ( ProcessorUtil.EVENTS.equals( annotation.getAnnotationType().toString() ) ) {
					annotation = ProcessorUtil.getAnnotationMirror( ProcessorUtil.EVENTS, e );
					value = ProcessorUtil.getAnnotationValue( ProcessorUtil.ATTRIBUTE_MODULE, annotation );
					if ( value != null ) {
						module = ( (DeclaredType)value.getValue() ).toString();
						info = childModules.get( module );
						if ( info == null ) {
							info = new ModuleInfo();
							childModules.put( module, info );
						}
						if ( info.getCurrentEventBus() != null ) {
							processingEnv.getMessager().printMessage( Kind.ERROR,
									String.format( Messages.MODULE_ASSOCIATED_TWICE, module, info.getCurrentEventBus().asType(), e.asType() ), e );
						} else {
							info.setCurrentEventBus( e );
							eventBusMap.put( e.getQualifiedName().toString(), module );
						}
					}
				} else if ( ProcessorUtil.CHILD_MODULES.equals( annotation.getAnnotationType().toString() ) ) {
					children = (List<AnnotationValue>)ProcessorUtil.getAnnotationValue( ProcessorUtil.ATTRIBUTE_VALUE, annotation ).getValue();
					for ( AnnotationValue childValue : children ) {
						child = (AnnotationMirror)childValue.getValue();
						module = ( (DeclaredType)ProcessorUtil.getAnnotationValue( ProcessorUtil.ATTRIBUTE_MODULE_CLASS, child ).getValue() )
								.toString();
						info = childModules.get( module );
						if ( info == null ) {
							info = new ModuleInfo();
							childModules.put( module, info );
						}
						if ( info.getParentEventBus() != null ) {
							processingEnv.getMessager().printMessage( Kind.ERROR,
									String.format( Messages.MODULE_TWO_PARENT_EVENT_BUS, module, info.getParentEventBus().asType(), e.asType() ), e );
						} else {
							info.setParentEventBus( e );
						}
					}
				}
			}
		}

		return new ApplicationInfo( childModules, eventBusMap );
	}
}

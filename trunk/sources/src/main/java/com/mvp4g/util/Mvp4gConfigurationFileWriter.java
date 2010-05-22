/*
 * Copyright 2010 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.SourceWriter;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

/**
 * This class reads the mvp4g-conf.xml
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gConfigurationFileWriter {

	private SourceWriter sourceWriter = null;

	private Mvp4gConfiguration configuration = null;

	public Mvp4gConfigurationFileWriter( SourceWriter sourceWriter, Mvp4gConfiguration configuration ) {
		this.sourceWriter = sourceWriter;
		this.configuration = configuration;
	}

	/**
	 * Parses the mvp4g-conf.xml and write the information in the source writer
	 * 
	 * @throws UnableToCompleteException
	 *             exception thrown if the configuratin file is not correct
	 */
	public void writeConf() {

		sourceWriter.indent();

		sourceWriter.println();

		writeEventBusClass();

		writeGinInjector();

		sourceWriter.println();

		sourceWriter.println( "private Object startView = null;" );
		sourceWriter.println( "protected AbstractEventBus eventBus = null;" );
		sourceWriter.print( "protected " );
		sourceWriter.print( configuration.getModule().getQualifiedSourceName() );
		sourceWriter.println( " itself = this;" );

		writeParentEventBus();

		if ( configuration.getChildModules().size() > 0 ) {
			writeChildModules();
		}

		writeHistoryConnection();

		sourceWriter.println();

		sourceWriter.println( "public void createAndStartModule(){" );
		sourceWriter.indent();

		sourceWriter.println( "Mvp4gGinjector injector = GWT.create( Mvp4gGinjector.class );" );

		writeViews();

		sourceWriter.println();

		writeServices();

		sourceWriter.println();

		writeHistory();

		sourceWriter.println();

		writePresenters();

		sourceWriter.println();

		writeEventHandlers();

		sourceWriter.println();

		writeEventBus();

		sourceWriter.println();

		injectEventBus();

		sourceWriter.println();

		writeStartEvent();
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		writeGetters();

	}

	private void writeGetters() {
		sourceWriter.println( "public Object getStartView(){" );
		sourceWriter.indent();
		sourceWriter.println( "return startView;" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );
		sourceWriter.println();
		sourceWriter.println( "public EventBus getEventBus(){" );
		sourceWriter.indent();
		sourceWriter.println( "return eventBus;" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );
	}

	private void writeParentEventBus() {
		JClassType parentModule = configuration.getParentModule();
		if ( parentModule != null ) {
			String parentModuleClass = parentModule.getQualifiedSourceName();
			String parentEventBusClass = configuration.getParentEventBus().getQualifiedSourceName();
			sourceWriter.print( "private " );
			sourceWriter.print( parentModuleClass );
			sourceWriter.println( " parentModule = null;" );
			sourceWriter.print( "private " );
			sourceWriter.print( parentEventBusClass );
			sourceWriter.println( " parentEventBus = null;" );
			sourceWriter.print( "public void setParentModule(" );
			sourceWriter.print( parentModuleClass );
			sourceWriter.println( " module){" );
			sourceWriter.indent();
			sourceWriter.println( "parentModule = module;" );
			sourceWriter.print( "parentEventBus = (" );
			sourceWriter.print( parentEventBusClass );
			sourceWriter.println( ") module.getEventBus();" );
			sourceWriter.outdent();
			sourceWriter.println( "}" );
		} else {
			// only root module can have a placeService instance
			sourceWriter.println( "private PlaceService placeService = null;" );
		}
	}

	private void writeChildModules() {

		sourceWriter
				.println( "public java.util.Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new java.util.HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();" );
		sourceWriter.println();

		String moduleClassName = null;
		EventElement event = null;
		Set<EventElement> events = configuration.getEvents();
		boolean isXml = configuration.getEventBus().isXml();

		ChildModulesElement loadConfig = configuration.getLoadChildConfig();
		String errorEvent, beforeEvent, afterEvent;
		boolean isError, isBefore, isAfter;

		if ( loadConfig == null ) {
			errorEvent = null;
			beforeEvent = null;
			afterEvent = null;
			isError = false;
			isBefore = false;
			isAfter = false;
		} else {
			errorEvent = loadConfig.getErrorEvent();
			beforeEvent = loadConfig.getBeforeEvent();
			afterEvent = loadConfig.getAfterEvent();
			isError = ( errorEvent != null ) && ( errorEvent.length() > 0 );
			isBefore = ( beforeEvent != null ) && ( beforeEvent.length() > 0 );
			isAfter = ( afterEvent != null ) && ( afterEvent.length() > 0 );
		}

		String formError = null;
		if ( isError ) {
			String[] params = getElement( errorEvent, configuration.getEvents() ).getEventObjectClasses();
			if ( ( params != null ) && ( params.length > 0 ) ) {
				formError = "reason";
			}
		}
		boolean isAsync = true;
		boolean isAsyncEnabled = configuration.isAsyncEnabled();
		for ( ChildModuleElement module : configuration.getChildModules() ) {
			moduleClassName = module.getClassName();
			isAsync = module.isAsync() && isAsyncEnabled;
			sourceWriter.print( "private void load" );
			sourceWriter.print( module.getName() );
			sourceWriter.println( "(final Mvp4gEventPasser passer){" );
			sourceWriter.indent();
			if ( isAsync ) {
				if ( isBefore ) {
					writeDispatchEvent( beforeEvent, null, isXml );
				}
				sourceWriter.println( "GWT.runAsync(new com.google.gwt.core.client.RunAsyncCallback() {" );
				sourceWriter.indent();
				sourceWriter.println( "public void onSuccess() {" );
				sourceWriter.indent();
				if ( isAfter ) {
					writeDispatchEvent( afterEvent, null, isXml );
				}
			}
			sourceWriter.print( moduleClassName );
			sourceWriter.print( " newModule = (" );
			sourceWriter.print( moduleClassName );
			sourceWriter.print( ") modules.get(" );
			sourceWriter.print( moduleClassName );
			sourceWriter.println( ".class);" );
			sourceWriter.println( "if(newModule == null){" );
			sourceWriter.indent();
			sourceWriter.print( "newModule = GWT.create(" );
			sourceWriter.print( moduleClassName );
			sourceWriter.println( ".class);" );
			sourceWriter.print( "modules.put(" );
			sourceWriter.print( moduleClassName );
			sourceWriter.println( ".class, newModule);" );
			if ( configuration.hasParentModule( moduleClassName ) ) {
				sourceWriter.println( "newModule.setParentModule(itself);" );
			}
			sourceWriter.println( "newModule.createAndStartModule();" );
			sourceWriter.outdent();
			sourceWriter.println( "}" );

			if ( module.isAutoDisplay() ) {
				event = getElement( module.getEventToDisplayView(), events );
				writeDispatchEvent( event.getType(), "(" + event.getEventObjectClasses()[0] + ") newModule.getStartView()", isXml );
			}

			sourceWriter.println( "if(passer != null) passer.pass(newModule);" );
			if ( isAsync ) {
				sourceWriter.outdent();
				sourceWriter.println( "}" );
				sourceWriter.println( "public void onFailure(Throwable reason) {" );
				if ( isAfter ) {
					writeDispatchEvent( afterEvent, null, isXml );
				}
				if ( isError ) {
					sourceWriter.indent();
					writeDispatchEvent( errorEvent, formError, isXml );
					sourceWriter.outdent();
				}
				sourceWriter.println( "}" );
				sourceWriter.outdent();
				sourceWriter.println( "});" );
			}
			sourceWriter.outdent();
			sourceWriter.println( "}" );
		}
	}

	private void writeEventBusClass() {

		EventBusElement eventBus = configuration.getEventBus();

		sourceWriter.print( "private abstract class AbstractEventBus extends " );
		sourceWriter.print( eventBus.getAbstractClassName() );
		sourceWriter.print( " implements " );
		sourceWriter.print( eventBus.getInterfaceClassName() );
		sourceWriter.println( "{}" );
	}

	private void writeGinInjector() {
		sourceWriter.print( "@GinModules( " );
		sourceWriter.print( configuration.getGinModule().getClassName() );
		sourceWriter.println( ".class)" );
		sourceWriter.println( "public interface Mvp4gGinjector extends Ginjector {" );
		sourceWriter.indent();
		for ( PresenterElement presenter : configuration.getPresenters() ) {
			sourceWriter.print( presenter.getClassName() );
			sourceWriter.print( " get" );
			sourceWriter.print( presenter.getName() );
			sourceWriter.println( "();" );
		}
		for ( EventHandlerElement eventHandler : configuration.getEventHandlers() ) {
			sourceWriter.print( eventHandler.getClassName() );
			sourceWriter.print( " get" );
			sourceWriter.print( eventHandler.getName() );
			sourceWriter.println( "();" );
		}
		for ( ViewElement view : configuration.getViews() ) {
			sourceWriter.print( view.getClassName() );
			sourceWriter.print( " get" );
			sourceWriter.print( view.getName() );
			sourceWriter.println( "();" );
		}
		for ( HistoryConverterElement history : configuration.getHistoryConverters() ) {
			sourceWriter.print( history.getClassName() );
			sourceWriter.print( " get" );
			sourceWriter.print( history.getName() );
			sourceWriter.println( "();" );
		}
		sourceWriter.outdent();
		sourceWriter.print( "}" );

	}

	/**
	 * Write the history converters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeHistory() {

		HistoryElement history = configuration.getHistory();

		if ( history != null ) {

			sourceWriter.println( "placeService = new PlaceService(){" );

			sourceWriter.indent();
			sourceWriter.println( "protected void sendInitEvent(){" );
			sourceWriter.indent();
			sourceWriter.print( "eventBus." );

			if ( configuration.getEventBus().isXml() ) {
				sourceWriter.print( "dispatch(\"" );
				sourceWriter.print( history.getInitEvent() );
				sourceWriter.println( "\");" );
			} else {
				sourceWriter.print( history.getInitEvent() );
				sourceWriter.println( "();" );
			}

			sourceWriter.outdent();
			sourceWriter.println( "}" );
			sourceWriter.outdent();
			sourceWriter.indent();
			sourceWriter.println( "protected void sendNotFoundEvent(){" );
			sourceWriter.indent();
			sourceWriter.print( "eventBus." );

			if ( configuration.getEventBus().isXml() ) {
				sourceWriter.print( "dispatch(\"" );
				sourceWriter.print( history.getNotFoundEvent() );
				sourceWriter.println( "\");" );
			} else {
				sourceWriter.print( history.getNotFoundEvent() );
				sourceWriter.println( "();" );
			}

			sourceWriter.outdent();
			sourceWriter.println( "}" );
			sourceWriter.outdent();
			sourceWriter.println( "};" );

		}

		String name = null;

		for ( HistoryConverterElement converter : configuration.getHistoryConverters() ) {
			name = converter.getName();
			createInstance( name, converter.getClassName() );
			injectServices( name, converter.getInjectedServices() );
		}

	}

	/**
	 * Write the views included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeViews() {

		for ( ViewElement view : configuration.getViews() ) {
			createInstance( view.getName(), view.getClassName() );
		}

	}

	/**
	 * Write the presenters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writePresenters() {

		String name = null;
		String className = null;

		for ( PresenterElement presenter : configuration.getPresenters() ) {
			name = presenter.getName();
			className = presenter.getClassName();

			createInstance( name, className );

			sourceWriter.print( name );
			sourceWriter.println( ".setView(" + presenter.getView() + ");" );

			injectServices( name, presenter.getInjectedServices() );

		}

	}
	
	/**
	 * Write the eventHandlers included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeEventHandlers() {

		String name = null;
		String className = null;

		for ( EventHandlerElement eventHandler : configuration.getEventHandlers() ) {
			name = eventHandler.getName();
			className = eventHandler.getClassName();

			createInstance( name, className );

			injectServices( name, eventHandler.getInjectedServices() );
		}
	}

	/**
	 * Write the presenters included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void injectEventBus() {

		for ( PresenterElement presenter : configuration.getPresenters() ) {
			sourceWriter.print( presenter.getName() );
			sourceWriter.println( ".setEventBus(eventBus);" );
		}
		
		for ( EventHandlerElement eventHandler : configuration.getEventHandlers() ) {
			sourceWriter.print( eventHandler.getName() );
			sourceWriter.println( ".setEventBus(eventBus);" );
		}

		if ( configuration.getHistory() != null ) {
			sourceWriter.print( "placeService.setModule(itself);" );
		}

	}

	/**
	 * Write the services included in the configuration file.
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 */
	private void writeServices() {

		String name = null;

		for ( ServiceElement service : configuration.getServices() ) {
			name = service.getName();

			sourceWriter.print( "final " );
			sourceWriter.print( service.getGeneratedClassName() );
			sourceWriter.print( " " );
			sourceWriter.print( name );
			sourceWriter.print( " = GWT.create(" );
			sourceWriter.print( service.getClassName() );
			sourceWriter.println( ".class);" );

			if ( service.hasPath() ) {
				sourceWriter.print( "((ServiceDefTarget) " );
				sourceWriter.print( name );
				sourceWriter.print( ").setServiceEntryPoint(\"" );
				sourceWriter.print( service.getPath() );
				sourceWriter.print( "\");" );
			}
		}
	}

	/**
	 * Write the events included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @param eventsConf
	 *            List of events tag included in the configuration file
	 * @throws UnableToCompleteException
	 *             thrown if the events tag aren't correct.
	 * @throws InvalidMvp4gConfigurationException
	 */
	private void writeEventBus() {

		EventBusElement eventBus = configuration.getEventBus();

		sourceWriter.println( "eventBus = new AbstractEventBus(){" );
		sourceWriter.indent();

		List<EventElement> eventsWithHistory = new ArrayList<EventElement>();

		String type = null;
		String calledMethod = null;
		String[] objectClasses = null;
		String param = null;
		String parentParam = null;
		String[] handlers = null;
		String history;
		List<String> activate;
		List<String> deactivate;

		for ( EventElement event : configuration.getEvents() ) {
			type = event.getType();
			calledMethod = event.getCalledMethod();
			objectClasses = event.getEventObjectClasses();

			handlers = event.getHandlers();
			history = event.getHistory();
			activate = event.getActivate();
			deactivate = event.getDeactivate();

			sourceWriter.print( "public void " );
			sourceWriter.print( type );
			sourceWriter.print( "(" );
			if ( ( objectClasses == null ) || ( objectClasses.length == 0 ) ) {
				param = "()";
				parentParam = null;
			} else {
				int nbParams = objectClasses.length;
				StringBuilder paramBuilder = new StringBuilder( 50 * nbParams );
				int i;
				for ( i = 0; i < ( nbParams - 1 ); i++ ) {
					sourceWriter.print( objectClasses[i] );
					sourceWriter.print( " attr" );
					sourceWriter.print( Integer.toString( i ) );
					sourceWriter.print( "," );

					paramBuilder.append( "attr" );
					paramBuilder.append( i );
					paramBuilder.append( "," );
				}

				sourceWriter.print( objectClasses[i] );
				sourceWriter.print( " attr" );
				sourceWriter.print( Integer.toString( i ) );

				paramBuilder.append( "attr" );
				paramBuilder.append( i );

				parentParam = paramBuilder.toString();
				param = "(" + paramBuilder.toString() + ")";

			}
			sourceWriter.println( "){" );

			sourceWriter.indent();

			writeLog( type, objectClasses );

			if ( activate != null ) {
				for ( String presenter : activate ) {
					sourceWriter.print( presenter );
					sourceWriter.println( ".setActivated(true);" );
				}
			}
			if ( deactivate != null ) {
				for ( String presenter : deactivate ) {
					sourceWriter.print( presenter );
					sourceWriter.println( ".setActivated(false);" );
				}
			}

			writeLoadChildModule( event, param );
			writeParentEvent( event, parentParam );

			if ( history != null ) {

				HistoryConverterElement historyConverterElement = getElement( history, configuration.getHistoryConverters() );
				if ( ClearHistory.class.getCanonicalName().equals( historyConverterElement.getClassName() ) ) {
					sourceWriter.println( "clearHistory(itself);" );
				} else {
					sourceWriter.print( "place( itself, \"" );
					sourceWriter.print( type );
					sourceWriter.print( "\"," );
					if ( historyConverterElement.isConvertParams() ) {
						sourceWriter.print( history );
						sourceWriter.print( "." );
						sourceWriter.print( calledMethod );
						sourceWriter.print( param );
					} else {
						sourceWriter.print( "null" );
					}
					sourceWriter.println( ");" );
					eventsWithHistory.add( event );
				}
			}

			if ( handlers != null ) {
				for ( String handler : handlers ) {
					sourceWriter.print( "if (" );
					sourceWriter.print( handler );
					sourceWriter.println( ".isActivated())" );
					sourceWriter.print( handler );
					sourceWriter.print( "." );
					sourceWriter.print( calledMethod );
					sourceWriter.print( param );
					sourceWriter.println( ";" );
				}
			}
			sourceWriter.outdent();
			sourceWriter.println( "}" );

		}

		if ( eventBus.isWithLookUp() ) {
			writeEventLookUp();
		}
		sourceWriter.println( "};" );

		for ( EventElement event : eventsWithHistory ) {
			sourceWriter.print( "addConverter( \"" );
			sourceWriter.print( event.getType() );
			sourceWriter.print( "\", \"" );
			sourceWriter.print( event.getHistoryName() );
			sourceWriter.print( "\"," );
			sourceWriter.print( event.getHistory() );
			sourceWriter.print( ");" );
		}
	}

	private void writeEventLookUp() {

		sourceWriter.println( "public void dispatch( String eventType, Object... data ){" );
		sourceWriter.indent();

		sourceWriter.println( "try{" );
		sourceWriter.indent();

		String type = null;
		String[] objectClasses = null;
		String param = null;

		for ( EventElement event : configuration.getEvents() ) {
			type = event.getType();

			objectClasses = event.getEventObjectClasses();

			if ( ( objectClasses == null ) || ( objectClasses.length == 0 ) ) {
				param = "();";
			} else {
				int nbParams = objectClasses.length;
				StringBuilder paramBuilder = new StringBuilder( 50 * nbParams );
				int i;
				for ( i = 0; i < ( nbParams - 1 ); i++ ) {
					paramBuilder.append( "(" );
					paramBuilder.append( getAssociatedClass( objectClasses[i] ) );
					paramBuilder.append( ") data[" );
					paramBuilder.append( Integer.toString( i ) );
					paramBuilder.append( "]," );
				}

				paramBuilder.append( "(" );
				paramBuilder.append( getAssociatedClass( objectClasses[i] ) );
				paramBuilder.append( ") data[" );
				paramBuilder.append( Integer.toString( i ) );
				paramBuilder.append( "]" );

				param = "(" + paramBuilder.toString() + ");";

			}

			sourceWriter.print( "if ( \"" );
			sourceWriter.print( type );
			sourceWriter.println( "\".equals( eventType ) ){" );

			sourceWriter.indent();
			sourceWriter.print( type );
			sourceWriter.println( param );
			sourceWriter.outdent();
			sourceWriter.print( "} else " );

		}

		sourceWriter.println( "{" );
		sourceWriter.indent();
		sourceWriter
				.println( "throw new Mvp4gException( \"Event \" + eventType + \" doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?\" );" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.outdent();
		sourceWriter.println( "} catch ( ClassCastException e ) {" );
		sourceWriter.indent();
		sourceWriter.println( "handleClassCastException( e, eventType );" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.outdent();
		sourceWriter.println( "}" );
		sourceWriter.outdent();
	}

	/**
	 * Write the start event tag included in the configuration file
	 * 
	 * Pre-condition: mvp4g configuration has been pre-loaded from configuration file.
	 * 
	 * @throws UnableToCompleteException
	 *             thrown if the start event tag isn't correct.
	 */
	private void writeStartEvent() {

		StartElement start = configuration.getStart();
		// Start view
		sourceWriter.print( "this.startView = " );
		sourceWriter.print( start.getView() );
		sourceWriter.println( ";" );

		String startPresenter = findStartPresenter();

		if ( startPresenter != null ) {
			sourceWriter.print( startPresenter );
			sourceWriter.println( ".setActivated(true);" );
			sourceWriter.print( startPresenter );
			sourceWriter.println( ".isActivated();" );
		}

		if ( start.hasEventType() ) {
			writeDispatchEvent( start.getEventType(), null, configuration.getEventBus().isXml() );
		}

		if ( start.hasHistory() ) {
			sourceWriter.println( "History.fireCurrentHistoryState();" );
		}

	}

	String findStartPresenter() {
		String startPresenter = null;
		String startView = configuration.getStart().getView();
		for ( PresenterElement presenter : configuration.getPresenters() ) {
			if ( startView.equals( presenter.getView() ) ) {
				startPresenter = presenter.getName();
				break;
			}
		}
		return startPresenter;
	}

	/**
	 * Write the lines to create a new instance of an element
	 * 
	 * @param elementName
	 *            name of the element to create
	 * @param className
	 *            class name of the element to create
	 */
	private void createInstance( String elementName, String className ) {
		sourceWriter.print( "final " );
		sourceWriter.print( className );
		sourceWriter.print( " " );
		sourceWriter.print( elementName );
		sourceWriter.print( " = injector.get" );
		sourceWriter.print( elementName );
		sourceWriter.println( "();" );
	}

	/**
	 * Write the lines to inject services into an element
	 * 
	 * @param elementName
	 *            name of the element where services need to be injected
	 * @param services
	 *            name of the services to inject
	 */
	private void injectServices( String elementName, List<InjectedElement> injectedServices ) {
		for ( InjectedElement service : injectedServices ) {
			sourceWriter.print( elementName );
			sourceWriter.println( "." + service.getSetterName() + "(" + service.getElementName() + ");" );
		}
	}

	private void writeParentEvent( EventElement event, String form ) {
		if ( event.hasForwardToParent() ) {
			sourceWriter.print( "parentEventBus." );
			if ( configuration.isParentEventBusXml() ) {
				sourceWriter.print( "dispatch(\"" );
				sourceWriter.print( event.getType() );
				if ( ( form != null ) && ( form.length() > 0 ) ) {
					sourceWriter.print( "\", " );
					sourceWriter.print( form );
					sourceWriter.println( ");" );
				} else {
					sourceWriter.println( "\");" );
				}

			} else {
				sourceWriter.print( event.getType() );
				sourceWriter.print( "(" );
				if ( ( form != null ) && ( form.length() > 0 ) ) {
					sourceWriter.print( form );
				}
				sourceWriter.println( ");" );
			}
		}
	}

	private void writeLoadChildModule( EventElement event, String param ) {

		ChildModuleElement module = null;
		Set<ChildModuleElement> modules = configuration.getChildModules();
		String[] eventObjectClasses = null;
		String eventObject = null;
		String[] modulesToLoad = event.getModulesToLoad();
		if ( modulesToLoad != null ) {
			for ( String moduleName : modulesToLoad ) {
				module = getElement( moduleName, modules );
				eventObjectClasses = event.getEventObjectClasses();
				sourceWriter.print( "load" );
				sourceWriter.print( module.getName() );

				if ( ( eventObjectClasses == null ) || ( eventObjectClasses.length == 0 ) ) {
					eventObject = null;
				} else {
					int nbParam = eventObjectClasses.length;
					StringBuilder eventObjectBuilder = new StringBuilder( nbParam * 70 );

					int i;
					for ( i = 0; i < ( nbParam - 1 ); i++ ) {
						eventObjectBuilder.append( "(" );
						eventObjectBuilder.append( getAssociatedClass( eventObjectClasses[i] ) );
						eventObjectBuilder.append( ") eventObjects[" );
						eventObjectBuilder.append( i );
						eventObjectBuilder.append( "]," );
					}
					eventObjectBuilder.append( "(" );
					eventObjectBuilder.append( getAssociatedClass( eventObjectClasses[i] ) );
					eventObjectBuilder.append( ") eventObjects[" );
					eventObjectBuilder.append( i );
					eventObjectBuilder.append( "]" );
					eventObject = eventObjectBuilder.toString();
				}

				JClassType eventBusType = configuration.getOthersEventBusClassMap().get( module.getClassName() );
				boolean isXml = false;
				String eventBusClass = null;
				if ( eventBusType == null ) {
					isXml = true;
					eventBusClass = EventBusWithLookup.class.getCanonicalName();
				} else {
					eventBusClass = eventBusType.getQualifiedSourceName();
				}
				sourceWriter.print( "(new Mvp4gEventPasser" );
				sourceWriter.print( param );
				sourceWriter.println( "{" );
				sourceWriter.indent();
				sourceWriter.println( "public void pass(Mvp4gModule module){" );
				sourceWriter.indent();
				sourceWriter.print( eventBusClass );
				sourceWriter.print( " eventBus = (" );
				sourceWriter.print( eventBusClass );
				sourceWriter.println( ") module.getEventBus();" );
				writeDispatchEvent( event.getType(), eventObject, isXml );
				sourceWriter.outdent();
				sourceWriter.println( "}" );
				sourceWriter.outdent();
				sourceWriter.println( "});" );

			}
		}

	}

	/**
	 * Retrieve an element exists in a set thanks to its unique identifier
	 * 
	 * @param <T>
	 *            type of the elements in the set
	 * @param elementName
	 *            value of the unique identifier of the element to find
	 * @param elements
	 *            set of elemets
	 * @return found element
	 */
	private <T extends Mvp4gElement> T getElement( String elementName, Set<T> elements ) {
		T eFound = null;
		for ( T element : elements ) {
			if ( element.getUniqueIdentifier().equals( elementName ) ) {
				eFound = element;
				break;
			}
		}

		return eFound;
	}

	private void writeDispatchEvent( String eventType, String form, boolean isXml ) {
		sourceWriter.print( "eventBus." );
		if ( isXml ) {
			sourceWriter.print( "dispatch(\"" );
			sourceWriter.print( eventType );
			if ( ( form != null ) && ( form.length() > 0 ) ) {
				sourceWriter.print( "\", " );
				sourceWriter.print( form );
				sourceWriter.println( ");" );
			} else {
				sourceWriter.println( "\");" );
			}

		} else {
			sourceWriter.print( eventType );
			sourceWriter.print( "(" );
			if ( ( form != null ) && ( form.length() > 0 ) ) {
				sourceWriter.print( form );
			}
			sourceWriter.println( ");" );
		}
	}

	private void writeHistoryConnection() {
		sourceWriter.println( "public void addConverter(String eventType, String historyName, HistoryConverter<?> hc){" );
		sourceWriter.indent();
		if ( configuration.getParentModule() != null ) {
			String historyName = configuration.getHistoryName();
			if ( historyName != null ) {
				sourceWriter.print( "parentModule.addConverter(\"" );
				sourceWriter.print( historyName );
				sourceWriter.print( PlaceService.MODULE_SEPARATOR );
				sourceWriter.print( "\" + eventType, \"" );
				sourceWriter.print( historyName );
				sourceWriter.print( PlaceService.MODULE_SEPARATOR );
				sourceWriter.println( "\" + historyName, hc);" );
			}
		} else {
			sourceWriter.println( "placeService.addConverter(eventType, historyName, hc);" );
		}
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.println( "public void clearHistory(){" );
		sourceWriter.indent();
		if ( configuration.getParentModule() != null ) {
			String historyName = configuration.getHistoryName();
			if ( historyName != null ) {
				sourceWriter.println( "parentModule.clearHistory();" );
			}
		} else {
			sourceWriter.println( "placeService.clearHistory();" );
		}
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.println( "public void place(String token, String form){" );
		sourceWriter.indent();
		if ( configuration.getParentModule() != null ) {
			String historyName = configuration.getHistoryName();
			if ( historyName != null ) {
				sourceWriter.print( "parentModule.place(\"" );
				sourceWriter.print( historyName );
				sourceWriter.print( PlaceService.MODULE_SEPARATOR );
				sourceWriter.println( "\" + token, form );" );
			}
		} else {
			sourceWriter.println( "placeService.place( token, form );" );
		}
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.println( "public void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser passer){" );
		sourceWriter.indent();
		sourceWriter.println( "int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);" );
		sourceWriter.println( "if(index > -1){" );
		sourceWriter.indent();
		sourceWriter.println( "String moduleHistoryName = eventType.substring(0, index);" );
		sourceWriter.println( "String nextToken = eventType.substring(index + 1);" );
		sourceWriter.println( "Mvp4gEventPasser nextPasser = new Mvp4gEventPasser(nextToken) {" );
		sourceWriter.indent();
		sourceWriter.println( "public void pass(Mvp4gModule module) {" );
		sourceWriter.indent();
		sourceWriter.println( "module.dispatchHistoryEvent((String) eventObjects[0], passer);" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );
		sourceWriter.outdent();
		sourceWriter.println( "};" );

		String historyName;
		for ( ChildModuleElement child : configuration.getChildModules() ) {
			historyName = child.getHistoryName();
			if ( ( historyName != null ) && ( historyName.length() > 0 ) ) {
				sourceWriter.print( "if(\"" );
				sourceWriter.print( historyName );
				sourceWriter.println( "\".equals(moduleHistoryName)){" );
				sourceWriter.indent();
				sourceWriter.print( "load" );
				sourceWriter.print( child.getName() );
				sourceWriter.println( "(nextPasser);" );
				sourceWriter.println( "return;" );
				sourceWriter.outdent();
				sourceWriter.println( "}" );
			}
		}

		sourceWriter.println( "passer.setEventObject(false);" );
		sourceWriter.println( "passer.pass(this);" );

		sourceWriter.outdent();
		sourceWriter.println( "}else{" );
		sourceWriter.indent();
		sourceWriter.println( "passer.pass(this);" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );

		sourceWriter.outdent();
		sourceWriter.println( "}" );
	}

	private void writeLog( String type, String[] objectClasses ) {
		DebugElement debug = configuration.getDebug();
		if ( ( debug != null ) && debug.isEnabled() ) {
			sourceWriter.print( "GWT.log(\"Module: " );
			sourceWriter.print( configuration.getModule().getSimpleSourceName() );
			sourceWriter.print( " || event: " );
			sourceWriter.print( type );
			int nbClasses = ( objectClasses == null ) ? 0 : objectClasses.length;
			if ( nbClasses > 0 ) {
				sourceWriter.print( " || object(s): \" + attr0" );
				for ( int i = 1; i < nbClasses; i++ ) {
					sourceWriter.print( "+ \", \" + attr" );
					sourceWriter.print( Integer.toString( i ) );
				}

			} else {
				sourceWriter.print( "\"" );
			}
			sourceWriter.println( ", null);" );
		}
	}

	private String getAssociatedClass( String param ) {
		String paramClass;
		if ( "boolean".equals( param ) ) {
			paramClass = "Boolean";
		} else if ( "byte".equals( param ) ) {
			paramClass = "Byte";
		} else if ( "char".equals( param ) ) {
			paramClass = "Character";
		} else if ( "double".equals( param ) ) {
			paramClass = "Double";
		} else if ( "float".equals( param ) ) {
			paramClass = "Float";
		} else if ( "int".equals( param ) ) {
			paramClass = "Integer";
		} else if ( "long".equals( param ) ) {
			paramClass = "Long";
		} else if ( "short".equals( param ) ) {
			paramClass = "Short";
		} else if ( "void".equals( param ) ) {
			paramClass = "Void";
		} else {
			paramClass = param;
		}
		return paramClass;
	}
}

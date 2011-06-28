/*
 * Copyright 2009 Pierre-Laurent Coirier
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
package com.mvp4g.util.config.loader.annotation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.inject.client.GinModule;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.Forward;
import com.mvp4g.client.annotation.PlaceService;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.DisplayChildModuleView;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.event.Mvp4gLogger;
import com.mvp4g.client.view.NoStartView;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.EventFilterElement;
import com.mvp4g.util.config.element.EventFiltersElement;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.config.element.GinModuleElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>Events</code> annotation.
 * 
 * @author plcoirier
 * 
 */
public class EventsAnnotationsLoader extends Mvp4gAnnotationsLoader<Events> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#loadElement
	 * (com.google.gwt .core.ext.typeinfo.JClassType, java.lang.annotation.Annotation,
	 * com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	protected void loadElement( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		if ( annotation.module().getCanonicalName().equals( configuration.getModule().getQualifiedSourceName() ) ) {

			if ( configuration.getEventBus() != null ) {
				String err = "You can define only one event bus by Mvp4g module. Do you already have another EventBus interface for the module "
						+ annotation.module().getCanonicalName() + "?";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}

			if ( c.isInterface() == null ) {
				String err = Events.class.getSimpleName() + " annotation can only be used on interfaces.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}

			EventBusElement eventBus = buildEventBusElement( c, configuration );

			if ( eventBus != null ) {
				configuration.setEventBus( eventBus );
				loadEventFilters( c, annotation, configuration );
				loadChildModules( c, annotation, configuration );
				loadStartView( c, annotation, configuration );
				loadEvents( c, annotation, configuration );
				loadDebug( c, annotation, configuration );
				loadGinModule( annotation, configuration );
				loadHistoryConfiguration( c, configuration );
			} else {
				String err = "this class must implement " + EventBus.class.getCanonicalName() + " since it is annoted with "
						+ Events.class.getSimpleName() + ".";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}
		} else {
			// save event bus type of potentiel child module
			configuration.getOthersEventBusClassMap().put( annotation.module().getCanonicalName(), c );
			ChildModules children = c.getAnnotation( ChildModules.class );
			if ( children != null ) {
				Map<String, ChildModuleElement> moduleParentEventBus = configuration.getModuleParentEventBusClassMap();
				ChildModuleElement childElement;
				String childClass;
				for ( ChildModule child : children.value() ) {
					childClass = child.moduleClass().getCanonicalName();
					if ( moduleParentEventBus.containsKey( childClass ) ) {
						String err = "Module " + childClass + "can only have 1 parent.";
						throw new Mvp4gAnnotationException( childClass, null, err );
					} else {
						childElement = new ChildModuleElement();
						childElement.setParentEventBus( c );
						try {
							childElement.setAutoDisplay( Boolean.toString( child.autoDisplay() ) );
						} catch ( DuplicatePropertyNameException e ) {
							//this error is never thrown
						}
						moduleParentEventBus.put( childClass, childElement );
					}
				}
			}
		}
	}

	/**
	 * Build event bus element according to the implemented interface.
	 * 
	 * @param c
	 *            annoted class type
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @return event bus corresponding to the implemented interface (null if none of the interfaces
	 *         are implemented)
	 */
	private EventBusElement buildEventBusElement( JClassType c, Mvp4gConfiguration configuration ) {

		TypeOracle oracle = configuration.getOracle();

		EventBusElement eventBus = null;
		if ( c.isAssignableTo( oracle.findType( EventBusWithLookup.class.getCanonicalName() ) ) ) {
			eventBus = new EventBusElement( c.getQualifiedSourceName(), BaseEventBusWithLookUp.class.getCanonicalName(), true );
		} else if ( c.isAssignableTo( oracle.findType( EventBus.class.getCanonicalName() ) ) ) {
			eventBus = new EventBusElement( c.getQualifiedSourceName(), BaseEventBus.class.getCanonicalName(), false );
		}

		return eventBus;
	}

	private void loadEventFilters( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		Filters filters = c.getAnnotation( Filters.class );
		if ( filters != null ) {
			boolean forceFilters = filters.forceFilters();
			Class<? extends EventFilter<?>>[] filterClasses = filters.filterClasses();
			if ( ( ( filterClasses == null ) || ( filterClasses.length == 0 ) ) && !forceFilters ) {
				String err = "Useless "
						+ Filters.class.getSimpleName()
						+ " annotation. Don't use this annotation if your module doesn't have any event filters. If you plan on adding filters when the application runs, set the forceFilters option to true.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}

			Set<EventFilterElement> filterElements = configuration.getEventFilters();
			String filterClassName = null;
			EventFilterElement filterElement = null;
			for ( Class<? extends EventFilter<?>> filterClass : filterClasses ) {
				filterClassName = filterClass.getCanonicalName();
				if ( getElementName( filterElements, filterClassName ) != null ) {
					String err = "Multiple definitions for event filter " + filterClassName + ".";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
				}
				filterElement = new EventFilterElement();
				try {
					filterElement.setName( buildElementName( filterClassName, "" ) );
					filterElement.setClassName( filterClassName );
				} catch ( DuplicatePropertyNameException e ) {
					// setters are only called once, so this error can't occur.
				}
				addElement( filterElements, filterElement, c, null );
			}

			EventFiltersElement filtersElement = new EventFiltersElement();
			try {
				filtersElement.setAfterHistory( Boolean.toString( filters.afterHistory() ) );
				filtersElement.setFilterForward( Boolean.toString( filters.filterForward() ) );
				filtersElement.setFilterStart( Boolean.toString( filters.filterStart() ) );
				filtersElement.setForceFilters( Boolean.toString( filters.forceFilters() ) );
			} catch ( DuplicatePropertyNameException e ) {
				// setters are only called once, so this error can't occur.
			}
			configuration.setEventFilterConfiguration( filtersElement );
		}
	}

	private void loadChildModules( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		ChildModules childModules = c.getAnnotation( ChildModules.class );
		if ( childModules != null ) {
			ChildModule[] children = childModules.value();
			if ( ( children == null ) || ( children.length == 0 ) ) {
				String err = "Useless " + ChildModules.class.getSimpleName()
						+ " annotation. Don't use this annotation if your module doesn't have any child module.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}

			Set<ChildModuleElement> modules = configuration.getChildModules();
			String moduleClass = null;
			ChildModuleElement module = null;
			for ( ChildModule child : children ) {
				moduleClass = child.moduleClass().getCanonicalName();
				if ( getElementName( modules, moduleClass ) != null ) {
					String err = "You can't have two child modules describing the same module: " + child.moduleClass().getCanonicalName();
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
				}
				module = new ChildModuleElement();
				try {
					module.setName( buildElementName( moduleClass, "" ) );
					module.setClassName( moduleClass );
					module.setAsync( Boolean.toString( child.async() ) );
					module.setAutoDisplay( Boolean.toString( child.autoDisplay() ) );
				} catch ( DuplicatePropertyNameException e ) {
					// setters are only called once, so this error can't occur.
				}
				addElement( modules, module, c, null );

			}
		}

	}

	/**
	 * Load information about the start view
	 * 
	 * @param c
	 *            annoted class
	 * @param annotation
	 *            Events annotation of the class
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @throws Mvp4gAnnotationException
	 *             if no view with the given class and name exist
	 */
	private void loadStartView( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		Set<ViewElement> views = configuration.getViews();
		String viewName = annotation.startViewName();
		Class<?> viewClass = annotation.startView();
		boolean hasView = !NoStartView.class.equals( viewClass );
		if ( hasView ) {
			if ( ( viewName != null ) && ( viewName.length() > 0 ) ) {
				boolean found = false;
				for ( ViewElement view : views ) {
					if ( viewName.equals( view.getName() ) ) {
						if ( !viewClass.getCanonicalName().equals( view.getClassName() ) ) {
							String err = "There is no instance of " + viewClass.getCanonicalName() + " with name " + viewName;
							throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
						}
						found = true;
						break;
					}
				}
				if ( !found ) {
					String err = "There is no view named " + viewName;
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
				}

			} else {
				viewName = getElementName( views, viewClass.getCanonicalName() );
				if ( viewName == null ) {
					String err = "There is no instance of " + viewClass.getCanonicalName() + ". Have you forgotten to inject it to a presenter?";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
				}
			}
		}

		try {
			StartElement element = new StartElement();
			if ( hasView ) {
				element.setView( viewName );
			}
			element.setHistory( Boolean.toString( annotation.historyOnStart() ) );
			configuration.setStart( element );
		} catch ( DuplicatePropertyNameException e ) {
			// setters are only called once, so this error can't occur.
		}

	}

	/**
	 * Load events defined by this class
	 * 
	 * @param c
	 *            annoted class
	 * @param annotation
	 *            annotation of the class
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @throws Mvp4gAnnotationException
	 *             if events are properly described
	 */
	private void loadEvents( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		Event event = null;
		EventElement element = null;

		Set<EventElement> events = configuration.getEvents();

		JParameter[] params = null;

		JClassType eventBusWithLookupType = configuration.getOracle().findType( EventBusWithLookup.class.getCanonicalName() );
		JClassType eventBusType = configuration.getOracle().findType( EventBus.class.getCanonicalName() );
		JClassType enclosingType = null;
		String historyName;
		Class<?> broadcast;
		for ( JMethod method : c.getOverridableMethods() ) {
			event = method.getAnnotation( Event.class );
			if ( event == null ) {
				enclosingType = method.getEnclosingType();
				if ( !( eventBusType.equals( enclosingType ) || ( eventBusWithLookupType.equals( enclosingType ) ) ) ) {
					String err = Event.class.getSimpleName() + " annotation missing.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				}
				//in this case, it's a method by Mvp4g EventBus interface, no need to create an event
				continue;
			}

			params = method.getParameters();
			int nbParams = params.length;
			String[] paramClasses;
			if ( nbParams > 0 ) {
				paramClasses = new String[nbParams];
				for ( int i = 0; i < nbParams; i++ ) {
					paramClasses[i] = params[i].getType().getQualifiedSourceName();
				}
			} else {
				paramClasses = null;
			}

			historyName = event.name();

			element = new EventElement();
			try {
				element.setType( method.getName() );
				element.setHandlers( buildPresentersAndEventHandlers( c, method, event.handlers(), event.handlerNames(), configuration ) );
				element.setCalledMethod( event.calledMethod() );
				element.setModulesToLoad( buildChildModules( c, method, event, configuration ) );
				element.setForwardToParent( Boolean.toString( event.forwardToParent() ) );
				element.setActivate( buildPresentersAndEventHandlers( c, method, event.activate(), event.activateNames(), configuration ) );
				element.setDeactivate( buildPresentersAndEventHandlers( c, method, event.deactivate(), event.deactivateNames(), configuration ) );
				element.setGenerate( buildPresentersAndEventHandlers( c, method, event.generate(), event.generateNames(), configuration ) );
				element.setNavigationEvent( Boolean.toString( event.navigationEvent() ) );
				element.setWithTokenGeneration( Boolean.toString( method.getReturnType().getQualifiedSourceName().equals( String.class.getName() ) ) );
				element.setPassive( Boolean.toString( event.passive() ) );
				broadcast = event.broadcastTo();
				if ( !Event.NoBroadcast.class.equals( broadcast ) ) {
					element.setBroadcastTo( broadcast.getCanonicalName() );
				}
				if ( paramClasses != null ) {
					element.setEventObjectClass( paramClasses );
				}
				if ( !Event.DEFAULT_NAME.equals( historyName ) ) {
					element.setName( historyName );
				}
			} catch ( DuplicatePropertyNameException e ) {
				// setters are only called once, so this error can't occur.
			}

			addElement( events, element, c, method );

			if ( method.getAnnotation( Start.class ) != null ) {
				try {
					configuration.getStart().setEventType( method.getName() );
				} catch ( DuplicatePropertyNameException e ) {
					String err = "Duplicate value for Start event. It is already defined by another method.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				}
			}
			if ( method.getAnnotation( Forward.class ) != null ) {
				try {
					configuration.getStart().setForwardEventType( method.getName() );
				} catch ( DuplicatePropertyNameException e ) {
					String err = "Duplicate value for Forward event. It is already defined by another method.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				}
			}
			loadHistoryEvent( c, method, configuration );
			loadHistory( c, method, event, element, configuration );
			loadEventToLoadChildModuleView( c, method, configuration );
			loadChildConfig( c, method, configuration );

		}

	}

	/**
	 * Build handler of the events. If the class name of the handler is given, try to find if an
	 * instance of this class exists, otherwise throw an error.
	 * 
	 * @param c
	 *            annoted class
	 * @param method
	 *            method that defines the event
	 * @param event
	 *            Event Annotation of the method
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @return array of handlers' names
	 * 
	 * @throws Mvp4gAnnotationException
	 *             if no instance of a given handler class can be found
	 */
	private String[] buildPresentersAndEventHandlers( JClassType c, JMethod method,
			Class<? extends EventHandlerInterface<? extends EventBus>>[] presenterAndEventHandlerClasses, String[] presenterAndEventHandlerNames,
			Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		Set<EventHandlerElement> presentersAndEventHandlers = new HashSet<EventHandlerElement>( configuration.getPresenters() );
		presentersAndEventHandlers.addAll( configuration.getEventHandlers() );
		String[] handlers = new String[presenterAndEventHandlerNames.length + presenterAndEventHandlerClasses.length];

		String handlerName = null;
		int index = 0;
		for ( Class<?> handler : presenterAndEventHandlerClasses ) {
			handlerName = getElementName( presentersAndEventHandlers, handler.getCanonicalName() );
			if ( handlerName == null ) {
				String err = "No instance of " + handler.getCanonicalName()
						+ " is defined. Have you forgotten to annotate your event handler with @Presenter or @EventHandler?";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
			handlers[index] = handlerName;
			index++;
		}

		for ( String h : presenterAndEventHandlerNames ) {
			handlers[index] = h;
			index++;
		}

		return handlers;
	}

	private String[] buildChildModules( JClassType c, JMethod method, Event event, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		Set<ChildModuleElement> loadedChildModules = configuration.getChildModules();

		Class<?>[] childModuleClasses = event.modulesToLoad();
		String[] childModules = new String[childModuleClasses.length];

		String moduleName = null;
		int index = 0;
		for ( Class<?> moduleClass : childModuleClasses ) {
			moduleName = getElementName( loadedChildModules, moduleClass.getCanonicalName() );
			if ( moduleName == null ) {
				String err = "No instance of " + moduleClass.getCanonicalName()
						+ " is defined. Have you forgotten to add it to @ChildModules of your event bus interface?";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
			childModules[index] = moduleName;
			index++;
		}

		return childModules;
	}

	/**
	 * Build history converter of an event. If the converter class name is given, first it tries to
	 * find an instance of this class, and if none is found, create one.
	 * 
	 * @param c
	 *            annoted class
	 * @param method
	 *            method that defines the event
	 * @param annotation
	 *            Event annotation
	 * @param element
	 *            Event element
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @throws Mvp4gAnnotationException
	 */
	private void loadHistory( JClassType c, JMethod method, Event annotation, EventElement element, Mvp4gConfiguration configuration )
			throws Mvp4gAnnotationException {
		String hcName = annotation.historyConverterName();
		Class<?> hcClass = annotation.historyConverter();
		if ( ( hcName != null ) && ( hcName.length() > 0 ) ) {
			try {
				element.setHistory( hcName );
			} catch ( DuplicatePropertyNameException e ) {
				// setter is only called once, so this error can't occur.
			}
		} else if ( !Event.NoHistoryConverter.class.equals( hcClass ) ) {
			String hcClassName = hcClass.getCanonicalName();
			Set<HistoryConverterElement> historyConverters = configuration.getHistoryConverters();
			hcName = getElementName( historyConverters, hcClassName );
			if ( hcName == null ) {
				String err = "No instance of " + hcClassName + " is defined. Have you forgotten to annotate your history converter with @History?";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
			try {
				element.setHistory( hcName );
			} catch ( DuplicatePropertyNameException e ) {
				// setters are only called once, so this error can't occur.
			}
		}
	}

	private void loadHistoryEvent( JClassType c, JMethod method, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		if ( method.getAnnotation( InitHistory.class ) != null ) {
			HistoryElement history = configuration.getHistory();
			if ( history == null ) {
				history = new HistoryElement();
				configuration.setHistory( history );
			}

			try {
				history.setInitEvent( method.getName() );
			} catch ( DuplicatePropertyNameException e ) {
				String err = "Duplicate value for Init History event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
		}
		if ( method.getAnnotation( NotFoundHistory.class ) != null ) {
			HistoryElement history = configuration.getHistory();
			if ( history == null ) {
				history = new HistoryElement();
				configuration.setHistory( history );
			}

			try {
				history.setNotFoundEvent( method.getName() );
			} catch ( DuplicatePropertyNameException e ) {
				String err = "Duplicate value for Not Found History event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
		}
	}

	private void loadEventToLoadChildModuleView( JClassType c, JMethod method, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		DisplayChildModuleView annotation = method.getAnnotation( DisplayChildModuleView.class );
		if ( annotation != null ) {
			ChildModuleElement module = null;
			Set<ChildModuleElement> childModules = configuration.getChildModules();
			for ( Class<?> moduleClass : annotation.value() ) {
				module = getElement( childModules, moduleClass.getCanonicalName() );
				if ( module == null ) {
					String err = "No instance of " + moduleClass.getCanonicalName()
							+ " is defined.  Have you forgotten to add it to @ChildModules of your event bus interface?";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				}
				try {
					module.setEventToDisplayView( method.getName() );
				} catch ( DuplicatePropertyNameException e ) {
					String err = "Module " + module.getClassName() + ": you can't have two events to load this module view.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				}

			}
		}
	}

	private void loadChildConfig( JClassType c, JMethod method, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		if ( method.getAnnotation( BeforeLoadChildModule.class ) != null ) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if ( childConfig == null ) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig( childConfig );
			}
			try {
				childConfig.setBeforeEvent( method.getName() );
			} catch ( DuplicatePropertyNameException e ) {
				String err = "Duplicate value for Before Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
		}
		if ( method.getAnnotation( AfterLoadChildModule.class ) != null ) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if ( childConfig == null ) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig( childConfig );
			}
			try {
				childConfig.setAfterEvent( method.getName() );
			} catch ( DuplicatePropertyNameException e ) {
				String err = "Duplicate value for After Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
		}
		if ( method.getAnnotation( LoadChildModuleError.class ) != null ) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if ( childConfig == null ) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig( childConfig );
			}
			try {
				childConfig.setErrorEvent( method.getName() );
			} catch ( DuplicatePropertyNameException e ) {
				String err = "Duplicate value for Error Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
		}

	}

	private void loadDebug( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		Debug debug = c.getAnnotation( Debug.class );

		if ( debug != null ) {
			Class<? extends Mvp4gLogger> loggerClass = debug.logger();
			DebugElement debugElem = new DebugElement();
			try {
				debugElem.setLogger( loggerClass.getCanonicalName() );
				debugElem.setLogLevel( debug.logLevel().name() );
			} catch ( DuplicatePropertyNameException e ) {
				// setter is only called once, so this error can't occur.
			}
			configuration.setDebug( debugElem );
		}
	}

	private void loadGinModule( Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {
		GinModuleElement ginModule = new GinModuleElement();
		Class<? extends GinModule>[] modules = annotation.ginModules();
		int modulesCount = modules.length;
		String[] modulesClassNames = new String[modules.length];
		for ( int i = 0; i < modulesCount; i++ ) {
			modulesClassNames[i] = modules[i].getCanonicalName();
		}
		try {
			ginModule.setModules( modulesClassNames );
			ginModule.setModuleProperties( annotation.ginModuleProperties() );
		} catch ( DuplicatePropertyNameException e ) {
			// setter is only called once, so this error can't occur.
		}
		
		configuration.setGinModule( ginModule );
	}

	private void loadHistoryConfiguration( JClassType c, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		PlaceService historyConfig = c.getAnnotation( PlaceService.class );
		if ( historyConfig != null ) {
			HistoryElement history = configuration.getHistory();
			if ( history == null ) {
				history = new HistoryElement();
				configuration.setHistory( history );
			}

			try {
				history.setPlaceServiceClass( historyConfig.value().getCanonicalName() );
			} catch ( DuplicatePropertyNameException e ) {
				//can't occur
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#controlType
	 * (com.google.gwt .core.ext.typeinfo.JClassType, com.google.gwt.core.ext.typeinfo.JClassType)
	 */
	@Override
	protected void controlType( JClassType c, JClassType s ) {
		// do nothing, control are done later.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#
	 * getMandatoryInterfaceName()
	 */
	@Override
	protected String getMandatoryInterfaceName() {
		return EventBus.class.getCanonicalName();
	}

}

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
package com.mvp4g.util.config;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.inject.client.GinModule;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.client.annotation.module.HistoryName;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.event.Mvp4gLogger;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.client.presenter.NoStartPresenter;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.client.view.ReverseViewInterface;
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
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.config.loader.annotation.EventHandlerAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.EventsAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.HistoryAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.PresenterAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.ServiceAnnotationsLoader;
import com.mvp4g.util.exception.InvalidClassException;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.exception.InvalidTypeException;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.NotFoundClassException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * An in-memory representation of all elements in the configuration file and in the annotations.
 * 
 * @author javier
 * 
 */
public class Mvp4gConfiguration {

	private static final String ROOT_MODULE_CLASS_NAME = Mvp4gModule.class.getCanonicalName();
	private static final String NO_EVENT_BUS = "No Event Bus interface has been found for %s module.";

	private static final String REMOVE_OBJ = "%s %s: No instance of this class has been created since this class is not used.";
	private static final String MISSING_ATTRIBUTE = "%s: child module %s doesn't define any event to load its view.";
	private static final String NOT_EMPTY_EVENT_OBJ = "%s: %s event %s can't have any object associated with it.";
	private static final String WRONG_EVENT_OBJ = "%s: %s event %s can only be associated with one and only one object with type %s";
	private static final String WRONG_NUMBER_ATT = "Event %s: event must have one and only one an object associated with it as it loads a child view.";
	private static final String WRONG_CHILD_LOAD_EVENT_OBJ = "Child Module %s: event %s can not load child module's start view. Can not convert %s to %s.";
	private static final String NO_PARENT_ERROR = "Event %s: Root module has no parent so you can't forward event to parent.";
	private static final String CHILD_MODULE_SAME_HISTORY_NAME = "Module %s: You can't have two child modules with the same history name \"%s\".";
	private static final String ACTIVATE_DEACTIVATE_SAME_TIME = "Event %s: an event can't activate and deactivate the same handler: %s.";
	private static final String NAME_WITH_NO_CONVERTER = "Event %s: you defined an history name for this event but this event has no history converter.";
	private static final String TOKEN_WITH_NO_CONVERTER = "Event %s: you can't generate a token for this event if it has no history converter.";
	private static final String EMPTY_HISTORY_NAME_ROOT = "Event %s: An event of the Mvp4g Root module can't have an history name equal to empty string.";
	private static final String SAME_HISTORY_NAME = "Event %s: history name already used for another event: %s.";
	private static final String WRONG_HISTORY_NAME = "%s %s: history name can't start with '" + PlaceService.CRAWLABLE + "' or contain '"
			+ PlaceService.MODULE_SEPARATOR + "'.";
	private static final String WRONG_FORWARD_EVENT = "You can't define a forward event for RootModule since no event from parent can be forwarded to it.";
	private static final String NO_START_PRESENTER = "Module %s: You must define a start presenter since this module has a parent module that uses the auto-displayed feature for this module.";
	private static final String NO_GIN_MODULE = "You need to define at least one GIN module. If you don't want to specify a GIN module, don't override the GIN modules option to use the default Mvp4g GIN module.";
	private static final String GIN_MODULE_UNKNOWN_PROPERTY = "Module %s: couldn't find a value for the GIN module property %s, %s.";
	private static final String ROOT_MODULE_NO_HISTORY_NAME = "Module %s can't have an history name since it's a root module.";

	private static final String HISTORY_ONLY_FOR_ROOT = "Module %s: History configuration (init, not found event and history parameter separator) should be set only for root module (only module with no parent)";
	private static final String HISTORY_NAME_MISSING = "Module %s: Child module that defines history converter must have a @HistoryName annotation.";
	private static final String HISTORY_INIT_MISSING = "You must define a History init event if you use history converters.";

	private static final String GENERATE_NOT_MULTIPLE = "Event %s: you can generate only multiple handlers. Did you forget to set the attribute multiple to true for %s?";

	private static final String CHILD_MODULE_NOT_USED = "Module %s: the child module %s is not loaded by any of the event of this module. You should remove it if it is not used by another child module (ie used for sibling communication).";
	private static final String UNKNOWN_MODULE = "Event %s: No instance of %s has been found. Is this module a child module, a parent module or a silbling module? If it's supposed to be a child module, have you forgotten to add it to @ChildModules of your event bus interface?";

	private Set<PresenterElement> presenters = new HashSet<PresenterElement>();
	private Set<EventHandlerElement> eventHandlers = new HashSet<EventHandlerElement>();
	private Set<ViewElement> views = new HashSet<ViewElement>();
	private Set<EventElement> events = new HashSet<EventElement>();
	private Set<ServiceElement> services = new HashSet<ServiceElement>();
	private Set<HistoryConverterElement> historyConverters = new HashSet<HistoryConverterElement>();
	private Set<EventFilterElement> eventFilters = new HashSet<EventFilterElement>();
	private Set<ChildModuleElement> childModules = new HashSet<ChildModuleElement>();
	private StartElement start = null;
	private HistoryElement history = null;
	private EventBusElement eventBus = null;
	private JClassType module = null;
	private ChildModulesElement loadChildConfig = null;
	//associate a module class name with its event bus type
	private Map<String, JClassType> othersEventBusClassMap = new HashMap<String, JClassType>();
	//associate a module class name with its parent
	private Map<String, ChildModuleElement> moduleParentEventBusClassMap = new HashMap<String, ChildModuleElement>();
	private JClassType parentEventBus = null;
	private String historyName = null;
	private DebugElement debug = null;
	private GinModuleElement ginModule = null;
	private EventFiltersElement eventFilterConfiguration = null;

	private TreeLogger logger = null;
	private TypeOracle oracle = null;
	private PropertyOracle propertyOracle;

	/**
	 * Contruct a Mvp4gConfiguration object
	 * 
	 * @param logger
	 *            logger of the GWT compiler
	 * @param oracle
	 *            oracle of the GWT compiler
	 */
	public Mvp4gConfiguration( TreeLogger logger, GeneratorContext context ) {
		this.logger = logger;
		this.oracle = context.getTypeOracle();
		this.propertyOracle = context.getPropertyOracle();
	}

	/**
	 * For test purpose only
	 * 
	 * @param logger
	 *            logger to set
	 */
	void setLogger( TreeLogger logger ) {
		this.logger = logger;
	}

	/**
	 * Loads all Mvp4g elements from an in-memory representation of the annotations.</p>
	 * 
	 * Configuration loading comprises up to three phases:
	 * 
	 * <ol>
	 * <li/>Phase 1, <i>Parsing all the annotations</i>: during this phase, all the Mvp4gElement
	 * instances are constructed from their corresponding annotations. All possible validations are
	 * performed.
	 * <li/>Phase 2, <i>Validation of cross-element references and removal of useless elements</i>:
	 * in this phase element identifiers are checked for global uniqueness; event handler references
	 * and view references are checked for existence. Validity elements class is also checked.
	 * </ol>
	 * 
	 * 
	 * @param module
	 *            module to load
	 * @param scanResult
	 *            Map of classes associated with an annotation
	 * @throws InvalidMvp4gConfigurationException
	 *             this exception is thrown where a configuration error occurs.
	 * 
	 */
	public String[] load( JClassType module, Map<Class<? extends Annotation>, List<JClassType>> scanResult )
			throws InvalidMvp4gConfigurationException {

		this.module = module;

		// Phase 1: load information from annotations
		loadServices( scanResult.get( Service.class ) );
		loadHistoryConverters( scanResult.get( History.class ) );
		loadPresenters( scanResult.get( Presenter.class ) );
		loadEventHandlers( scanResult.get( EventHandler.class ) );
		loadEvents( scanResult.get( Events.class ) );
		loadParentModule();

		// Phase 2: perform cross-element validations
		if ( eventBus == null ) {
			throw new InvalidMvp4gConfigurationException( String.format( NO_EVENT_BUS, module.getSimpleSourceName() ) );
		}

		findChildModuleHistoryName();
		checkUniquenessOfAllElements();
		validateEventHandlers();
		validateEventFilters();
		validateHistoryConverters();
		validateViews();
		validateServices();
		validateHistory();
		validateChildModules();
		validateEvents();
		validateDebug();
		String[] propertiesValues = validateGinModule();
		validateStart();

		return propertiesValues;
	}

	public boolean isAsyncEnabled() {
		return ( oracle.findType( "com.google.gwt.core.client.RunAsyncCallback" ) != null );
	}

	/*
	 * GETTERS & SETTERS
	 */
	/**
	 * @return a set of Presenters loaded.
	 */
	public Set<PresenterElement> getPresenters() {
		return presenters;
	}

	/**
	 * @return a set of Presenters loaded.
	 */
	public Set<EventHandlerElement> getEventHandlers() {
		return eventHandlers;
	}

	/**
	 * @return a set of Views loaded.
	 */
	public Set<ViewElement> getViews() {
		return views;
	}

	/**
	 * @return a set of History Converters loaded.
	 */
	public Set<HistoryConverterElement> getHistoryConverters() {
		return historyConverters;
	}

	/**
	 * @return a set of Events loaded.
	 */
	public Set<EventElement> getEvents() {
		return events;
	}

	/**
	 * @return a set of Services loaded.
	 */
	public Set<ServiceElement> getServices() {
		return services;
	}

	/**
	 * @return a set of Event Filters loaded
	 */
	public Set<EventFilterElement> getEventFilters() {
		return eventFilters;
	}

	/**
	 * @return a set of Child Modules loaded
	 */
	public Set<ChildModuleElement> getChildModules() {
		return childModules;
	}

	/**
	 * @return the Start element loaded.
	 */
	public StartElement getStart() {
		return start;
	}

	/**
	 * @param start
	 *            the start to set
	 */
	public void setStart( StartElement start ) {
		this.start = start;
	}

	/**
	 * @return the History element loaded.
	 */
	public HistoryElement getHistory() {
		return history;
	}

	/**
	 * @param history
	 *            the history to set
	 */
	public void setHistory( HistoryElement history ) {
		this.history = history;
	}

	/**
	 * @return the Event Bus element loaded
	 */
	public EventBusElement getEventBus() {
		return eventBus;
	}

	/**
	 * @param eventBus
	 *            the eventBus to set
	 */
	public void setEventBus( EventBusElement eventBus ) {
		this.eventBus = eventBus;
	}

	/**
	 * @return the oracle
	 */
	public TypeOracle getOracle() {
		return oracle;
	}

	/**
	 * @return the module
	 */
	public JClassType getModule() {
		return module;
	}

	/**
	 * Should be used only for test
	 * 
	 * @param module
	 *            the module to set
	 */
	public void setModule( JClassType module ) {
		this.module = module;
	}

	/**
	 * Should be used only for test
	 * 
	 * @param parentEventBus
	 *            the parentEventBus to set
	 */
	public void setParentEventBus( JClassType parentEventBus ) {
		this.parentEventBus = parentEventBus;
	}

	/**
	 * Should be used only for test
	 * 
	 * @param historyName
	 *            the historyName to set
	 */
	public void setHistoryName( String historyName ) {
		this.historyName = historyName;
	}

	/**
	 * @return the childEventBusClassMap
	 */
	public Map<String, JClassType> getOthersEventBusClassMap() {
		return othersEventBusClassMap;
	}

	/**
	 * @return the moduleParentEventBusClassMap
	 */
	public Map<String, ChildModuleElement> getModuleParentEventBusClassMap() {
		return moduleParentEventBusClassMap;
	}

	/**
	 * @return the loadChildConfig
	 */
	public ChildModulesElement getLoadChildConfig() {
		return loadChildConfig;
	}

	/**
	 * @param loadChildConfig
	 *            the loadChildConfig to set
	 */
	public void setLoadChildConfig( ChildModulesElement loadChildConfig ) {
		this.loadChildConfig = loadChildConfig;
	}

	/**
	 * @return the parentModule
	 */
	public boolean isRootModule() {
		return ROOT_MODULE_CLASS_NAME.equals( module.getQualifiedSourceName() ) || ( parentEventBus == null );
	}

	/**
	 * @return the parentEventBus
	 */
	public JClassType getParentEventBus() {
		return parentEventBus;
	}

	/**
	 * @return the historyName
	 */
	public String getHistoryName() {
		return historyName;
	}

	/**
	 * @return the debug
	 */
	public DebugElement getDebug() {
		return debug;
	}

	/**
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug( DebugElement debug ) {
		this.debug = debug;
	}

	/**
	 * @return the ginModule
	 */
	public GinModuleElement getGinModule() {
		return ginModule;
	}

	/**
	 * @param ginModule
	 *            the ginModule to set
	 */
	public void setGinModule( GinModuleElement ginModule ) {
		this.ginModule = ginModule;
	}

	/**
	 * @return the filters
	 */
	public EventFiltersElement getEventFilterConfiguration() {
		return eventFilterConfiguration;
	}

	/**
	 * @param eventFilterConfiguration
	 *            the eventFilterConfiguration to set
	 */
	public void setEventFilterConfiguration( EventFiltersElement eventFilterConfiguration ) {
		this.eventFilterConfiguration = eventFilterConfiguration;
	}

	/*
	 * Validation
	 */

	/**
	 * Checks that all injected views correspond to a configured mvp4g element. Remove views that
	 * aren't injected into a presenter or loaded at start.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a view reference cannot be found among the configured elements.
	 */
	void validateViews() throws UnknownConfigurationElementException {

		Map<String, List<PresenterElement>> viewMap = new HashMap<String, List<PresenterElement>>();

		List<PresenterElement> presenterList = null;
		String viewName = null;
		for ( PresenterElement presenter : presenters ) {
			viewName = presenter.getView();
			presenterList = viewMap.get( viewName );
			if ( presenterList == null ) {
				presenterList = new ArrayList<PresenterElement>();
				viewMap.put( viewName, presenterList );
			}
			presenterList.add( presenter );
		}

		Set<ViewElement> toRemove = new HashSet<ViewElement>();
		for ( ViewElement view : views ) {
			viewName = view.getName();
			if ( ( viewMap.remove( viewName ) == null ) ) {
				// this object is not used, you can remove it
				toRemove.add( view );
			}
		}

		// Missing view
		if ( !viewMap.isEmpty() ) {
			String it = viewMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( viewMap.get( it ).get( 0 ), it );
		}

		removeUselessElements( views, toRemove );

	}

	/**
	 * Checks that all service names injected on every presenter and history converter correspond to
	 * a configured mvp4g element. Remove all services that aren't injected into a presenter or an
	 * history converter.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a service cannot be found among the configured elements.
	 */
	void validateServices() throws UnknownConfigurationElementException {

		Map<String, List<Mvp4gWithServicesElement>> serviceMap = new HashMap<String, List<Mvp4gWithServicesElement>>();

		Set<Mvp4gWithServicesElement> currentElements = new HashSet<Mvp4gWithServicesElement>( presenters );
		currentElements.addAll( eventHandlers );
		currentElements.addAll( historyConverters );

		List<Mvp4gWithServicesElement> elementList = null;
		for ( Mvp4gWithServicesElement elementWithService : currentElements ) {
			for ( InjectedElement service : elementWithService.getInjectedServices() ) {
				elementList = serviceMap.get( service.getElementName() );
				if ( elementList == null ) {
					elementList = new ArrayList<Mvp4gWithServicesElement>();
					serviceMap.put( service.getElementName(), elementList );
				}
				elementList.add( elementWithService );
			}
		}

		Set<ServiceElement> toRemove = new HashSet<ServiceElement>();
		for ( ServiceElement service : services ) {
			if ( serviceMap.remove( service.getName() ) == null ) {
				// this object is not used, you can remove it
				toRemove.add( service );
			}
		}

		// Missing service
		if ( !serviceMap.isEmpty() ) {
			String it = serviceMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( serviceMap.get( it ).get( 0 ), it );
		}

		removeUselessElements( services, toRemove );

	}

	/**
	 * Checks that all history converter names associated to each event is a configured mvp4g
	 * element. Verify that these elements are valid. Remove all history converters that aren't used
	 * by an event.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 */
	void validateHistoryConverters() throws InvalidMvp4gConfigurationException {

		Map<String, List<EventElement>> historyConverterMap = new HashMap<String, List<EventElement>>();

		List<EventElement> eventList = null;
		String hcName = null;
		String historyName;
		List<String> historyNames = new ArrayList<String>();
		for ( EventElement event : events ) {
			if ( event.hasHistory() ) {
				hcName = event.getHistory();
				eventList = historyConverterMap.get( hcName );
				if ( eventList == null ) {
					eventList = new ArrayList<EventElement>();
					historyConverterMap.put( hcName, eventList );
				}
				eventList.add( event );
				historyName = event.getName();
				if ( isRootModule() && ( historyName.length() == 0 ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( EMPTY_HISTORY_NAME_ROOT, event.getType() ) );
				}
				validateHistoryName( historyName, event );
				if ( historyNames.contains( historyName ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( SAME_HISTORY_NAME, event.getType(), event.getName(), historyName ) );
				}
				historyNames.add( historyName );
			} else if ( event.isWithTokenGeneration() ) {
				if ( isRootModule() || !checkIfParentEventReturnsString( event ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( TOKEN_WITH_NO_CONVERTER, event.getType() ) );
				} else {
					event.setTokenGenerationFromParent( Boolean.toString( Boolean.TRUE ) );
				}
			} else if ( event.getName() != event.getType() ) {
				throw new InvalidMvp4gConfigurationException( String.format( NAME_WITH_NO_CONVERTER, event.getType() ) );
			}
		}

		JGenericType hcGenType = getType( null, HistoryConverter.class.getCanonicalName() ).isGenericType();
		JClassType eventBusType = getType( null, eventBus.getInterfaceClassName() );
		String clearHistoryClassName = ClearHistory.class.getCanonicalName();
		JClassType clearHistoryType = getType( null, clearHistoryClassName );
		JClassType hcType = null;
		JClassType eventBusParam = null;
		JMethod[] methods = null;
		JParameterizedType genHC = null;
		HistoryConverterElement clearHistoryToRemove = null;

		Set<HistoryConverterElement> toRemove = new HashSet<HistoryConverterElement>();
		for ( HistoryConverterElement history : historyConverters ) {
			eventList = historyConverterMap.remove( history.getName() );
			if ( eventList != null ) {

				hcType = getType( history, history.getClassName() );

				// if historyConverter is a ClearHistory instance, no control
				// needed
				if ( !clearHistoryType.equals( hcType ) ) {

					genHC = hcType.asParameterizationOf( hcGenType );
					if ( genHC == null ) {
						throw new InvalidClassException( history, HistoryConverter.class.getCanonicalName() );
					}

					methods = genHC.getMethods();

					eventBusParam = (JClassType)methods[0].getParameters()[2].getType();

					// Control if history converter event bus is compatible with
					// module event bus
					if ( !eventBusType.isAssignableTo( eventBusParam ) ) {
						throw new InvalidTypeException( history, "Event Bus", eventBusParam.getQualifiedSourceName(),
								eventBus.getInterfaceClassName() );
					}
				}
			} else {
				if ( !clearHistoryClassName.equals( history.getClassName() ) ) {
					// this object is not used, you can remove it
					toRemove.add( history );
				} else {
					clearHistoryToRemove = history;
				}
			}
		}

		// Missing history converter
		if ( !historyConverterMap.isEmpty() ) {
			String it = historyConverterMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( historyConverterMap.get( it ).get( 0 ), it );
		}

		// remove clear history (you don't want this to appear on log)
		if ( clearHistoryToRemove != null ) {
			historyConverters.remove( clearHistoryToRemove );
		}

		removeUselessElements( historyConverters, toRemove );

	}

	/**
	 * Checks that all event handler names correspond to a configured mvp4g element. Verify that
	 * these elements are valid. Remove the ones that don't handle events or aren't associated with
	 * the start view.</p>
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 */
	void validateEventHandlers() throws InvalidMvp4gConfigurationException {

		Map<String, List<EventElement>> presenterAndEventHandlerMap = new HashMap<String, List<EventElement>>();
		Map<String, List<EventElement>> activateMap = new HashMap<String, List<EventElement>>();
		Map<String, List<EventElement>> deactivateMap = new HashMap<String, List<EventElement>>();
		Map<String, List<EventElement>> generateMap = new HashMap<String, List<EventElement>>();
		Map<JClassType, List<EventElement>> broadcastMap = new HashMap<JClassType, List<EventElement>>();

		// Add presenter that handles event
		List<EventElement> eventList;
		List<String> activates, deactivates, handlers;
		String[] generates;
		String broadcast;
		JClassType type;
		for ( EventElement event : events ) {
			handlers = event.getHandlers();
			activates = event.getActivate();
			deactivates = event.getDeactivate();
			broadcast = event.getBroadcastTo();
			generates = event.getGenerate();
			if ( handlers != null ) {
				for ( String handler : handlers ) {
					eventList = presenterAndEventHandlerMap.get( handler );
					if ( eventList == null ) {
						eventList = new ArrayList<EventElement>();
						presenterAndEventHandlerMap.put( handler, eventList );
					}
					eventList.add( event );
				}
			}
			if ( activates != null ) {

				for ( String activate : activates ) {
					eventList = presenterAndEventHandlerMap.get( activate );
					if ( ( deactivates != null ) && ( deactivates.contains( activate ) ) ) {
						throw new InvalidMvp4gConfigurationException( String.format( ACTIVATE_DEACTIVATE_SAME_TIME, event, activate ) );
					}

					if ( eventList == null ) {
						eventList = new ArrayList<EventElement>();
						activateMap.put( activate, eventList );
					}
					eventList.add( event );
				}
			}
			if ( deactivates != null ) {
				for ( String deactivate : deactivates ) {
					eventList = presenterAndEventHandlerMap.get( deactivate );
					if ( eventList == null ) {
						eventList = new ArrayList<EventElement>();
						deactivateMap.put( deactivate, eventList );
					}
					eventList.add( event );
				}
			}
			if ( broadcast != null ) {
				type = getType( event, broadcast );
				eventList = broadcastMap.get( type );
				if ( eventList == null ) {
					eventList = new ArrayList<EventElement>();
					broadcastMap.put( type, eventList );
				}
				eventList.add( event );
			}
			if ( generates != null ) {
				if ( handlers == null ) {
					event.setHandlers( new String[0] );
					handlers = event.getHandlers();
				}

				for ( String generate : generates ) {
					eventList = generateMap.get( generate );
					if ( eventList == null ) {
						eventList = new ArrayList<EventElement>();
						generateMap.put( generate, eventList );
					}
					eventList.add( event );
					if ( !handlers.contains( generate ) ) {
						handlers.add( generate );
					}
				}
			}
		}

		boolean hasStartView = start.hasPresenter();
		String startPresenter = start.getPresenter();
		JGenericType presenterGenType = getType( null, PresenterInterface.class.getCanonicalName() ).isGenericType();
		JGenericType eventHandlerGenType = getType( null, EventHandlerInterface.class.getCanonicalName() ).isGenericType();
		JGenericType reverseViewGenType = getType( null, ReverseViewInterface.class.getCanonicalName() ).isGenericType();
		JClassType eventBusType = getType( null, eventBus.getInterfaceClassName() );
		JType[] noParam = new JType[0];
		JClassType presenterType, viewParam, presenterParam, viewType;
		ViewElement view = null;
		JParameterizedType genPresenter, genView;
		boolean hasPossibleBroadcast = ( broadcastMap.size() > 0 );
		Set<PresenterElement> toRemove = new HashSet<PresenterElement>();
		String viewName, name;
		boolean toKeep, notDirectHandler;
		List<EventElement> eventDeactivateList, eventActivateList, eventGenerateList;
		for ( PresenterElement presenter : presenters ) {
			name = presenter.getName();
			eventList = presenterAndEventHandlerMap.remove( name );
			eventDeactivateList = deactivateMap.remove( name );
			eventActivateList = activateMap.remove( name );
			eventGenerateList = generateMap.remove( name );
			viewName = presenter.getView();

			toKeep = ( eventList != null ) || ( hasStartView && name.equals( startPresenter ) );
			notDirectHandler = !toKeep && ( presenter.isMultiple() || hasPossibleBroadcast );
			if ( toKeep || notDirectHandler ) {
				toKeep = controlEventBus( presenter, eventHandlerGenType, eventBusType, toKeep );
				if ( toKeep ) {

					presenterType = getType( presenter, presenter.getClassName() );

					toKeep = findPossibleBroadcast( broadcastMap, presenter, presenterType ) || !notDirectHandler || presenter.isMultiple();

					if ( toKeep ) {
						genPresenter = presenterType.asParameterizationOf( presenterGenType );
						if ( genPresenter == null ) {
							throw new InvalidClassException( presenter, PresenterInterface.class.getCanonicalName() );
						}

						viewParam = (JClassType)genPresenter.findMethod( "getView", noParam ).getReturnType();

						// Control if view injected to the event bus is compatible with
						// presenter view type
						view = getElement( viewName, views, presenter );
						viewType = getType( view, view.getClassName() );
						if ( !viewType.isAssignableTo( viewParam ) ) {
							throw new InvalidTypeException( presenter, "View", view.getClassName(), viewParam.getQualifiedSourceName() );
						}

						if ( viewType.isAssignableTo( reverseViewGenType ) ) {
							genView = viewType.asParameterizationOf( reverseViewGenType );
							presenterParam = (JClassType)genView.findMethod( "getPresenter", noParam ).getReturnType();
							if ( !presenterType.isAssignableTo( presenterParam ) ) {
								throw new InvalidTypeException( view, "Presenter", presenter.getClassName(), presenterParam.getQualifiedSourceName() );
							}
							presenter.setInverseView( Boolean.TRUE.toString() );

						}

						if ( !presenter.isMultiple() ) {
							view.setInstantiateAtStart( true );
						}
					}
				}

			}

			if ( !toKeep ) {
				removeFromActivateDeactivate( eventActivateList, eventDeactivateList, presenter );
				// this object is not used, you can remove it
				toRemove.add( presenter );
			}

			if ( ( eventGenerateList != null ) && ( !presenter.isMultiple() ) ) {
				throw new InvalidMvp4gConfigurationException( String.format( GENERATE_NOT_MULTIPLE, eventGenerateList.get( 0 ).getType(),
						presenter.getName() ) );
			}
		}

		Set<EventHandlerElement> toRemoveEventHandlers = new HashSet<EventHandlerElement>();
		for ( EventHandlerElement eventHandler : eventHandlers ) {
			name = eventHandler.getName();
			eventList = presenterAndEventHandlerMap.remove( name );
			eventDeactivateList = deactivateMap.remove( name );
			eventActivateList = activateMap.remove( name );
			eventGenerateList = generateMap.remove( name );

			toKeep = ( eventList != null );
			notDirectHandler = !toKeep && ( eventHandler.isMultiple() || hasPossibleBroadcast );
			if ( toKeep || notDirectHandler ) {
				toKeep = controlEventBus( eventHandler, eventHandlerGenType, eventBusType, toKeep );
				if ( toKeep ) {
					toKeep = findPossibleBroadcast( broadcastMap, eventHandler, getType( eventHandler, eventHandler.getClassName() ) )
							|| !notDirectHandler || eventHandler.isMultiple();
				}
			}

			if ( !toKeep ) {
				removeFromActivateDeactivate( eventActivateList, eventDeactivateList, eventHandler );
				// this object is not used, you can remove it
				toRemoveEventHandlers.add( eventHandler );
			}

			if ( ( eventGenerateList != null ) && ( !eventHandler.isMultiple() ) ) {
				throw new InvalidMvp4gConfigurationException( String.format( GENERATE_NOT_MULTIPLE, eventGenerateList.get( 0 ).getType(),
						eventHandler.getName() ) );
			}
		}

		// Missing handlers
		if ( !presenterAndEventHandlerMap.isEmpty() ) {
			String it = presenterAndEventHandlerMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( presenterAndEventHandlerMap.get( it ).get( 0 ), it );
		}
		if ( !activateMap.isEmpty() ) {
			String it = activateMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( activateMap.get( it ).get( 0 ), it );
		}

		if ( !deactivateMap.isEmpty() ) {
			String it = deactivateMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( deactivateMap.get( it ).get( 0 ), it );
		}
		if ( !generateMap.isEmpty() ) {
			String it = generateMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( generateMap.get( it ).get( 0 ), it );
		}

		removeUselessElements( presenters, toRemove );
		removeUselessElements( eventHandlers, toRemoveEventHandlers );
	}

	boolean findPossibleBroadcast( Map<JClassType, List<EventElement>> broadcastMap, EventHandlerElement eventHandler, JClassType handler )
			throws InvalidClassException, InvalidTypeException, NotFoundClassException {

		boolean keep = false;
		Iterator<JClassType> it = broadcastMap.keySet().iterator();
		JClassType type;

		String handlerName = eventHandler.getName();

		while ( it.hasNext() ) {
			type = it.next();
			if ( handler.isAssignableTo( type ) ) {
				List<String> handlers;
				for ( EventElement event : broadcastMap.get( type ) ) {
					handlers = event.getHandlers();
					if ( !handlers.contains( handlerName ) ) {
						handlers.add( handlerName );
					}
				}
				keep = true;
			}
		}
		return keep;
	}

	/**
	 * Verify that filters are valid.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 */
	void validateEventFilters() throws InvalidMvp4gConfigurationException {

		JGenericType filterGenType = getType( null, EventFilter.class.getCanonicalName() ).isGenericType();
		JClassType eventBusType = getType( null, eventBus.getInterfaceClassName() );
		JClassType filterType, eventBusParam;
		JParameterizedType genEventFilter;
		for ( EventFilterElement filter : eventFilters ) {
			filterType = getType( filter, filter.getClassName() );
			genEventFilter = filterType.asParameterizationOf( filterGenType );
			if ( genEventFilter == null ) {
				throw new InvalidClassException( filter, EventFilter.class.getCanonicalName() );
			}

			eventBusParam = (JClassType)genEventFilter.getMethods()[0].getParameters()[2].getType();

			// Control if filter event bus is compatible with module
			// event bus
			if ( !eventBusType.isAssignableTo( eventBusParam ) ) {
				throw new InvalidTypeException( filter, "Event Bus", eventBus.getInterfaceClassName(), eventBusParam.getQualifiedSourceName() );
			}
		}
	}

	/**
	 * Control if the event bus of the handler is compatible with the module event bus.
	 * 
	 * If the handler event bus is not compatible and is not multiple, then throw an exception.
	 * 
	 * If the handler is multiple, then it's an handler of another module, just ignore it by
	 * returning false
	 * 
	 * 
	 * @param eventHandler
	 * @param eventHandlerGenType
	 * @param eventBusType
	 * @return true if no error, false if the presenter has to be ignored.
	 * @throws InvalidClassException
	 *             throws if the handler event bus is not compatible
	 * @throws InvalidTypeException
	 *             throws if the handler event bus is not compatible
	 * @throws NotFoundClassException
	 *             throws if the handler event bus is not compatible
	 */
	private boolean controlEventBus( EventHandlerElement eventHandler, JGenericType eventHandlerGenType, JClassType eventBusType,
			boolean directHandler ) throws InvalidClassException, InvalidTypeException, NotFoundClassException {

		JClassType eventHandlerType = getType( eventHandler, eventHandler.getClassName() );
		JParameterizedType genEventHandler = eventHandlerType.asParameterizationOf( eventHandlerGenType );
		if ( genEventHandler == null ) {
			if ( directHandler ) {
				throw new InvalidClassException( eventHandler, EventHandlerInterface.class.getCanonicalName() );
			} else {
				return false;
			}
		}

		JClassType eventBusParam = (JClassType)genEventHandler.findMethod( "getEventBus", new JType[0] ).getReturnType();

		// Control if presenter event bus is compatible with module
		// event bus
		if ( !eventBusType.isAssignableTo( eventBusParam ) ) {
			if ( directHandler ) {
				throw new InvalidTypeException( eventHandler, "Event Bus", eventBus.getInterfaceClassName(), eventBusParam.getQualifiedSourceName() );
			} else {
				return false;
			}
		}

		return true;
	}

	private void removeFromActivateDeactivate( List<EventElement> eventActivateList, List<EventElement> eventDeactivateList,
			EventHandlerElement eventHandler ) {
		if ( eventActivateList != null ) {
			for ( EventElement event : eventActivateList ) {
				event.getActivate().remove( eventHandler.getName() );
			}
		}
		if ( eventDeactivateList != null ) {
			for ( EventElement event : eventDeactivateList ) {
				event.getDeactivate().remove( eventHandler.getName() );
			}
		}
	}

	void validateChildModules() throws InvalidMvp4gConfigurationException {

		Map<String, List<EventElement>> childModuleMap = new HashMap<String, List<EventElement>>();
		Map<JClassType, List<EventElement>> broadcastMap = new HashMap<JClassType, List<EventElement>>();

		// Add presenter that handles event
		List<EventElement> eventList = null;
		List<String> modulesToLoad;
		String broadcast;
		JClassType type;
		for ( EventElement event : events ) {
			modulesToLoad = event.getForwardToModules();
			broadcast = event.getBroadcastTo();
			if ( modulesToLoad != null ) {
				for ( String childModule : modulesToLoad ) {
					eventList = childModuleMap.get( childModule );
					if ( eventList == null ) {
						eventList = new ArrayList<EventElement>();
						childModuleMap.put( childModule, eventList );
					}
					eventList.add( event );
				}
			}
			if ( broadcast != null ) {
				type = getType( event, broadcast );
				eventList = broadcastMap.get( type );
				if ( eventList == null ) {
					eventList = new ArrayList<EventElement>();
					broadcastMap.put( type, eventList );
				}
				eventList.add( event );
			}
		}

		JClassType moduleSuperClass = getType( null, Mvp4gModule.class.getCanonicalName() );
		JClassType moduleType = null;

		String eventName = null;
		EventElement eventElt = null;
		String[] eventObjClasses = null;
		String childModuleClass = null;
		JClassType childEventBus = null;
		JClassType startPresenterType = null;
		JClassType startViewType = null;
		String startPresenterClass = null;
		JGenericType presenterGenType = getType( null, PresenterInterface.class.getCanonicalName() ).isGenericType();
		JType[] noParam = new JType[0];
		for ( ChildModuleElement childModule : childModules ) {
			eventList = childModuleMap.remove( childModule.getName() );
			childModuleClass = childModule.getClassName();
			moduleType = getType( childModule, childModuleClass );
			if ( findPossibleModuleBroadcast( broadcastMap, childModule, moduleType ) || ( eventList != null ) ) {
				if ( !moduleType.isAssignableTo( moduleSuperClass ) ) {
					throw new InvalidClassException( childModule, Mvp4gModule.class.getCanonicalName() );
				}

				childEventBus = othersEventBusClassMap.get( childModuleClass );

				if ( childModule.isAutoDisplay() ) {
					eventName = childModule.getEventToDisplayView();
					if ( ( eventName == null ) || ( eventName.length() == 0 ) ) {
						String error = String.format( MISSING_ATTRIBUTE, module.getQualifiedSourceName(), childModule.getClassName() );
						throw new InvalidMvp4gConfigurationException( error );
					}
					// verify event exists
					eventElt = getElement( eventName, events, childModule );
					eventObjClasses = eventElt.getEventObjectClass();
					if ( ( eventObjClasses == null ) || ( eventObjClasses.length != 1 ) ) {
						throw new InvalidMvp4gConfigurationException( String.format( WRONG_NUMBER_ATT, eventElt.getType() ) );
					}

					startPresenterClass = childEventBus.getAnnotation( Events.class ).startPresenter().getCanonicalName();

					if ( startPresenterClass.equals( NoStartPresenter.class.getCanonicalName() ) ) {
						throw new InvalidMvp4gConfigurationException( String.format( NO_START_PRESENTER, childModule.getClassName() ) );
					}

					startPresenterType = oracle.findType( startPresenterClass ).asParameterizationOf( presenterGenType );
					startViewType = (JClassType)startPresenterType.findMethod( "getView", noParam ).getReturnType();
					if ( !startViewType.isAssignableTo( getType( eventElt, eventObjClasses[0] ) ) ) {
						throw new InvalidMvp4gConfigurationException( String.format( WRONG_CHILD_LOAD_EVENT_OBJ, childModule.getClassName(),
								eventElt.getType(), startViewType.getQualifiedSourceName(), eventObjClasses[0] ) );
					}
				}
			} else {
				// this object is not used, could be used by one of the child module so display a warning
				logger.log( Type.WARN, String.format( CHILD_MODULE_NOT_USED, module.getName(), childModule.getName() ) );
			}
		}

		List<ChildModuleElement> siblings = getSiblings();
		List<String> siblingsToLoad;
		String siblingClassName;
		for ( ChildModuleElement sibling : siblings ) {
			siblingClassName = sibling.getClassName();
			eventList = childModuleMap.remove( siblingClassName );
			findPossibleSiblingBroadcast( broadcastMap, sibling );
			if ( eventList != null ) {
				for ( EventElement event : eventList ) {
					siblingsToLoad = event.getSiblingsToLoad();
					modulesToLoad = event.getForwardToModules();
					if ( !siblingsToLoad.contains( siblingClassName ) ) {
						siblingsToLoad.add( siblingClassName );
					}
					modulesToLoad.remove( siblingClassName );
				}
			}
		}

		ChildModuleElement currentModule = moduleParentEventBusClassMap.get( module.getQualifiedSourceName() );
		if ( currentModule != null ) {
			String parentModuleClassName = currentModule.getParentModuleClass();
			findPossibleParentBroadcast(broadcastMap, currentModule, parentModuleClassName);
			Iterator<String> it = childModuleMap.keySet().iterator();
			String trueStr = Boolean.TRUE.toString();
			if ( it.hasNext() ) {
				String moduleName = it.next();
				if ( parentModuleClassName.equals( moduleName ) ) {
					eventList = childModuleMap.remove( moduleName );
					for ( EventElement event : eventList ) {
						event.getForwardToModules().remove( parentModuleClassName );
						event.setForwardToParent( trueStr );
					}
				}
			}
		}

		// Missing child modules
		if ( !childModuleMap.isEmpty() ) {
			String next = childModuleMap.keySet().iterator().next();
			throw new InvalidMvp4gConfigurationException( String.format( UNKNOWN_MODULE, childModuleMap.get( next ).get( 0 ).getName(), next ) );
		}

	}

	List<ChildModuleElement> getSiblings() {
		List<ChildModuleElement> siblings = new ArrayList<ChildModuleElement>();

		JClassType parentEventBus = findParentEventBus( module.getQualifiedSourceName() );
		if ( parentEventBus != null ) {
			Iterator<String> it = moduleParentEventBusClassMap.keySet().iterator();
			String currentModule = module.getQualifiedSourceName();
			ChildModuleElement childModule;
			while ( it.hasNext() ) {
				childModule = moduleParentEventBusClassMap.get( it.next() );
				if ( parentEventBus.equals( childModule.getParentEventBus() ) && !currentModule.equals( childModule.getClassName() ) ) {
					siblings.add( childModule );
				}
			}
		}
		return siblings;
	}

	boolean findPossibleModuleBroadcast( Map<JClassType, List<EventElement>> broadcastMap, ChildModuleElement childModuleElement,
			JClassType childModule ) {
		boolean keep = false;
		Iterator<JClassType> it = broadcastMap.keySet().iterator();
		JClassType type;

		String moduleName = childModuleElement.getName();

		while ( it.hasNext() ) {
			type = it.next();
			if ( childModule.isAssignableTo( type ) ) {
				List<String> modules;
				for ( EventElement event : broadcastMap.get( type ) ) {
					modules = event.getForwardToModules();
					if ( !modules.contains( moduleName ) ) {
						modules.add( moduleName );
					}
				}
				keep = true;
			}
		}
		return keep;
	}

	boolean findPossibleSiblingBroadcast( Map<JClassType, List<EventElement>> broadcastMap, ChildModuleElement siblingElement )
			throws NotFoundClassException {
		boolean keep = false;
		Iterator<JClassType> it = broadcastMap.keySet().iterator();
		JClassType type;

		String siblingClass = siblingElement.getClassName();
		JClassType siblingType = getType( siblingElement, siblingClass );
		List<String> siblings;
		while ( it.hasNext() ) {
			type = it.next();
			if ( siblingType.isAssignableTo( type ) ) {
				for ( EventElement event : broadcastMap.get( type ) ) {
					siblings = event.getSiblingsToLoad();
					if ( !siblings.contains( siblingClass ) ) {
						siblings.add( siblingClass );
					}
				}
				keep = true;
			}
		}
		return keep;
	}

	boolean findPossibleParentBroadcast( Map<JClassType, List<EventElement>> broadcastMap, ChildModuleElement currentElement, String parentClass )
			throws NotFoundClassException {
		boolean keep = false;
		Iterator<JClassType> it = broadcastMap.keySet().iterator();
		JClassType type;

		JClassType siblingType = getType( currentElement, parentClass );
		String trueStr = Boolean.TRUE.toString();
		while ( it.hasNext() ) {
			type = it.next();
			if ( siblingType.isAssignableTo( type ) ) {
				for ( EventElement event : broadcastMap.get( type ) ) {
					event.setForwardToParent( trueStr );
				}
				keep = true;
			}
		}
		return keep;
	}

	/**
	 * Checks that events dispatch at start or History correspond to a configured mvp4g element.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 */
	void validateEvents() throws InvalidMvp4gConfigurationException {

		if ( isRootModule() ) {
			for ( EventElement event : events ) {
				if ( event.hasForwardToParent() ) {
					throw new InvalidMvp4gConfigurationException( String.format( NO_PARENT_ERROR, event.getType() ) );
				}
			}
		}

		String event;
		EventElement eventElt;
		String[] objClasses;

		if ( start.hasForwardEventType() ) {
			if ( isRootModule() ) {
				throw new InvalidMvp4gConfigurationException( WRONG_FORWARD_EVENT );
			}
			event = start.getForwardEventType();
			eventElt = getElement( event, events, start );
			objClasses = eventElt.getEventObjectClass();
			if ( ( objClasses != null ) && ( objClasses.length > 0 ) ) {
				throw new InvalidMvp4gConfigurationException( String.format( NOT_EMPTY_EVENT_OBJ, "Forward", "Forward", eventElt.getType() ) );
			}
		}

		if ( ( history != null ) && isRootModule() ) {
			event = history.getInitEvent();
			getElement( event, events, history );
		}

		if ( loadChildConfig != null ) {
			String eventName = loadChildConfig.getErrorEvent();
			if ( ( eventName != null ) && ( eventName.length() > 0 ) ) {
				eventElt = getElement( eventName, events, loadChildConfig );
				objClasses = eventElt.getEventObjectClass();
				if ( ( objClasses != null )
						&& ( ( objClasses.length > 1 ) || ( ( objClasses.length == 1 ) && ( !Throwable.class.getName().equals( objClasses[0] ) ) ) ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( WRONG_EVENT_OBJ, loadChildConfig.getTagName(), "Error",
							eventElt.getType(), Throwable.class.getName() ) );
				}
			}

			eventName = loadChildConfig.getAfterEvent();
			if ( ( eventName != null ) && ( eventName.length() > 0 ) ) {
				eventElt = getElement( eventName, events, loadChildConfig );
				objClasses = eventElt.getEventObjectClass();
				if ( ( objClasses != null ) && ( objClasses.length > 0 ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( NOT_EMPTY_EVENT_OBJ, loadChildConfig.getTagName(), "After",
							eventElt.getType() ) );
				}
			}

			eventName = loadChildConfig.getBeforeEvent();
			if ( ( eventName != null ) && ( eventName.length() > 0 ) ) {
				eventElt = getElement( eventName, events, loadChildConfig );
				objClasses = eventElt.getEventObjectClass();
				if ( ( objClasses != null ) && ( objClasses.length > 0 ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( NOT_EMPTY_EVENT_OBJ, loadChildConfig.getTagName(), "Before",
							eventElt.getType() ) );
				}
			}

		}

	}

	/**
	 * Validate that if history converters are used, init history event is defined
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if history init event is not defined but history converters are used
	 */
	void validateHistory() throws InvalidMvp4gConfigurationException {

		if ( historyConverters.size() > 0 ) {
			if ( !isRootModule() ) {
				if ( ( history != null )
						&& ( ( history.getInitEvent() != null ) || ( history.getNotFoundEvent() != null ) || ( history.getPlaceServiceClass() != null ) ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( HISTORY_ONLY_FOR_ROOT, module.getQualifiedSourceName() ) );
				}
				if ( ( historyName == null ) || ( historyName.length() == 0 ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( HISTORY_NAME_MISSING, module.getQualifiedSourceName() ) );
				}
				// make sure history is equal to null for the writer
				history = null;
			} else {
				if ( ( history == null ) || ( history.getInitEvent() == null ) || ( history.getInitEvent().length() == 0 ) ) {
					throw new InvalidMvp4gConfigurationException( HISTORY_INIT_MISSING );
				}
			}
		}
	}

	/**
	 * Validates that every mvp4g element has a globally unique identifier.</p>
	 * 
	 * @throws NonUniqueIdentifierExcpetion
	 *             if two or more elements have the same textual identifier.
	 */
	void checkUniquenessOfAllElements() throws NonUniqueIdentifierException {
		Set<String> allIds = new HashSet<String>();
		checkUniquenessOf( historyConverters, allIds );
		checkUniquenessOf( presenters, allIds );
		checkUniquenessOf( eventHandlers, allIds );
		checkUniquenessOf( views, allIds );
		checkUniquenessOf( events, allIds );
		checkUniquenessOf( services, allIds );
		checkUniquenessOf( childModules, allIds );
		checkUniquenessOf( eventFilters, allIds );
	}

	/**
	 * Validate start element
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             thrown if no view to load at start has been defined.
	 */
	void validateStart() throws InvalidMvp4gConfigurationException {
		String startEvent = start.getEventType();
		if ( ( startEvent != null ) && ( startEvent.length() > 0 ) ) {
			EventElement eventElt = getElement( startEvent, events, start );

			String[] objClasses = eventElt.getEventObjectClass();
			if ( ( objClasses != null ) && ( objClasses.length > 0 ) ) {
				throw new InvalidMvp4gConfigurationException( String.format( NOT_EMPTY_EVENT_OBJ, "Start", "Start", eventElt.getType() ) );
			}

		}
	}

	void validateDebug() throws NotFoundClassException, InvalidTypeException {
		if ( debug != null ) {
			JClassType debugType = getType( debug, debug.getLogger() );
			if ( !debugType.isAssignableTo( oracle.findType( Mvp4gLogger.class.getCanonicalName() ) ) ) {
				throw new InvalidTypeException( debug, "Logger", debug.getLogger(), Mvp4gLogger.class.getCanonicalName() );
			}
		}
	}

	String[] validateGinModule() throws InvalidMvp4gConfigurationException {
		String[] propertiesValues = getGinModulesByProperties();

		List<String> modulesClassName = ginModule.getModules();
		if ( ( modulesClassName == null ) || ( modulesClassName.size() == 0 ) ) {
			throw new InvalidMvp4gConfigurationException( NO_GIN_MODULE );
		}

		JClassType ginType;
		JClassType ginModuleClass = getType( ginModule, GinModule.class.getName() );
		for ( String module : modulesClassName ) {
			ginType = getType( ginModule, module );
			if ( !ginType.isAssignableTo( ginModuleClass ) ) {
				throw new InvalidTypeException( ginModule, "Logger", module, GinModule.class.getCanonicalName() );
			}
		}

		return propertiesValues;
	}

	private String[] getGinModulesByProperties() throws InvalidMvp4gConfigurationException {
		String[] properties = ginModule.getModuleProperties();
		String[] propertiesValue;
		if ( properties != null ) {
			int size = properties.length;
			propertiesValue = new String[size];
			String moduleClassName;
			List<String> modules = ginModule.getModules();
			if ( modules == null ) {
				ginModule.setModules( new String[0] );
				modules = ginModule.getModules();
			}
			String property;
			for ( int i = 0; i < size; i++ ) {
				property = properties[i];
				try {
					moduleClassName = propertyOracle.getSelectionProperty( logger, property ).getCurrentValue().replace( "$", "." );
				} catch ( BadPropertyValueException e ) {
					throw new InvalidMvp4gConfigurationException( String.format( GIN_MODULE_UNKNOWN_PROPERTY, module.getSimpleSourceName(), property,
							e.getMessage() ) );
				}
				modules.add( moduleClassName );
				propertiesValue[i] = moduleClassName;
			}
		} else {
			propertiesValue = null;
		}
		return propertiesValue;
	}

	/*
	 * ANNOTATION LOAD
	 */

	/**
	 * Pre-loads information contained in Presenter annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the Presenter annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if presenter class associated with the annotation is not correct
	 */
	void loadPresenters( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		PresenterAnnotationsLoader loader = new PresenterAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads information contained in EventHandler annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the EventHandler annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if event handler class associated with the annotation is not correct
	 */
	void loadEventHandlers( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		EventHandlerAnnotationsLoader loader = new EventHandlerAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads information contained in Service annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the Service annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if service class associated with the annotation is not correct
	 */
	void loadServices( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		ServiceAnnotationsLoader loader = new ServiceAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads all Events contained in the class of the Events annotation. Build eventBus element
	 * if needed
	 * 
	 * @param annotedClasses
	 *            classes with the Events annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if events class associated with the annotation is not correct
	 */
	void loadEvents( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		EventsAnnotationsLoader loader = new EventsAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads information contained in History annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the History annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if history converter class associated with the annotation is not correct
	 */
	void loadHistoryConverters( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		HistoryAnnotationsLoader loader = new HistoryAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	void loadParentModule() throws InvalidMvp4gConfigurationException {

		if ( !ROOT_MODULE_CLASS_NAME.equals( module.getQualifiedSourceName() ) ) {
			parentEventBus = findParentEventBus( module.getQualifiedSourceName() );
		}

		HistoryName hName = module.getAnnotation( HistoryName.class );
		if ( hName != null ) {
			if ( isRootModule() ) {
				throw new InvalidMvp4gConfigurationException( String.format( ROOT_MODULE_NO_HISTORY_NAME, module.getQualifiedSourceName() ) );
			} else {
				historyName = hName.value();
			}
		}

	}

	/**
	 * Verify that id of elements contained in the set isn't already contained in the ids set.
	 * 
	 * @param <E>
	 *            type of elements contained in the set
	 * @param subset
	 *            set of elements to test
	 * @param ids
	 *            set containing the ids
	 * 
	 * @throws NonUniqueIdentifierException
	 *             if id of an element is already contained in the ids set.
	 */
	private <E extends Mvp4gElement> void checkUniquenessOf( Set<E> subset, Set<String> ids ) throws NonUniqueIdentifierException {
		for ( E item : subset ) {
			boolean unique = ids.add( item.getUniqueIdentifier() );
			if ( !unique ) {
				throw new NonUniqueIdentifierException( item.getUniqueIdentifier() );
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
	 * @return element if found, null otherwise
	 * @throws UnknownConfigurationElementException
	 */
	private <T extends Mvp4gElement> T getElement( String elementName, Set<T> elements, Mvp4gElement relatedElement )
			throws UnknownConfigurationElementException {
		T eFound = null;
		for ( T element : elements ) {
			if ( element.getUniqueIdentifier().equals( elementName ) ) {
				eFound = element;
				break;
			}
		}

		if ( eFound == null ) {
			throw new UnknownConfigurationElementException( relatedElement, elementName );
		}

		return eFound;
	}

	/**
	 * Remove all elements of the useless set from the set
	 * 
	 * @param <T>
	 *            type of the elements to remove
	 * @param set
	 *            set where elements need to be removed
	 * @param toRemove
	 *            set of elements to remove
	 */
	private <T extends Mvp4gElement> void removeUselessElements( Set<T> set, Set<T> toRemove ) {
		for ( T e : toRemove ) {
			set.remove( e );
			logger.log( TreeLogger.DEBUG, String.format( REMOVE_OBJ, e.getTagName(), e.getUniqueIdentifier() ) );
		}
	}

	/**
	 * Retrieve a type from the oracle.
	 * 
	 * @param element
	 *            element that asks to retrieve the class (can be null)
	 * @param className
	 *            name of the class to retrieve
	 * @return type of the class
	 * 
	 * @throws NotFoundClassException
	 *             if the class can not be found
	 */
	private JClassType getType( Mvp4gElement element, String className ) throws NotFoundClassException {
		JClassType type = oracle.findType( className );
		if ( type == null ) {
			throw new NotFoundClassException( element, className );
		}
		return type;
	}

	private JClassType findParentEventBus( String moduleClassName ) {
		ChildModuleElement child = moduleParentEventBusClassMap.get( moduleClassName );
		return ( child == null ) ? null : child.getParentEventBus();
	}

	void findChildModuleHistoryName() throws InvalidMvp4gConfigurationException {
		JClassType childType;
		HistoryName hName;
		List<String> historyNames = new ArrayList<String>();
		for ( ChildModuleElement childModule : childModules ) {
			childType = getType( childModule, childModule.getClassName() );
			hName = childType.getAnnotation( HistoryName.class );
			if ( hName != null ) {
				String hNameStr = hName.value();
				validateHistoryName( hNameStr, childModule );
				if ( historyNames.contains( hNameStr ) ) {
					throw new InvalidMvp4gConfigurationException( String.format( CHILD_MODULE_SAME_HISTORY_NAME, module.getQualifiedSourceName(),
							hNameStr ) );
				}
				historyNames.add( hNameStr );

				childModule.setHistoryName( hNameStr );
			}
		}
	}

	void validateHistoryName( String historyName, Mvp4gElement element ) throws InvalidMvp4gConfigurationException {
		if ( historyName.startsWith( PlaceService.CRAWLABLE ) || historyName.contains( PlaceService.MODULE_SEPARATOR ) ) {
			throw new InvalidMvp4gConfigurationException( String.format( WRONG_HISTORY_NAME, element.getTagName(), element.getUniqueIdentifier(),
					historyName ) );
		}
	}

	boolean checkIfParentEventReturnsString( EventElement e ) {
		if ( parentEventBus != null ) {
			try {
				String eventType = e.getType();
				JClassType string = oracle.getType( java.lang.String.class.getName() );
				for ( JMethod m : parentEventBus.getMethods() ) {
					if ( eventType.equals( m.getName() ) ) {
						return m.getReturnType().equals( string );
					}
				}
			} catch ( NotFoundException e1 ) {
				// nothing to do
			}
		}
		return false;
	}
}

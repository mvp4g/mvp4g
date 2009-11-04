/**
 * 
 */
package com.mvp4g.util.config;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.XmlEventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.config.loader.annotation.EventsAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.HistoryAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.PresenterAnnotationsLoader;
import com.mvp4g.util.config.loader.annotation.ServiceAnnotationsLoader;
import com.mvp4g.util.config.loader.xml.EventsLoader;
import com.mvp4g.util.config.loader.xml.HistoryConverterLoader;
import com.mvp4g.util.config.loader.xml.HistoryLoader;
import com.mvp4g.util.config.loader.xml.PresentersLoader;
import com.mvp4g.util.config.loader.xml.ServicesLoader;
import com.mvp4g.util.config.loader.xml.StartLoader;
import com.mvp4g.util.config.loader.xml.ViewsLoader;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.exception.InvalidTypeException;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * An in-memory representation of all elements in the mvp4g-config.xml file.
 * 
 * @author javier
 * 
 */
public class Mvp4gConfiguration {

	private Set<PresenterElement> presenters = new HashSet<PresenterElement>();
	private Set<ViewElement> views = new HashSet<ViewElement>();
	private Set<EventElement> events = new HashSet<EventElement>();
	private Set<ServiceElement> services = new HashSet<ServiceElement>();
	private Set<HistoryConverterElement> historyConverters = new HashSet<HistoryConverterElement>();
	private StartElement start = null;
	private HistoryElement history = null;
	private EventBusElement eventBus = null;

	/**
	 * Loads all Mvp4g elements from an in-memory representation of the XML configuration.</p>
	 * 
	 * Configuration loading comprises two phases:
	 * 
	 * <ol>
	 * <li/>Phase 1, <i>Parsing all the XML elements</i>: during this phase, all the Mvp4gElement
	 * instances are constructed from their corresponding xml tags. Simple validation of element
	 * attributes is performed.
	 * <li/>Phase 2, <i>Validation of cross-element references</i>: in this phase element
	 * identifiers are checked for global uniqueness; event handler references and view references
	 * are checked for existence.
	 * </ol>
	 * 
	 * @param xmlConfig
	 *            raw, in-memory representation of the XML configuration.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if any tags cannot be loaded.
	 * 
	 * @throws NonUniqueIdentifierExcpetion
	 *             if two or more elements have the same textual identifier.
	 * 
	 * @throws UnknownConfigurationElementException
	 *             <ol>
	 *             <li/>if an event handler cannot be found among the configured elements.
	 *             <li/>if a view reference cannot be found among the configured elements.
	 *             </ol>
	 */
	public void load( String xmlConfigPath, Map<Class<? extends Annotation>, List<JClassType>> scanResult, TypeOracle oracle )
			throws InvalidMvp4gConfigurationException {

		File f = new File( xmlConfigPath );

		if ( f.exists() ) {

			XMLConfiguration xmlConfig;
			try {
				xmlConfig = new XMLConfiguration( "mvp4g-conf.xml" );
			} catch ( ConfigurationException e ) {
				throw new InvalidMvp4gConfigurationException( e.getMessage() );
			}

			// Phase 1: load all elements, performing attribute validation
			try {
				loadViews( xmlConfig );
				loadServices( xmlConfig );
				loadHistoryConverters( xmlConfig );
				loadPresenters( xmlConfig );
				loadEvents( xmlConfig );
				loadStart( xmlConfig );
				loadHistory( xmlConfig );
			} catch ( Mvp4gXmlException e ) {
				e.setXmlFilePath( xmlConfigPath );
				throw e;
			}

		}

		// Phase 2: load information from annotations
		loadServices( scanResult.get( Service.class ) );
		loadHistoryConverters( scanResult.get( History.class ) );
		loadPresenters( scanResult.get( Presenter.class ) );
		loadEvents( scanResult.get( Events.class ) );

		// Phase 3: perform cross-element validations
		if ( eventBus.isLookForObjectClass() ) {
			findEventObjectClass( oracle );
		}
		checkUniquenessOfAllElements();
		validateStart();
		validateEventHandlers( oracle );
		validateHistoryConverters( oracle );
		validateViews();
		validateServices();
		validateEvents();
	}

	/**
	 * Pre-loads information contained in Presenter annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the Presenter annotations
	 * @throws Mvp4gAnnotationException
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if presenter tags cannot be loaded.
	 * 
	 */
	private void loadPresenters( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		PresenterAnnotationsLoader loader = new PresenterAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads all Presenters in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if presenter tags cannot be loaded.
	 * 
	 */
	private void loadPresenters( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		PresentersLoader presentersConfig = new PresentersLoader( xmlConfig );
		presenters = presentersConfig.loadElements();
	}

	/**
	 * Pre-loads all History Converter in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * @throws Mvp4gAnnotationException
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadServices( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		ServiceAnnotationsLoader loader = new ServiceAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads all Services in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if service tags cannot be loaded.
	 * 
	 */
	private void loadServices( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		ServicesLoader servicesConfig = new ServicesLoader( xmlConfig );
		services = servicesConfig.loadElements();
	}

	/**
	 * Pre-loads all Views in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if view tags cannot be loaded.
	 */
	private void loadViews( XMLConfiguration xmlConfig ) throws Mvp4gXmlException {
		ViewsLoader viewsConfig = new ViewsLoader( xmlConfig );
		views = viewsConfig.loadElements();
	}

	/**
	 * Pre-loads all Events in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * @throws Mvp4gAnnotationException
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadEvents( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		EventsAnnotationsLoader loader = new EventsAnnotationsLoader();
		loader.load( annotedClasses, this );
		if ( eventBus == null ) {
			eventBus = new EventBusElement( EventBusWithLookup.class.getName(), XmlEventBus.class.getName(), true, true );
		}
	}

	/**
	 * Pre-loads all Events in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadEvents( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		EventsLoader eventsConfig = new EventsLoader( xmlConfig );
		events = eventsConfig.loadElements();
	}

	/**
	 * Pre-loads all History Converter in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * @throws Mvp4gAnnotationException
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadHistoryConverters( List<JClassType> annotedClasses ) throws Mvp4gAnnotationException {
		HistoryAnnotationsLoader loader = new HistoryAnnotationsLoader();
		loader.load( annotedClasses, this );
	}

	/**
	 * Pre-loads all History Converter in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if event tags cannot be loaded.
	 */
	private void loadHistoryConverters( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		HistoryConverterLoader historyConverterConfig = new HistoryConverterLoader( xmlConfig );
		historyConverters = historyConverterConfig.loadElements();
	}

	/**
	 * Pre-loads the Start element in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if start tag cannot be loaded.
	 */
	private void loadStart( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		StartLoader startConfig = new StartLoader( xmlConfig );
		start = startConfig.loadElement();
	}

	/**
	 * Pre-loads the History element in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of mvp4g-config.xml file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if start tag cannot be loaded.
	 */
	private void loadHistory( XMLConfiguration xmlConfig ) throws InvalidMvp4gConfigurationException {
		HistoryLoader historyConfig = new HistoryLoader( xmlConfig );
		history = historyConfig.loadElement();
	}

	/**
	 * Returns a set of valid Presenters loaded from the configuration file.
	 */
	public Set<PresenterElement> getPresenters() {
		return presenters;
	}

	/**
	 * Returns a set of valid Views loaded from the configuration file.
	 */
	public Set<ViewElement> getViews() {
		return views;
	}

	/**
	 * Returns a set of valid History Converters loaded from the configuration file.
	 */
	public Set<HistoryConverterElement> getHistoryConverters() {
		return historyConverters;
	}

	/**
	 * Returns a set of valid Events loaded from the configuration file.
	 */
	public Set<EventElement> getEvents() {
		return events;
	}

	/**
	 * Returns a set of valid Services loaded from the configuration file.
	 */
	public Set<ServiceElement> getServices() {
		return services;
	}

	/**
	 * Returns the Start element loaded from the configuration file.
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
	 * @param history
	 *            the history to set
	 */
	public void setHistory( HistoryElement history ) {
		this.history = history;
	}

	/**
	 * Returns the Start element loaded from the configuration file.
	 */
	public HistoryElement getHistory() {
		return history;
	}

	/**
	 * Validates that every mvp4g element has a globally unique identifier.</p>
	 * 
	 * @throws NonUniqueIdentifierException
	 * 
	 * @throws NonUniqueIdentifierExcpetion
	 *             if two or more elements have the same textual identifier.
	 */
	void checkUniquenessOfAllElements() throws NonUniqueIdentifierException {
		Set<String> allIds = new HashSet<String>();
		checkUniquenessOf( historyConverters, allIds );
		checkUniquenessOf( presenters, allIds );
		checkUniquenessOf( views, allIds );
		checkUniquenessOf( events, allIds );
		checkUniquenessOf( services, allIds );
	}

	private <E extends Mvp4gElement> void checkUniquenessOf( Set<E> subset, Set<String> ids ) throws NonUniqueIdentifierException {
		for ( E item : subset ) {
			boolean unique = ids.add( item.getUniqueIdentifier() );
			if ( !unique ) {
				throw new NonUniqueIdentifierException( item.getUniqueIdentifier() );
			}
		}
	}

	void validateStart() throws InvalidMvp4gConfigurationException {
		if ( ( start == null ) || ( start.getView() == null ) ) {
			throw new InvalidMvp4gConfigurationException( "You must define a view to load when the application starts." );
		}
	}

	/**
	 * Checks that all event handler names correspond to a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if an event handler cannot be found among the configured elements.
	 * @throws InvalidTypeException
	 */
	void validateEventHandlers( TypeOracle oracle ) throws UnknownConfigurationElementException, InvalidTypeException {

		Map<String, List<EventElement>> presenterMap = new HashMap<String, List<EventElement>>();

		//Add presenter that handles event
		List<EventElement> eventList = null;
		for ( EventElement event : events ) {
			for ( String handler : event.getHandlers() ) {
				eventList = presenterMap.get( handler );
				if ( eventList == null ) {
					eventList = new ArrayList<EventElement>();
					presenterMap.put( handler, eventList );
				}
				eventList.add( event );
			}
		}

		String startView = start.getView();
		JGenericType presenterGenType = oracle.findType( PresenterInterface.class.getName() ).isGenericType();
		JClassType eventBusType = oracle.findType( eventBus.getInterfaceClassName() );
		JType[] noParam = new JType[0];
		JClassType presenterType = null;
		JClassType eventBusParam = null;
		JClassType viewParam = null;
		String viewName = null;
		String viewClass = null;

		for ( PresenterElement presenter : presenters ) {
			eventList = presenterMap.remove( presenter.getName() );
			viewName = presenter.getView();
			if ( eventList != null || viewName.equals( startView ) ) {
				presenterType = oracle.findType( presenter.getClassName() );
				eventBusParam = (JClassType)presenterType.asParameterizationOf( presenterGenType ).findMethod( "getEventBus", noParam )
						.getReturnType();
				viewParam = (JClassType)presenterType.asParameterizationOf( presenterGenType ).findMethod( "getView", noParam ).getReturnType();

				//Control if presenter event bus is compatible with module event bus
				if ( !eventBusType.isAssignableTo( eventBusParam ) ) {
					throw new InvalidTypeException( presenter, "Event Bus", eventBusParam.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
				}

				//Control if view injected to the event bus is compatible with presenter view type
				viewClass = getViewClass( viewName );
				if ( !oracle.findType( viewClass ).isAssignableTo( viewParam ) ) {
					throw new InvalidTypeException( presenter, "View", viewClass, viewParam.getQualifiedSourceName() );
				}

			} else {
				//this object is not used, you can remove it
				presenters.remove( presenter );
			}
		}

		//Missing presenter
		if ( !presenterMap.isEmpty() ) {
			String it = presenterMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( presenterMap.get( it ).get( 0 ), it );
		}

		for ( EventElement event : events ) {
			for ( String handlerName : event.getHandlers() ) {
				if ( !elementExists( handlerName, presenters ) ) {
					throw new UnknownConfigurationElementException( event, handlerName );
				}
			}
		}
	}

	/**
	 * Checks that all injected views correspond to a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
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

		String startView = start.getView();
		for ( ViewElement view : views ) {
			viewName = view.getName();
			if ( ( viewMap.remove( viewName ) == null ) && ( !startView.equals( viewName ) ) ) {
				//this object is not used, you can remove it
				views.remove( view );
			}
		}

		//Missing view
		if ( !viewMap.isEmpty() ) {
			String it = viewMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( viewMap.get( it ).get( 0 ), it );
		}

	}
	

	/**
	 * Checks that all service names injected on every presenter and history converter correspond to
	 * a configured mvp4g element.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a service cannot be found among the configured elements.
	 */
	void validateServices() throws UnknownConfigurationElementException {

		Map<String, List<Mvp4gWithServicesElement>> serviceMap = new HashMap<String, List<Mvp4gWithServicesElement>>();

		//Add presenter that handles event
		List<Mvp4gWithServicesElement> elementList = null;
		for ( PresenterElement presenter : presenters ) {
			for ( InjectedElement service : presenter.getInjectedServices() ) {
				elementList = serviceMap.get( service.getElementName() );
				if ( elementList == null ) {
					elementList = new ArrayList<Mvp4gWithServicesElement>();
					serviceMap.put( service.getElementName(), elementList );
				}
				elementList.add( presenter );
			}
		}
		for ( HistoryConverterElement hc : historyConverters ) {
			for ( InjectedElement service : hc.getInjectedServices() ) {
				elementList = serviceMap.get( service.getElementName() );
				if ( elementList == null ) {
					elementList = new ArrayList<Mvp4gWithServicesElement>();
					serviceMap.put( service.getElementName(), elementList );
				}
				elementList.add( hc );
			}
		}

		for ( ServiceElement service : services ) {
			if ( serviceMap.remove( service.getName() ) == null ) {
				//this object is not used, you can remove it
				services.remove( service );
			}
		}

		//Missing service
		if ( !serviceMap.isEmpty() ) {
			String it = serviceMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( serviceMap.get( it ).get( 0 ), it );
		}

	}

	void validateHistoryConverters( TypeOracle oracle ) throws InvalidMvp4gConfigurationException {

		Map<String, List<EventElement>> historyConverterMap = new HashMap<String, List<EventElement>>();

		List<EventElement> eventList = null;
		String hcName = null;
		for ( EventElement event : events ) {
			if ( event.hasHistory() ) {
				hcName = event.getHistory();
				eventList = historyConverterMap.get( hcName );
				if ( eventList == null ) {
					eventList = new ArrayList<EventElement>();
					historyConverterMap.put( hcName, eventList );
				}
				eventList.add( event );
			}
		}

		JGenericType hcGenType = oracle.findType( HistoryConverter.class.getName() ).isGenericType();
		JClassType eventBusType = oracle.findType( eventBus.getInterfaceClassName() );
		JClassType hcType = null;
		JClassType eventBusParam = null;
		JClassType objectClassParam = null;
		String objectClass = null;
		JMethod[] methods = null;

		for ( HistoryConverterElement history : historyConverters ) {
			eventList = historyConverterMap.remove( history.getName() );
			if ( eventList != null ) {
				hcType = oracle.findType( history.getClassName() );
				methods = hcType.asParameterizationOf( hcGenType ).getMethods();

				//Retrieve classes of History Converter event bus & form
				if ( "convertFromToken".equals( methods[0].getName() ) ) {
					eventBusParam = (JClassType)methods[0].findParameter( "eventBus" ).getType();
					objectClassParam = (JClassType)methods[1].findParameter( "form" ).getType();
				} else {
					eventBusParam = (JClassType)methods[1].findParameter( "eventBus" ).getType();
					objectClassParam = (JClassType)methods[0].findParameter( "form" ).getType();
				}

				//Control if history converter event bus is compatible with module event bus
				if ( !eventBusType.isAssignableTo( eventBusParam ) ) {
					throw new InvalidTypeException( history, "Event Bus", eventBusParam.getQualifiedSourceName(), eventBus.getInterfaceClassName() );
				}

				//Control if event object class is compatible with History Converter object class
				for ( EventElement event : eventList ) {
					objectClass = event.getEventObjectClass();
					if ( ( objectClass != null ) && ( objectClass.length() > 0 ) ) {
						if ( !oracle.findType( objectClass ).isAssignableTo( objectClassParam ) ) {
							throw new InvalidTypeException( event, "History Converter", eventBusParam.getQualifiedSourceName(), eventBus
									.getInterfaceClassName() );
						}
					}
				}
			} else {
				//this object is not used, you can remove it
				historyConverters.remove( history );
			}
		}

		//Missing history converter
		if ( !historyConverterMap.isEmpty() ) {
			String it = historyConverterMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException( historyConverterMap.get( it ).get( 0 ), it );
		}

	}

	private <T extends Mvp4gElement> boolean elementExists( String elementName, Set<T> elements ) {
		for ( Mvp4gElement element : elements ) {
			if ( element.getUniqueIdentifier().equals( elementName ) ) {
				return true;
			}
		}
		return false;
	}

	void validateEvents() throws UnknownConfigurationElementException {
		String event = null;
		if ( start.hasEventType() ) {
			event = start.getEventType();
			if ( !elementExists( event, events ) ) {
				throw new UnknownConfigurationElementException( start, event );
			}
		}

		if ( history != null ) {
			event = history.getInitEvent();
			if ( !elementExists( event, events ) ) {
				throw new UnknownConfigurationElementException( history, event );
			}
		}

	}

	void findEventObjectClass( TypeOracle typeOracle ) throws InvalidMvp4gConfigurationException {

		for ( EventElement event : events ) {
			String objectClass = event.getEventObjectClass();
			if ( ( objectClass == null ) || ( objectClass.length() == 0 ) ) {
				String[] handlers = event.getHandlers();

				//if no handler, then event object class can't be deduce 
				if ( handlers.length != 0 ) {
					String presenterClass = getPresenterClass( handlers[0] );

					try {
						JClassType handlerClass = typeOracle.getType( presenterClass );
						String eventMethod = event.getCalledMethod();
						JParameter[] parameters = null;
						boolean found = false;
						int parameterSize = 0;
						for ( JMethod method : handlerClass.getMethods() ) {
							if ( eventMethod.equals( method.getName() ) ) {
								parameters = method.getParameters();
								parameterSize = parameters.length;
								if ( parameterSize == 0 ) {
									found = true;
									break;
								} else if ( parameterSize == 1 ) {
									found = true;
									try {
										event.setEventObjectClass( parameters[0].getType().getQualifiedSourceName() );
									} catch ( DuplicatePropertyNameException e ) {
										//setter is only called once, so this error can't occur.
									}
									break;
								}
							}
						}
						if ( !found ) {
							throw new InvalidMvp4gConfigurationException( "Event " + event.getType() + ": handler " + handlers[0]
									+ " doesn't define a method " + event.getCalledMethod() + " with 1 or 0 parameter." );
						}

					} catch ( NotFoundException e ) {
						throw new InvalidMvp4gConfigurationException( "Event " + event.getType() + ": handler " + handlers[0] + ": class "
								+ presenterClass + " not found" );
					}
				}
			}

		}

	}

	private String getPresenterClass( String presenterName ) {
		String presenterClass = null;
		for ( PresenterElement presenter : presenters ) {
			if ( presenterName.equals( presenter.getName() ) ) {
				presenterClass = presenter.getClassName();
			}
		}

		return presenterClass;
	}

	/**
	 * @return the eventBus
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

	private String getViewClass( String viewName ) {
		String viewClass = null;
		for ( ViewElement view : views ) {
			if ( view.getName().equals( viewName ) ) {
				viewClass = view.getClassName();
			}
		}
		return viewClass;
	}

}

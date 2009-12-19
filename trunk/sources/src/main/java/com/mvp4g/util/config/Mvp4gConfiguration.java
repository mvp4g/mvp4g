/**
 * 
 */
package com.mvp4g.util.config;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.History;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.ChildModulesElement;
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
import com.mvp4g.util.config.loader.xml.ChildModuleLoader;
import com.mvp4g.util.config.loader.xml.ChildModulesLoader;
import com.mvp4g.util.config.loader.xml.EventsLoader;
import com.mvp4g.util.config.loader.xml.HistoryConverterLoader;
import com.mvp4g.util.config.loader.xml.HistoryLoader;
import com.mvp4g.util.config.loader.xml.PresentersLoader;
import com.mvp4g.util.config.loader.xml.ServicesLoader;
import com.mvp4g.util.config.loader.xml.StartLoader;
import com.mvp4g.util.config.loader.xml.ViewsLoader;
import com.mvp4g.util.exception.InvalidClassException;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;
import com.mvp4g.util.exception.InvalidTypeException;
import com.mvp4g.util.exception.NonUniqueIdentifierException;
import com.mvp4g.util.exception.NotFoundClassException;
import com.mvp4g.util.exception.UnknownConfigurationElementException;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * An in-memory representation of all elements in the configuration file and in
 * the annotations.
 * 
 * @author javier
 * 
 */
public class Mvp4gConfiguration {

	private static final String HC_WARNING = "History Converter %s: must be able to translate null objects since it is associated to event %s which is sent with no object.";
	private static final String REMOVE_OBJ = "%s %s: No instance of this object has been created since this class is not used.";
	private static final String MISSING_ATTRIBUTE = "%s: child module %s doesn't define any event to load its view.";
	private static final String NOT_EMPTY_EVENT_OBJ = "%s: %s event %s can't have any object associated with it.";
	private static final String WRONG_EVENT_OBJ = "%s: %s event %s can only be associated with %s";
	private static final String EMPTY_EVENT_OBJ = "Event %s: event must have an object associated with it as it loads a child view.";
	private static final String WRONG_CHILD_LOAD_EVENT_OBJ = "Child Module %s: event %s can not load child module's start view. Can not convert %s to %s.";
	private static final String START_VIEW_XML_WARNING = "Child Module %s: could not verify if child module's start view can be loaded by event %s since child module uses a XML event bus.";

	private Set<PresenterElement> presenters = new HashSet<PresenterElement>();
	private Set<ViewElement> views = new HashSet<ViewElement>();
	private Set<EventElement> events = new HashSet<EventElement>();
	private Set<ServiceElement> services = new HashSet<ServiceElement>();
	private Set<HistoryConverterElement> historyConverters = new HashSet<HistoryConverterElement>();
	private Set<ChildModuleElement> childModules = new HashSet<ChildModuleElement>();
	private StartElement start = null;
	private HistoryElement history = null;
	private EventBusElement eventBus = null;
	private JClassType module = null;
	private ChildModulesElement loadChildConfig = null;
	private Map<String, JClassType> childEventBusClassMap = new HashMap<String, JClassType>();

	private TreeLogger logger = null;
	private TypeOracle oracle = null;

	/**
	 * Contruct a Mvp4gConfiguration object
	 * 
	 * @param logger
	 *            logger of the GWT compiler
	 * @param oracle
	 *            oracle of the GWT compiler
	 */
	public Mvp4gConfiguration(TreeLogger logger, TypeOracle oracle) {
		this.logger = logger;
		this.oracle = oracle;
	}

	/**
	 * Loads all Mvp4g elements from an in-memory representation of the XML
	 * configuration and/or annotations.</p>
	 * 
	 * Configuration loading comprises up to three phases:
	 * 
	 * <ol>
	 * <li/>Phase 1, <i>Parsing all the XML elements (if there is an XML
	 * configuration file)</i>: during this phase, all the Mvp4gElement
	 * instances are constructed from their corresponding xml tags. Simple
	 * validation of element attributes is performed.
	 * <li/>Phase 2, <i>Parsing all the annotations</i>: during this phase, all
	 * the Mvp4gElement instances are constructed from their corresponding
	 * annotations. All possible validations are performed.
	 * <li/>Phase 3, <i>Validation of cross-element references and removal of
	 * useless elements</i>: in this phase element identifiers are checked for
	 * global uniqueness; event handler references and view references are
	 * checked for existence. Validity elements class is also checked.
	 * </ol>
	 * 
	 * 
	 * @param xmlConfigPath
	 *            path of the XML configuration file to load
	 * @param scanResult
	 *            Map of classes associated with an annotation
	 * @throws InvalidMvp4gConfigurationException
	 *             this exception is thrown where a configuration error occurs.
	 * 
	 */
	public void load(JClassType module,
			Map<Class<? extends Annotation>, List<JClassType>> scanResult)
			throws InvalidMvp4gConfigurationException {

		this.module = module;
		XmlFilePath xmlFilePath = module.getAnnotation(XmlFilePath.class);

		if (xmlFilePath != null) {
			InputStream input = getClass().getClassLoader()
					.getResourceAsStream(xmlFilePath.value());

			if (input != null) {

				XMLConfiguration xmlConfig = new XMLConfiguration();
				try {
					xmlConfig.load(input);
				} catch (ConfigurationException e) {
					throw new InvalidMvp4gConfigurationException(e.getMessage());
				}

				// Phase 1: load all elements, performing attribute validation
				try {
					loadViews(xmlConfig);
					loadServices(xmlConfig);
					loadHistoryConverters(xmlConfig);
					loadPresenters(xmlConfig);
					loadEvents(xmlConfig);
					loadStart(xmlConfig);
					loadHistory(xmlConfig);
					loadChildConfig(xmlConfig);
					loadChildModules(xmlConfig);
				} catch (Mvp4gXmlException e) {
					e.setXmlFilePath(xmlFilePath.value());
					throw e;
				}

			}
		}

		// Phase 2: load information from annotations
		loadServices(scanResult.get(Service.class));
		loadHistoryConverters(scanResult.get(History.class));
		loadPresenters(scanResult.get(Presenter.class));
		loadEvents(scanResult.get(Events.class));

		// Phase 3: perform cross-element validations
		if (eventBus == null) {
			String err = "No XML configuration or Event Bus interface has been found for "
					+ module.getSimpleSourceName()
					+ " module. You need to define at least one of them.";
			throw new InvalidMvp4gConfigurationException(err);
		}
		if (eventBus.isXml()) {
			findEventObjectClass();
		}
		checkUniquenessOfAllElements();
		validateStart();
		validateEventHandlers();
		validateHistoryConverters();
		validateViews();
		validateServices();
		validateEvents();
		validateHistory();
		validateChildModules();
	}

	public boolean isAsyncEnabled() {
		return (oracle.findType("com.google.gwt.core.client.RunAsyncCallback") != null);
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
	public void setStart(StartElement start) {
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
	public void setHistory(HistoryElement history) {
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
	public void setEventBus(EventBusElement eventBus) {
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
	 * @return the childEventBusClassMap
	 */
	public Map<String, JClassType> getChildEventBusClassMap() {
		return childEventBusClassMap;
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
	public void setLoadChildConfig(ChildModulesElement loadChildConfig) {
		this.loadChildConfig = loadChildConfig;
	}

	/*
	 * Validation
	 */

	/**
	 * Checks that all injected views correspond to a configured mvp4g element.
	 * Remove views that aren't injected into a presenter or loaded at
	 * start.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a view reference cannot be found among the configured
	 *             elements.
	 */
	void validateViews() throws UnknownConfigurationElementException {

		Map<String, List<PresenterElement>> viewMap = new HashMap<String, List<PresenterElement>>();

		List<PresenterElement> presenterList = null;
		String viewName = null;
		for (PresenterElement presenter : presenters) {
			viewName = presenter.getView();
			presenterList = viewMap.get(viewName);
			if (presenterList == null) {
				presenterList = new ArrayList<PresenterElement>();
				viewMap.put(viewName, presenterList);
			}
			presenterList.add(presenter);
		}

		Set<ViewElement> toRemove = new HashSet<ViewElement>();
		String startView = start.getView();
		for (ViewElement view : views) {
			viewName = view.getName();
			if ((viewMap.remove(viewName) == null)
					&& (!startView.equals(viewName))) {
				// this object is not used, you can remove it
				toRemove.add(view);
			}
		}

		// Missing view
		if (!viewMap.isEmpty()) {
			String it = viewMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException(viewMap.get(it).get(
					0), it);
		}

		removeUselessElements(views, toRemove);

	}

	/**
	 * Checks that all service names injected on every presenter and history
	 * converter correspond to a configured mvp4g element. Remove all services
	 * that aren't injected into a presenter or an history converter.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a service cannot be found among the configured elements.
	 */
	void validateServices() throws UnknownConfigurationElementException {

		Map<String, List<Mvp4gWithServicesElement>> serviceMap = new HashMap<String, List<Mvp4gWithServicesElement>>();

		// Add presenter that handles event
		List<Mvp4gWithServicesElement> elementList = null;
		for (PresenterElement presenter : presenters) {
			for (InjectedElement service : presenter.getInjectedServices()) {
				elementList = serviceMap.get(service.getElementName());
				if (elementList == null) {
					elementList = new ArrayList<Mvp4gWithServicesElement>();
					serviceMap.put(service.getElementName(), elementList);
				}
				elementList.add(presenter);
			}
		}
		for (HistoryConverterElement hc : historyConverters) {
			for (InjectedElement service : hc.getInjectedServices()) {
				elementList = serviceMap.get(service.getElementName());
				if (elementList == null) {
					elementList = new ArrayList<Mvp4gWithServicesElement>();
					serviceMap.put(service.getElementName(), elementList);
				}
				elementList.add(hc);
			}
		}
		Set<ServiceElement> toRemove = new HashSet<ServiceElement>();
		for (ServiceElement service : services) {
			if (serviceMap.remove(service.getName()) == null) {
				// this object is not used, you can remove it
				toRemove.add(service);
			}
		}

		// Missing service
		if (!serviceMap.isEmpty()) {
			String it = serviceMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException(serviceMap.get(it)
					.get(0), it);
		}

		removeUselessElements(services, toRemove);

	}

	/**
	 * Checks that all history converter names associated to each event is a
	 * configured mvp4g element. Verify that these elements are valid. Remove
	 * all history converters that aren't used by an event.
	 * 
	 * 
	 * @throws InvalidClassException
	 *             if an history converter don't implement History Converter
	 * 
	 * @throws InvalidTypeException
	 *             if the class of event bus and object used by history
	 *             converter to convert is not compatible with the event bus of
	 *             the module or the object class linked with the event.
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if a history converter cannot be found among the configured
	 *             elements.
	 * 
	 * @throws NotFoundClassException
	 *             if a class can not be found
	 */
	void validateHistoryConverters() throws InvalidClassException,
			InvalidTypeException, UnknownConfigurationElementException,
			NotFoundClassException {

		Map<String, List<EventElement>> historyConverterMap = new HashMap<String, List<EventElement>>();

		List<EventElement> eventList = null;
		String hcName = null;
		for (EventElement event : events) {
			if (event.hasHistory()) {
				hcName = event.getHistory();
				eventList = historyConverterMap.get(hcName);
				if (eventList == null) {
					eventList = new ArrayList<EventElement>();
					historyConverterMap.put(hcName, eventList);
				}
				eventList.add(event);
			}
		}

		JGenericType hcGenType = getType(null,
				HistoryConverter.class.getCanonicalName()).isGenericType();
		JClassType eventBusType = getType(null, eventBus
				.getInterfaceClassName());
		JClassType hcType = null;
		JClassType eventBusParam = null;
		JClassType objectClassParam = null;
		String objectClass = null;
		JMethod[] methods = null;
		JParameterizedType genHC = null;

		Set<HistoryConverterElement> toRemove = new HashSet<HistoryConverterElement>();
		for (HistoryConverterElement history : historyConverters) {
			eventList = historyConverterMap.remove(history.getName());
			if (eventList != null) {

				hcType = getType(history, history.getClassName());

				genHC = hcType.asParameterizationOf(hcGenType);
				if (genHC == null) {
					throw new InvalidClassException(history,
							HistoryConverter.class.getCanonicalName());
				}

				methods = genHC.getMethods();

				// Retrieve classes of History Converter event bus & form
				if ("convertFromToken".equals(methods[0].getName())) {
					eventBusParam = (JClassType) methods[0].getParameters()[2]
							.getType();
					objectClassParam = (JClassType) methods[1].getParameters()[1]
							.getType();
				} else {
					eventBusParam = (JClassType) methods[1].getParameters()[2]
							.getType();
					objectClassParam = (JClassType) methods[0].getParameters()[1]
							.getType();
				}

				// Control if history converter event bus is compatible with
				// module event bus
				if (!eventBusType.isAssignableTo(eventBusParam)) {
					throw new InvalidTypeException(history, "Event Bus",
							eventBusParam.getQualifiedSourceName(), eventBus
									.getInterfaceClassName());
				}

				// Control if event object class is compatible with History
				// Converter object class
				for (EventElement event : eventList) {
					objectClass = event.getEventObjectClass();
					if ((objectClass != null) && (objectClass.length() > 0)) {
						if (!getType(event, objectClass).isAssignableTo(
								objectClassParam)) {
							throw new InvalidTypeException(event,
									"History Converter", objectClassParam
											.getQualifiedSourceName(),
									objectClass);
						}
					} else {
						logger.log(TreeLogger.WARN, String.format(HC_WARNING,
								history.getUniqueIdentifier(), event
										.getUniqueIdentifier()));
					}
				}
			} else {
				// this object is not used, you can remove it
				toRemove.add(history);
			}
		}

		// Missing history converter
		if (!historyConverterMap.isEmpty()) {
			String it = historyConverterMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException(historyConverterMap
					.get(it).get(0), it);
		}

		removeUselessElements(historyConverters, toRemove);

	}

	/**
	 * Checks that all event handler names correspond to a configured mvp4g
	 * element. Verify that these elements are valid. Remove the ones that don't
	 * handle events or aren't associated with the start view.</p>
	 * 
	 * @throws UnknownConfigurationElementException
	 *             if an event handler cannot be found among the configured
	 *             elements.
	 * 
	 * @throws InvalidTypeException
	 *             if the class of event bus or view injected in the presenter
	 *             is not compatible with the event bus/view managed by the
	 *             presenter.
	 * 
	 * @throws InvalidClassException
	 *             if presenter element class doesn't implement
	 *             PresenterInterface.
	 * 
	 * @throws NotFoundClassException
	 *             if a class can not be found
	 */
	void validateEventHandlers() throws UnknownConfigurationElementException,
			InvalidTypeException, InvalidClassException, NotFoundClassException {

		Map<String, List<EventElement>> presenterMap = new HashMap<String, List<EventElement>>();

		// Add presenter that handles event
		List<EventElement> eventList = null;
		for (EventElement event : events) {
			for (String handler : event.getHandlers()) {
				eventList = presenterMap.get(handler);
				if (eventList == null) {
					eventList = new ArrayList<EventElement>();
					presenterMap.put(handler, eventList);
				}
				eventList.add(event);
			}
		}

		String startView = start.getView();
		JGenericType presenterGenType = getType(null,
				PresenterInterface.class.getCanonicalName()).isGenericType();
		JClassType eventBusType = getType(null, eventBus
				.getInterfaceClassName());
		JType[] noParam = new JType[0];
		JClassType presenterType = null;
		JClassType eventBusParam = null;
		JClassType viewParam = null;
		String viewName = null;
		ViewElement view = null;
		JParameterizedType genPresenter = null;

		Set<PresenterElement> toRemove = new HashSet<PresenterElement>();

		for (PresenterElement presenter : presenters) {
			eventList = presenterMap.remove(presenter.getName());
			viewName = presenter.getView();
			if (eventList != null || viewName.equals(startView)) {
				presenterType = getType(presenter, presenter.getClassName());
				genPresenter = presenterType
						.asParameterizationOf(presenterGenType);
				if (genPresenter == null) {
					throw new InvalidClassException(presenter,
							PresenterInterface.class.getCanonicalName());
				}

				eventBusParam = (JClassType) genPresenter.findMethod(
						"getEventBus", noParam).getReturnType();
				viewParam = (JClassType) genPresenter.findMethod("getView",
						noParam).getReturnType();

				// Control if presenter event bus is compatible with module
				// event bus
				if (!eventBusType.isAssignableTo(eventBusParam)) {
					throw new InvalidTypeException(presenter, "Event Bus",
							eventBus.getInterfaceClassName(), eventBusParam
									.getQualifiedSourceName());
				}

				// Control if view injected to the event bus is compatible with
				// presenter view type
				view = getElement(viewName, views, presenter);

				if (!getType(view, view.getClassName()).isAssignableTo(
						viewParam)) {
					throw new InvalidTypeException(presenter, "View", view
							.getClassName(), viewParam.getQualifiedSourceName());
				}

			} else {
				// this object is not used, you can remove it
				toRemove.add(presenter);
			}
		}

		// Missing presenter
		if (!presenterMap.isEmpty()) {
			String it = presenterMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException(presenterMap.get(it)
					.get(0), it);
		}

		removeUselessElements(presenters, toRemove);

	}

	void validateChildModules() throws InvalidMvp4gConfigurationException {

		Map<String, List<EventElement>> childModuleMap = new HashMap<String, List<EventElement>>();

		// Add presenter that handles event
		List<EventElement> eventList = null;
		for (EventElement event : events) {
			for (String childModule : event.getModulesToLoad()) {
				eventList = childModuleMap.get(childModule);
				if (eventList == null) {
					eventList = new ArrayList<EventElement>();
					childModuleMap.put(childModule, eventList);
				}
				eventList.add(event);
			}
		}

		JClassType moduleSuperClass = getType(null, Mvp4gModule.class
				.getCanonicalName());
		JClassType moduleType = null;

		Set<ChildModuleElement> toRemove = new HashSet<ChildModuleElement>();
		String eventName = null;
		EventElement eventElt = null;
		String eventObjClass = null;
		String childModuleClass = null;
		JClassType childEventBus = null;
		String startViewClass = null;
		for (ChildModuleElement childModule : childModules) {
			eventList = childModuleMap.remove(childModule.getName());
			if (eventList != null) {
				childModuleClass = childModule.getClassName();
				moduleType = getType(childModule, childModuleClass);
				if (!moduleType.isAssignableTo(moduleSuperClass)) {
					throw new InvalidClassException(childModule,
							Mvp4gModule.class.getCanonicalName());
				}
				eventName = childModule.getEventToLoadView();
				if ((eventName == null) || (eventName.length() == 0)) {
					String error = String.format(MISSING_ATTRIBUTE, module
							.getQualifiedSourceName(), childModule
							.getClassName());
					throw new InvalidMvp4gConfigurationException(error);
				}
				// verify event exists
				eventElt = getElement(eventName, events, childModule);
				eventObjClass = eventElt.getEventObjectClass();
				if ((eventObjClass == null) || (eventObjClass.length() == 0)) {
					throw new InvalidMvp4gConfigurationException(String.format(
							EMPTY_EVENT_OBJ, eventElt.getType()));
				}
				childEventBus = childEventBusClassMap.get(childModuleClass);
				if (childEventBus != null) {
					startViewClass = childEventBus.getAnnotation(Events.class)
							.startView().getCanonicalName();
					if (!getType(childModule, startViewClass).isAssignableTo(
							getType(eventElt, eventElt.getEventObjectClass()))) {
						throw new InvalidMvp4gConfigurationException(String
								.format(WRONG_CHILD_LOAD_EVENT_OBJ, childModule
										.getClassName(), eventElt.getType(),
										startViewClass, eventElt
												.getEventObjectClass()));
					}
				} else {
					logger.log(TreeLogger.WARN, String.format(
							START_VIEW_XML_WARNING, childModule.getClassName(),
							eventElt.getType()));
				}
			} else {
				// this object is not used, you can remove it
				toRemove.add(childModule);
			}
		}

		// Missing presenter
		if (!childModuleMap.isEmpty()) {
			String it = childModuleMap.keySet().iterator().next();
			throw new UnknownConfigurationElementException(childModuleMap.get(
					it).get(0), it);
		}

		removeUselessElements(childModules, toRemove);

	}

	/**
	 * Checks that events dispatch at start or History correspond to a
	 * configured mvp4g element.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 */
	void validateEvents() throws InvalidMvp4gConfigurationException {
		String event = null;
		if (start.hasEventType()) {
			event = start.getEventType();
			getElement(event, events, start);
		}

		if (history != null) {
			event = history.getInitEvent();
			getElement(event, events, history);
		}

		if (loadChildConfig != null) {
			EventElement eventElt = null;
			String objClass = null;
			String eventName = loadChildConfig.getErrorEvent();
			if ((eventName != null) && (eventName.length() > 0)) {
				eventElt = getElement(eventName, events, loadChildConfig);
				objClass = eventElt.getEventObjectClass();
				if ((objClass != null)
						&& (!Throwable.class.getName().equals(objClass))) {
					throw new InvalidMvp4gConfigurationException(String.format(
							WRONG_EVENT_OBJ, loadChildConfig.getTagName(),
							"Error", eventElt.getType(), Throwable.class
									.getName()));
				}
			}

			eventName = loadChildConfig.getAfterEvent();
			if ((eventName != null) && (eventName.length() > 0)) {
				eventElt = getElement(eventName, events, loadChildConfig);
				objClass = eventElt.getEventObjectClass();
				if ((objClass != null) && (objClass.length() > 0)) {
					throw new InvalidMvp4gConfigurationException(String.format(
							NOT_EMPTY_EVENT_OBJ, loadChildConfig.getTagName(),
							"After", eventElt.getType()));
				}
			}

			eventName = loadChildConfig.getBeforeEvent();
			if ((eventName != null) && (eventName.length() > 0)) {
				eventElt = getElement(eventName, events, loadChildConfig);
				objClass = eventElt.getEventObjectClass();
				if ((objClass != null) && (objClass.length() > 0)) {
					throw new InvalidMvp4gConfigurationException(String.format(
							NOT_EMPTY_EVENT_OBJ, loadChildConfig.getTagName(),
							"Before", eventElt.getType()));
				}
			}

		}

	}

	/**
	 * Validate that if history converters are used, init history event is
	 * defined
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if history init event is not defined but history converters
	 *             are used
	 */
	void validateHistory() throws InvalidMvp4gConfigurationException {
		if (historyConverters.size() > 0) {
			if ((history == null) || (history.getInitEvent() == null)
					|| (history.getInitEvent().length() == 0)) {
				throw new InvalidMvp4gConfigurationException(
						"You must define a History init event if you use history converters.");
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
		checkUniquenessOf(historyConverters, allIds);
		checkUniquenessOf(presenters, allIds);
		checkUniquenessOf(views, allIds);
		checkUniquenessOf(events, allIds);
		checkUniquenessOf(services, allIds);
	}

	/**
	 * Validate start element
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             thrown if no view to load at start has been defined.
	 */
	void validateStart() throws InvalidMvp4gConfigurationException {
		if ((start == null) || (start.getView() == null)
				|| (start.getView().length() == 0)) {
			throw new InvalidMvp4gConfigurationException(
					"You must define a view to load when the application starts.");
		}
	}

	/**
	 * For each event, deduce the object class associated with the event if
	 * needed thanks to presenter definition.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if handler doesn't define the method associated with the
	 *             event if class of the handler can not be found
	 */
	void findEventObjectClass() throws InvalidMvp4gConfigurationException {

		for (EventElement event : events) {
			String objectClass = event.getEventObjectClass();
			if ((objectClass == null) || (objectClass.length() == 0)) {
				String[] handlers = event.getHandlers();

				// if no handler, then event object class can't be deduce
				if (handlers.length != 0) {
					String presenterClass = getElement(handlers[0], presenters,
							event).getClassName();

					JClassType handlerClass = getType(event, presenterClass);
					String eventMethod = event.getCalledMethod();
					JParameter[] parameters = null;
					boolean found = false;
					int parameterSize = 0;
					for (JMethod method : handlerClass.getMethods()) {
						if (eventMethod.equals(method.getName())) {
							parameters = method.getParameters();
							parameterSize = parameters.length;
							if (parameterSize == 0) {
								found = true;
								break;
							} else if (parameterSize == 1) {
								found = true;
								try {
									event
											.setEventObjectClass(parameters[0]
													.getType()
													.getQualifiedSourceName());
								} catch (DuplicatePropertyNameException e) {
									// setter is only called once, so this error
									// can't occur.
								}
								break;
							}
						}
					}
					if (!found) {
						throw new InvalidMvp4gConfigurationException("Event "
								+ event.getType() + ": handler " + handlers[0]
								+ " doesn't define a method "
								+ event.getCalledMethod()
								+ " with 1 or 0 parameter.");
					}

				}
			}

		}

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
	 *             thrown if presenter class associated with the annotation is
	 *             not correct
	 */
	void loadPresenters(List<JClassType> annotedClasses)
			throws Mvp4gAnnotationException {
		PresenterAnnotationsLoader loader = new PresenterAnnotationsLoader();
		loader.load(annotedClasses, this);
	}

	/**
	 * Pre-loads information contained in Service annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the Service annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if service class associated with the annotation is not
	 *             correct
	 */
	void loadServices(List<JClassType> annotedClasses)
			throws Mvp4gAnnotationException {
		ServiceAnnotationsLoader loader = new ServiceAnnotationsLoader();
		loader.load(annotedClasses, this);
	}

	/**
	 * Pre-loads all Events contained in the class of the Events annotation.
	 * Build eventBus element if needed
	 * 
	 * @param annotedClasses
	 *            classes with the Events annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if events class associated with the annotation is not
	 *             correct
	 */
	void loadEvents(List<JClassType> annotedClasses)
			throws Mvp4gAnnotationException {
		EventsAnnotationsLoader loader = new EventsAnnotationsLoader();
		loader.load(annotedClasses, this);
	}

	/**
	 * Pre-loads information contained in History annotations.
	 * 
	 * @param annotedClasses
	 *            classes with the History annotation
	 * @throws Mvp4gAnnotationException
	 *             thrown if history converter class associated with the
	 *             annotation is not correct
	 */
	void loadHistoryConverters(List<JClassType> annotedClasses)
			throws Mvp4gAnnotationException {
		HistoryAnnotationsLoader loader = new HistoryAnnotationsLoader();
		loader.load(annotedClasses, this);
	}

	/*
	 * LOAD XML CONFIGURATION
	 */

	/**
	 * Pre-loads all Presenters in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of the configuration file.
	 * 
	 * @throws Mvp4gXmlException
	 *             if presenter tags cannot be loaded.
	 * 
	 */
	void loadPresenters(XMLConfiguration xmlConfig) throws Mvp4gXmlException {
		PresentersLoader presentersConfig = new PresentersLoader(xmlConfig);
		presenters = presentersConfig.loadElements();
	}

	/**
	 * Pre-loads all Services in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of the configuration file.
	 * 
	 * @throws Mvp4gXmlException
	 *             if service tags cannot be loaded.
	 * 
	 */
	void loadServices(XMLConfiguration xmlConfig) throws Mvp4gXmlException {
		ServicesLoader servicesConfig = new ServicesLoader(xmlConfig);
		services = servicesConfig.loadElements();
	}

	/**
	 * Pre-loads all Views in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of the configuration file.
	 * 
	 * @throws Mvp4gXmlException
	 *             if view tags cannot be loaded.
	 */
	void loadViews(XMLConfiguration xmlConfig) throws Mvp4gXmlException {
		ViewsLoader viewsConfig = new ViewsLoader(xmlConfig);
		views = viewsConfig.loadElements();
	}

	/**
	 * Pre-loads all Events in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of the configuration file.
	 * 
	 * @throws Mvp4gXmlException
	 *             if event tags cannot be loaded.
	 */
	void loadEvents(XMLConfiguration xmlConfig) throws Mvp4gXmlException {
		EventsLoader eventsConfig = new EventsLoader(xmlConfig);
		events = eventsConfig.loadElements();
		if (events.size() > 0) {
			eventBus = new EventBusElement(EventBusWithLookup.class
					.getCanonicalName(), BaseEventBusWithLookUp.class
					.getCanonicalName(), true);
		}
	}

	/**
	 * Pre-loads all History Converter in the configuration file.
	 * 
	 * @param xmlConfig
	 *            raw representation of the configuration file.
	 * 
	 * @throws Mvp4gXmlException
	 *             if event tags cannot be loaded.
	 */
	void loadHistoryConverters(XMLConfiguration xmlConfig)
			throws Mvp4gXmlException {
		HistoryConverterLoader historyConverterConfig = new HistoryConverterLoader(
				xmlConfig);
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
	void loadStart(XMLConfiguration xmlConfig)
			throws InvalidMvp4gConfigurationException {
		StartLoader startConfig = new StartLoader(xmlConfig);
		start = startConfig.loadElement();
	}

	void loadChildConfig(XMLConfiguration xmlConfig)
			throws InvalidMvp4gConfigurationException {
		ChildModulesLoader childConfigLoader = new ChildModulesLoader(xmlConfig);
		loadChildConfig = childConfigLoader.loadElement();
	}

	void loadChildModules(XMLConfiguration xmlConfig) throws Mvp4gXmlException {
		ChildModuleLoader loader = new ChildModuleLoader(xmlConfig);
		childModules = loader.loadElements();
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
	void loadHistory(XMLConfiguration xmlConfig)
			throws InvalidMvp4gConfigurationException {
		HistoryLoader historyConfig = new HistoryLoader(xmlConfig);
		history = historyConfig.loadElement();
	}

	/**
	 * Verify that id of elements contained in the set isn't already contained
	 * in the ids set.
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
	private <E extends Mvp4gElement> void checkUniquenessOf(Set<E> subset,
			Set<String> ids) throws NonUniqueIdentifierException {
		for (E item : subset) {
			boolean unique = ids.add(item.getUniqueIdentifier());
			if (!unique) {
				throw new NonUniqueIdentifierException(item
						.getUniqueIdentifier());
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
	private <T extends Mvp4gElement> T getElement(String elementName,
			Set<T> elements, Mvp4gElement relatedElement)
			throws UnknownConfigurationElementException {
		T eFound = null;
		for (T element : elements) {
			if (element.getUniqueIdentifier().equals(elementName)) {
				eFound = element;
				break;
			}
		}

		if (eFound == null) {
			throw new UnknownConfigurationElementException(relatedElement,
					elementName);
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
	private <T extends Mvp4gElement> void removeUselessElements(Set<T> set,
			Set<T> toRemove) {
		for (T e : toRemove) {
			set.remove(e);
			logger.log(TreeLogger.INFO, String.format(REMOVE_OBJ, e
					.getTagName(), e.getUniqueIdentifier()));
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
	private JClassType getType(Mvp4gElement element, String className)
			throws NotFoundClassException {
		JClassType type = oracle.findType(className);
		if (type == null) {
			throw new NotFoundClassException(element, className);
		}
		return type;
	}

}

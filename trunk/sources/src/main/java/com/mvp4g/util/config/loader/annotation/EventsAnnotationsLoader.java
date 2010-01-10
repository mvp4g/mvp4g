package com.mvp4g.util.config.loader.annotation;

import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.NotFoundHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.annotation.module.UseToLoadChildModuleView;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.BaseEventBusWithLookUp;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.ChildModuleElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

/**
 * A class responsible for loading information contained in <code>Events</code>
 * annotation.
 * 
 * @author plcoirier
 * 
 */
public class EventsAnnotationsLoader extends Mvp4gAnnotationsLoader<Events> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#loadElement
	 * (com.google.gwt .core.ext.typeinfo.JClassType,
	 * java.lang.annotation.Annotation,
	 * com.mvp4g.util.config.Mvp4gConfiguration)
	 */
	@Override
	protected void loadElement(JClassType c, Events annotation,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {

		if (annotation.module().getCanonicalName().equals(
				configuration.getModule().getQualifiedSourceName())) {

			if (configuration.getEventBus() != null) {
				String err = "You can either define your events thanks to the configuration file or a single EventBus interface.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}

			if (configuration.getStart() != null) {
				String err = "You can't use start tag in your configuration file when you define your events in an EventBus interface.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}

			if (c.isInterface() == null) {
				String err = Events.class.getSimpleName()
						+ " annotation can only be used on interfaces.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}

			EventBusElement eventBus = buildEventBusElement(c, configuration);

			if (eventBus != null) {
				configuration.setEventBus(eventBus);
				loadChildModules(c, annotation, configuration);
				loadStartView(c, annotation, configuration);
				loadEvents(c, annotation, configuration);
			} else {
				String err = "this class must implement "
						+ EventBus.class.getCanonicalName()
						+ " since it is annoted with "
						+ Events.class.getSimpleName() + ".";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}
		}
		else{
			//save event bus type of potentiel child module
			configuration.getChildEventBusClassMap().put(annotation.module().getCanonicalName(), c);
		}
	}

	/**
	 * Build event bus element according to the implemented interface.
	 * 
	 * @param c
	 *            annoted class type
	 * @param configuration
	 *            configuration containing loaded elements of the application
	 * @return event bus corresponding to the implemented interface (null if
	 *         none of the interfaces are implemented)
	 */
	private EventBusElement buildEventBusElement(JClassType c,
			Mvp4gConfiguration configuration) {

		TypeOracle oracle = configuration.getOracle();

		EventBusElement eventBus = null;
		if (c.isAssignableTo(oracle.findType(EventBusWithLookup.class
				.getCanonicalName()))) {
			eventBus = new EventBusElement(c.getQualifiedSourceName(),
					BaseEventBusWithLookUp.class.getCanonicalName(), true);
		} else if (c.isAssignableTo(oracle.findType(EventBus.class
				.getCanonicalName()))) {
			eventBus = new EventBusElement(c.getQualifiedSourceName(),
					BaseEventBus.class.getCanonicalName(), false);
		}

		return eventBus;
	}

	private void loadChildModules(JClassType c, Events annotation,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {
		ChildModules childModules = c.getAnnotation(ChildModules.class);
		if (childModules != null) {
			ChildModule[] children = childModules.value();
			if ((children == null) || (children.length == 0)) {
				String err = "Useless " + ChildModules.class.getSimpleName()
						+ " annotation.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}

			Set<ChildModuleElement> modules = configuration.getChildModules();
			String moduleClass = null;
			ChildModuleElement module = null;
			for (ChildModule child : children) {
				moduleClass = child.moduleClass().getCanonicalName();
				if (getElementName(modules, moduleClass) != null) {
					String err = "You can't have two child modules describing the same module: "
							+ child.moduleClass().getCanonicalName();
					throw new Mvp4gAnnotationException(c
							.getQualifiedSourceName(), null, err);
				}
				module = new ChildModuleElement();
				try {
					module.setName(buildElementName(moduleClass, ""));
					module.setClassName(moduleClass);
					module.setAsync(Boolean.toString(child.async()));
					module.setAutoLoad(Boolean.toString(child.autoLoad()));
				} catch (DuplicatePropertyNameException e) {
					// setters are only called once, so this error can't occur.
				}
				addElement(modules, module, c, null);

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
	private void loadStartView(JClassType c, Events annotation,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {

		Set<ViewElement> views = configuration.getViews();
		String viewName = annotation.startViewName();
		Class<?> viewClass = annotation.startView();
		if ((viewName != null) && (viewName.length() > 0)) {
			boolean found = false;
			for (ViewElement view : views) {
				if (viewName.equals(view.getName())) {
					if (!viewClass.getCanonicalName().equals(
							view.getClassName())) {
						String err = "There is no instance of "
								+ viewClass.getCanonicalName() + " with name "
								+ viewName;
						throw new Mvp4gAnnotationException(c
								.getQualifiedSourceName(), null, err);
					}
					found = true;
					break;
				}
			}
			if (!found) {
				String err = "There is no view named " + viewName;
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}

		} else {
			viewName = getElementName(views, viewClass.getCanonicalName());
			if (viewName == null) {
				String err = "There is no instance of "
						+ viewClass.getCanonicalName();
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						null, err);
			}
		}

		try {
			StartElement element = new StartElement();
			element.setView(viewName);
			element.setHistory(Boolean.toString(annotation.historyOnStart()));
			configuration.setStart(element);
		} catch (DuplicatePropertyNameException e) {
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
	private void loadEvents(JClassType c, Events annotation,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {

		Event event = null;
		EventElement element = null;

		Set<EventElement> events = configuration.getEvents();

		JParameter[] params = null;

		for (JMethod method : c.getMethods()) {
			event = method.getAnnotation(Event.class);
			if (event == null) {
				String err = Event.class.getSimpleName()
						+ " annotation missing.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}

			params = method.getParameters();
			if (params.length > 1) {
				String err = "Event method must not have more than 1 argument.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}

			element = new EventElement();
			try {
				element.setType(method.getName());
				element.setHandlers(buildEventHandlers(c, method, event,
						configuration));
				element.setCalledMethod(event.calledMethod());
				element.setModulesToLoad(buildChildModules(c, method, event,
						configuration));
				element.setForwardToParent(Boolean.toString(event.forwardToParent()));

				if (params.length > 0) {
					element.setEventObjectClass(params[0].getType()
							.getQualifiedSourceName());
				}
			} catch (DuplicatePropertyNameException e) {
				// setters are only called once, so this error can't occur.
			}

			addElement(events, element, c, method);

			if (method.getAnnotation(Start.class) != null) {
				try {
					configuration.getStart().setEventType(method.getName());
				} catch (DuplicatePropertyNameException e) {
					String err = "Duplicate value for Start event. It is already defined by another method.";
					throw new Mvp4gAnnotationException(c
							.getQualifiedSourceName(), method.getName(), err);
				}
			}
			loadHistoryEvent(c, method, configuration);
			loadHistory(c, method, event, element, configuration);
			loadEventToLoadChildModuleView(c, method, configuration);
			loadChildConfig(c, method, configuration);

		}

	}

	/**
	 * Build handler of the events. If the class name of the handler is given,
	 * try to find if an instance of this class exists, otherwise throw an
	 * error.
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
	private String[] buildEventHandlers(JClassType c, JMethod method,
			Event event, Mvp4gConfiguration configuration)
			throws Mvp4gAnnotationException {

		Set<PresenterElement> presenters = configuration.getPresenters();

		String[] handlerNames = event.handlerNames();
		Class<?>[] handlerClasses = event.handlers();
		String[] handlers = new String[handlerNames.length
				+ handlerClasses.length];

		String handlerName = null;
		int index = 0;
		for (Class<?> handler : handlerClasses) {
			handlerName = getElementName(presenters, handler.getCanonicalName());
			if (handlerName == null) {
				String err = "No instance of " + handler.getCanonicalName()
						+ " is defined.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}
			handlers[index] = handlerName;
			index++;
		}

		for (String h : handlerNames) {
			handlers[index] = h;
			index++;
		}

		return handlers;
	}

	private String[] buildChildModules(JClassType c, JMethod method,
			Event event, Mvp4gConfiguration configuration)
			throws Mvp4gAnnotationException {
		Set<ChildModuleElement> loadedChildModules = configuration.getChildModules();

		Class<?>[] childModuleClasses = event.modulesToLoad();
		String[] childModules = new String[childModuleClasses.length];

		String moduleName = null;
		int index = 0;
		for (Class<?> moduleClass : childModuleClasses) {
			moduleName = getElementName(loadedChildModules, moduleClass
					.getCanonicalName());
			if (moduleName == null) {
				String err = "No instance of " + moduleClass.getCanonicalName()
						+ " is defined.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}
			childModules[index] = moduleName;
			index++;
		}

		return childModules;
	}

	/**
	 * Build history converter of an event. If the converter class name is
	 * given, first it tries to find an instance of this class, and if none is
	 * found, create one.
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
	private void loadHistory(JClassType c, JMethod method, Event annotation,
			EventElement element, Mvp4gConfiguration configuration)
			throws Mvp4gAnnotationException {
		String hcName = annotation.historyConverterName();
		Class<?> hcClass = annotation.historyConverter();
		if ((hcName != null) && (hcName.length() > 0)) {
			try {
				element.setHistory(hcName);
			} catch (DuplicatePropertyNameException e) {
				// setter is only called once, so this error can't occur.
			}
		} else if (!Event.NoHistoryConverter.class.equals(hcClass)) {
			String hcClassName = hcClass.getCanonicalName();
			Set<HistoryConverterElement> historyConverters = configuration
					.getHistoryConverters();
			hcName = getElementName(historyConverters, hcClassName);
			if (hcName == null) {
				String err = "No instance of " + hcClassName + " is defined.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}
			try {
				element.setHistory(hcName);
			} catch (DuplicatePropertyNameException e) {
				// setters are only called once, so this error can't occur.
			}
		}
	}

	private void loadHistoryEvent(JClassType c, JMethod method,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {
		if (method.getAnnotation(InitHistory.class) != null) {
			HistoryElement history = configuration.getHistory();
			if (history == null) {
				history = new HistoryElement();
				configuration.setHistory(history);
			}

			try {
				history.setInitEvent(method.getName());
			} catch (DuplicatePropertyNameException e) {
				String err = "Duplicate value for Init History event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}			
		}
		if (method.getAnnotation(NotFoundHistory.class) != null) {
			HistoryElement history = configuration.getHistory();
			if (history == null) {
				history = new HistoryElement();
				configuration.setHistory(history);
			}

			try {
				history.setNotFoundEvent(method.getName());
			} catch (DuplicatePropertyNameException e) {
				String err = "Duplicate value for Not Found History event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}	
		}
	}

	private void loadEventToLoadChildModuleView(JClassType c, JMethod method,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {
		UseToLoadChildModuleView annotation = method
				.getAnnotation(UseToLoadChildModuleView.class);
		if (annotation != null) {
			ChildModuleElement module = null;
			Set<ChildModuleElement> childModules = configuration.getChildModules();
			for (Class<?> moduleClass : annotation.value()) {
				module = getElement(childModules, moduleClass
						.getCanonicalName());
				if (module == null) {
					String err = "No instance of "
							+ moduleClass.getCanonicalName() + " is defined.";
					throw new Mvp4gAnnotationException(c
							.getQualifiedSourceName(), method.getName(), err);
				}
				try {
					module.setEventToLoadView(method.getName());
				} catch (DuplicatePropertyNameException e) {
					String err = "Module "
							+ module.getClassName()
							+ ": you can't have two events to load this module view.";
					throw new Mvp4gAnnotationException(c
							.getQualifiedSourceName(), method.getName(), err);
				}

			}
		}
	}
	
	private void loadChildConfig(JClassType c, JMethod method,
			Mvp4gConfiguration configuration) throws Mvp4gAnnotationException {
		if (method.getAnnotation(BeforeLoadChildModule.class) != null) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if (childConfig == null) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig(childConfig);
			}
			try {
				childConfig.setBeforeEvent(method.getName());
			} catch (DuplicatePropertyNameException e) {
				String err = "Duplicate value for Before Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}			
		}
		if (method.getAnnotation(AfterLoadChildModule.class) != null) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if (childConfig == null) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig(childConfig);
			}
			try {
				childConfig.setAfterEvent(method.getName());
			} catch (DuplicatePropertyNameException e) {
				String err = "Duplicate value for After Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}			
		}
		if (method.getAnnotation(LoadChildModuleError.class) != null) {
			ChildModulesElement childConfig = configuration.getLoadChildConfig();
			if (childConfig == null) {
				childConfig = new ChildModulesElement();
				configuration.setLoadChildConfig(childConfig);
			}
			try {
				childConfig.setErrorEvent(method.getName());
			} catch (DuplicatePropertyNameException e) {
				String err = "Duplicate value for Error Load Child event. It is already defined by another method or in your configuration file.";
				throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
						method.getName(), err);
			}			
		}
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvp4g.util.config.loader.annotation.Mvp4gAnnotationsLoader#controlType
	 * (com.google.gwt .core.ext.typeinfo.JClassType,
	 * com.google.gwt.core.ext.typeinfo.JClassType)
	 */
	@Override
	protected void controlType(JClassType c, JClassType s) {
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

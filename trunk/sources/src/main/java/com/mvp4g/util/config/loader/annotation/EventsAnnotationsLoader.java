package com.mvp4g.util.config.loader.annotation;

import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.XmlEventBus;
import com.mvp4g.util.config.Mvp4gConfiguration;
import com.mvp4g.util.config.element.EventBusElement;
import com.mvp4g.util.config.element.EventElement;
import com.mvp4g.util.config.element.HistoryConverterElement;
import com.mvp4g.util.config.element.HistoryElement;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.StartElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;

public class EventsAnnotationsLoader extends Mvp4gAnnotationsLoader<Events> {

	@Override
	protected void loadElement( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		if ( configuration.getEvents().size() > 0 ) {
			String err = "You can either define your events thanks to the configuration file or an EventBus interface but not both.";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
		}

		if ( configuration.getStart() != null ) {
			String err = "You can't use start tag in your configuration file when you define your events in an EventBus interface.";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
		}

		if ( c.isInterface() == null ) {
			String err = Events.class.getSimpleName() + " annotation can only be used on interfaces.";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
		}

		EventBusElement eventBus = buildEventBusElement( c );

		if ( eventBus != null ) {
			configuration.setEventBus( eventBus );
			loadStartView( c, annotation, configuration );
			loadEvents( c, annotation, configuration );
		} else {
			String err = Events.class.getSimpleName() + " annotation can only be used on classes implemented " + EventBus.class.getName() + ". ";
			throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
		}
	}

	private EventBusElement buildEventBusElement( JClassType c ) {
		boolean ok = false;
		boolean withLookup = false;
		String className = null;
		for ( JClassType type : c.getImplementedInterfaces() ) {
			className = type.getQualifiedSourceName();
			if ( EventBusWithLookup.class.getName().equals( className ) ) {
				ok = true;
				withLookup = true;
				break;
			} else if ( EventBus.class.getName().equals( type.getQualifiedSourceName() ) ) {
				ok = true;
			}
		}

		EventBusElement eventBus = null;
		if ( ok ) {
			if ( withLookup ) {
				eventBus = new EventBusElement( c.getQualifiedSourceName(), XmlEventBus.class.getName(), true, false );
			} else {
				eventBus = new EventBusElement( c.getQualifiedSourceName(), BaseEventBus.class.getName(), false, false );
			}
		}
		return eventBus;
	}

	private void loadStartView( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		Set<ViewElement> views = configuration.getViews();
		String viewName = annotation.startViewName();
		Class<?> viewClass = annotation.startView();
		if ( ( viewName != null ) && ( viewName.length() > 0 ) ) {
			for ( ViewElement view : views ) {
				if ( viewName.equals( view.getName() ) ) {
					if ( !viewClass.getName().equals( view.getClassName() ) ) {
						String err = "There is no instance of " + viewClass.getName() + " with name " + viewName;
						throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
					}
					break;
				}
			}

		} else {
			viewName = getElementName( views, viewClass.getName() );
			if ( viewName == null ) {
				String err = "There is no instance of " + viewClass.getName();
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), null, err );
			}
		}

		try {
			StartElement element = new StartElement();
			element.setView( viewName );
			element.setHistory( Boolean.toString( annotation.historyOnStart() ) );
			configuration.setStart( element );
		} catch ( DuplicatePropertyNameException e ) {
			//setters are only called once, so this error can't occur.
		}

	}

	private void loadEvents( JClassType c, Events annotation, Mvp4gConfiguration configuration ) throws Mvp4gAnnotationException {

		Event event = null;
		EventElement element = null;

		Set<EventElement> events = configuration.getEvents();

		JParameter[] params = null;

		for ( JMethod method : c.getMethods() ) {
			event = method.getAnnotation( Event.class );
			if ( event == null ) {
				String err = Event.class.getSimpleName() + " annotation missing.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}

			params = method.getParameters();
			if ( params.length > 1 ) {
				String err = "Event method must not have more than 1 argument.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}

			element = new EventElement();
			try {
				element.setType( method.getName() );
				element.setHandlers( buildEventHandlers( c, method, event, configuration ) );
				element.setCalledMethod( event.calledMethod() );

				if ( params.length > 0 ) {
					element.setEventObjectClass( params[0].getType().getQualifiedSourceName() );
				}
			} catch ( DuplicatePropertyNameException e ) {
				//setters are only called once, so this error can't occur.
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
			if ( method.getAnnotation( InitHistory.class ) != null ) {
				HistoryElement history = configuration.getHistory();
				if ( history != null ) {
					String err = "Duplicate value for Init History event. It is already defined by another method or in your configuration file.";
					throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
				} else {
					history = new HistoryElement();
					try {
						history.setInitEvent( method.getName() );
					} catch ( DuplicatePropertyNameException e ) {
						//setter is only called once, so this error can't occur.
					}
					configuration.setHistory( history );
				}
			}
			loadHistory( event, element, configuration );

		}

	}

	private String[] buildEventHandlers( JClassType c, JMethod method, Event event, Mvp4gConfiguration configuration )
			throws Mvp4gAnnotationException {

		Set<PresenterElement> presenters = configuration.getPresenters();

		String[] handlerNames = event.handlerNames();
		Class<?>[] handlerClasses = event.handlers();
		String[] handlers = new String[handlerNames.length + handlerClasses.length];

		String handlerName = null;
		int index = 0;
		for ( Class<?> handler : handlerClasses ) {
			handlerName = getElementName( presenters, handler.getName() );
			if ( handlerName == null ) {
				String err = "No instance of " + handler.getName() + "is defined.";
				throw new Mvp4gAnnotationException( c.getQualifiedSourceName(), method.getName(), err );
			}
			handlers[index] = handlerName;
			index++;
		}

		for ( String h : handlerNames ) {
			handlers[index] = h;
			index++;
		}

		return handlers;
	}

	private void loadHistory( Event annotation, EventElement element, Mvp4gConfiguration configuration ) {
		String hcName = annotation.historyConverterName();
		Class<?> hcClass = annotation.historyConverter();
		if ( ( hcName != null ) && ( hcName.length() > 0 ) ) {
			try {
				element.setHistory( hcName );
			} catch ( DuplicatePropertyNameException e ) {
				//setter is only called once, so this error can't occur.
			}
		} else if ( !Event.NoHistoryConverter.class.equals( hcClass ) ) {
			String hcClassName = hcClass.getName();
			Set<HistoryConverterElement> historyConverters = configuration.getHistoryConverters();
			hcName = getElementName( historyConverters, hcClassName );
			if ( hcName == null ) {
				HistoryConverterElement hcElement = new HistoryConverterElement();
				try {
					hcElement.setClassName( hcClassName );
					hcElement.setName( buildElementName( hcClassName, "" ) );
				} catch ( DuplicatePropertyNameException e ) {
					//setters are only called once, so this error can't occur.
				}

			}
			try {
				element.setHistory( hcName );
			} catch ( DuplicatePropertyNameException e ) {
				//setters are only called once, so this error can't occur.
			}
		}
	}

}

package com.mvp4g.util.config.loader.annotation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.annotation.EventHandlers;
import com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler;

public class EventHandlerAnnotationsLoaderTest extends AbstractMvp4gAnnotationsWithServiceLoaderTest<EventHandler, EventHandlerAnnotationsLoader> {

	@Test
	public void testNotMultiple() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( getSimpleClass() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventHandlerElement element = configuration.getEventHandlers().iterator().next();
		assertFalse( element.isMultiple() );
	}

	@Test
	public void testMultiple() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( EventHandlers.MultipleEventHandler.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		EventHandlerElement element = configuration.getEventHandlers().iterator().next();
		assertTrue( element.isMultiple() );
	}

	@Override
	protected Class<?> getClassNotAsync() {
		return EventHandlers.EventHandlerNotAsync.class;
	}

	@Override
	protected Class<?> getClassNotPublic() {
		return EventHandlers.EventHandlerNotPublic.class;
	}

	@Override
	protected Class<?> getClassWithMoreThanOne() {
		return EventHandlers.EventHandlerWithMoreThanOneParameter.class;
	}

	@Override
	protected Class<?> getClassWithNoParameter() {
		return EventHandlers.EventHandlerNoParameter.class;
	}

	@Override
	protected Class<?> getSameService() {
		return EventHandlers.EventHandlerWithSameService.class;
	}

	@Override
	protected Class<?> getService() {
		return EventHandlers.EventHandlerWithService.class;
	}

	@Override
	protected Class<?> getServiceWithName() {
		return EventHandlers.EventHandlerWithServiceAndName.class;
	}

	@Override
	protected EventHandlerAnnotationsLoader createLoader() {
		return new EventHandlerAnnotationsLoader();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected Set<EventHandlerElement> getSet() {
		return configuration.getEventHandlers();
	}

	@Override
	protected Class<?> getSimpleClass() {
		return SimpleEventHandler.class;
	}

	@Override
	protected Class<?> getWithNameClass() {
		return EventHandlers.EventHandlerWithName.class;
	}

	@Override
	protected Class<?> getWrongInterface() {
		return Object.class;
	}

}

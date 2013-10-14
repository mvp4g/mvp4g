package com.mvp4g.util.config.loader.annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.*;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.EventHandler;
import com.mvp4g.util.config.element.EventHandlerElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.annotation.EventHandlers;
import com.mvp4g.util.test_tools.annotation.handlers.SimpleEventHandler;

public class EventHandlerAnnotationsLoaderTest extends AbstractHandlerAnnotationsLoaderTest<EventHandler, EventHandlerElement, EventHandlerAnnotationsLoader> {

	@Test
	public void testPresenterAndEventHandler() {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( EventHandlers.PresenterAndEventHandler.class );
		annotedClasses.add( type );
		try {
			loader.load( annotedClasses, configuration );
			fail();
		} catch ( Mvp4gAnnotationException e ) {
			assertEquals( EventHandlers.PresenterAndEventHandler.class.getCanonicalName()
					+ ": You can't annotate a class with @Presenter and @EventHandler.", e.getMessage());
		}
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

	@Override
	protected Class<?> getMultipleClass() {
		return EventHandlers.MultipleEventHandler.class;
	}

	@Override
	protected Class<?> getAsyncClass() {
		return EventHandlers.AsyncEventHandler.class;
	}

}

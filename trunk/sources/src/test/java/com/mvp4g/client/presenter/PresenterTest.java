package com.mvp4g.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

public class PresenterTest {

	private BasePresenter<Object, EventBus> presenter = null;

	@Before
	public void setUp() {
		presenter = new BasePresenter<Object, EventBus>();
	}

	@Test
	public void testDefaultConstructor() {
		assertNull( presenter.getView() );
		assertNull( presenter.getEventBus() );
	}

	@Test
	public void testSetter() {
		String view = "View";
		BaseEventBus bus = new BaseEventBus(){

			@Override
			protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ) {
				return null;
			}

			public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {
								
			}

			public void confirmNavigation( NavigationEventCommand event ) {
				
			}

			public void setApplicationHistoryStored( boolean historyStored ) {
				
			}
			
		};
		presenter.setEventBus( bus );
		presenter.setView( view );
		assertSame( presenter.getView(), view );
		assertSame( presenter.getEventBus(), bus );
	}

	@Test
	public void testXmlPresenter() {
		XmlPresenter<String> presenter = new XmlPresenter<String>();
		assertEquals( EventBusWithLookup.class,
				(Class<?>)( (ParameterizedType)presenter.getClass().getGenericSuperclass() ).getActualTypeArguments()[1] );

	}

}

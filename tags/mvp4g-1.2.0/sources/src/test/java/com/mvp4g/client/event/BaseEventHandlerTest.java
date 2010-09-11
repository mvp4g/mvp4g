package com.mvp4g.client.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;

public class BaseEventHandlerTest {

	private BaseEventHandler<EventBus> eventHandler = null;
	private int bindCallCount;

	@Before
	public void setUp() {
		eventHandler = new BaseEventHandler<EventBus>();
		bindCallCount = 0;
	}

	@Test
	public void testDefaultConstructor() {
		assertNull( eventHandler.getEventBus() );
	}

	@Test
	public void testSetter() {
		BaseEventBus bus = new BaseEventBus() {

			@Override
			protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ) {
				return null;
			}

		};
		eventHandler.setEventBus( bus );
		assertSame( eventHandler.getEventBus(), bus );
	}

	@Test
	public void testActivated() {
		BaseEventHandler<EventBus> handler = new BaseEventHandler<EventBus>() {
			public void bind() {
				bindCallCount++;
			}
		};
		
		assertTrue( handler.isActivated() );
		assertTrue( bindCallCount == 1 );
		
		assertTrue( handler.isActivated() );
		assertTrue( bindCallCount == 1 );
		
		handler.setActivated( false );
		
		assertFalse( handler.isActivated() );
		assertTrue( bindCallCount == 1 );
	}
	
	@Test
	public void testXmlEventHandler() {
		XmlEventHandler handler = new XmlEventHandler();
		assertEquals( EventBusWithLookup.class,
				(Class<?>)( (ParameterizedType)handler.getClass().getGenericSuperclass() ).getActualTypeArguments()[0] );

	}

}

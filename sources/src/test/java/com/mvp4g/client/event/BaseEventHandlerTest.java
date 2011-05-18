package com.mvp4g.client.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

public class BaseEventHandlerTest {

	private BaseEventHandler<BaseEventBus> eventHandler = null;
	private BaseEventBus bus;
	private int bindCallCount;

	@Before
	public void setUp() {
		eventHandler = new BaseEventHandler<BaseEventBus>();
		bindCallCount = 0;
		bus = new BaseEventBus() {

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
	}

	@Test
	public void testDefaultConstructor() {
		assertNull( eventHandler.getEventBus() );
	}

	@Test
	public void testSetter() {
		eventHandler.setEventBus( bus );
		assertSame( eventHandler.getEventBus(), bus );
	}

	@Test
	public void testActivated() {
		BaseEventHandler<EventBus> handler = new BaseEventHandler<EventBus>() {
			public void bind() {
				super.bind();
				bindCallCount++;
			}
		};

		assertTrue( handler.isActivated( false, null ) );
		assertTrue( bindCallCount == 1 );

		assertTrue( handler.isActivated( false, null ) );
		assertTrue( bindCallCount == 1 );

		handler.setActivated( false );

		assertFalse( handler.isActivated( false, null ) );
		assertTrue( bindCallCount == 1 );
	}

	@Test
	public void testPassiveEvent() {
		BaseEventHandler<EventBus> handler = new BaseEventHandler<EventBus>() {
			public void bind() {
				super.bind();
				bindCallCount++;
			}
		};

		assertFalse( handler.isActivated( true, null ) );
		assertTrue( bindCallCount == 0 );

		assertTrue( handler.isActivated( false, null ) );
		assertTrue( bindCallCount == 1 );

		assertTrue( handler.isActivated( true, null ) );
		assertTrue( bindCallCount == 1 );
	}

	@Test
	public void testTokeniser() {
		eventHandler.setEventBus( bus );
		BaseEventBus eventBus = eventHandler.getTokenGenerator();
		assertSame( bus, eventBus );
		assertTrue( bus.tokenMode );
	}

}

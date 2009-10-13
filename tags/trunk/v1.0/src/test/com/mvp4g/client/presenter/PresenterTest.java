package com.mvp4g.client.presenter;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import com.mvp4g.client.event.EventBus;

public class PresenterTest {

	@Test
	public void testDefaultConstructor() {
		Presenter<Object> p = new Presenter<Object>();
		assertNull( p.getView() );
		assertNull( p.getEventBus() );
	}

	@Test
	public void testConstructorWithArgument() {
		String view = "View";
		final EventBus bus = new EventBus();
		Presenter<String> p = new Presenter<String>( view, bus );
		assertSame( p.getView(), view );
		assertSame( p.getEventBus(), bus );
	}

	@Test
	public void testSetter() {
		String view = "View";
		final EventBus bus = new EventBus();
		Presenter<String> p = new Presenter<String>();
		p.setEventBus( bus );
		p.setView( view );
		assertSame( p.getView(), view );
		assertSame( p.getEventBus(), bus );
	}

	@Test
	public void testBindCalledBySetView() {
		Presenter<String> p = new Presenter<String>() {
			@Override
			public void bind() {
				view = "bind";
			}
		};

		p.setView( "view" );
		assertSame( p.getView(), "bind" );
	}

}

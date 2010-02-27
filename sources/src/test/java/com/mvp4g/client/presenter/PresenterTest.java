package com.mvp4g.client.presenter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;

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
		BaseEventBus bus = new BaseEventBus();
		presenter.setEventBus( bus );
		presenter.setView( view );
		assertSame( presenter.getView(), view );
		assertSame( presenter.getEventBus(), bus );
	}

	@Test
	public void testBindIfNeeded() {
		final String bind = "bind";

		BasePresenter<String, EventBus> p = new BasePresenter<String, EventBus>() {
			@Override
			public void bind() {
				view = bind;
			}
		};

		assertNull( p.getView() );
		p.bindIfNeeded();
		assertEquals( p.getView(), bind );
		p.setView( "notBind" );
		p.bindIfNeeded();
		assertFalse( bind.equals( p.getView() ) );
	}

	@Test
	public void testXmlPresenter() {
		XmlPresenter<String> presenter = new XmlPresenter<String>();
		assertEquals( EventBusWithLookup.class,
				(Class<?>)( (ParameterizedType)presenter.getClass().getGenericSuperclass() ).getActualTypeArguments()[1] );

	}

}

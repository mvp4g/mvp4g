package com.mvp4g.util;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class TestMvp4gGenerator {

	Mvp4gGenerator generator;

	@Before
	public void setUp() {
		generator = new Mvp4gGenerator();
	}

	@Test
	public void testClassesToImport() {
		String[] classesToImport = new String[] { "com.mvp4g.client.history.PlaceService", "com.google.gwt.core.client.GWT",
				"com.google.gwt.user.client.History", "com.google.gwt.user.client.rpc.ServiceDefTarget",
				"com.mvp4g.client.presenter.PresenterInterface", "com.mvp4g.client.event.EventBus", "com.mvp4g.client.Mvp4gException",
				"com.mvp4g.client.history.HistoryConverter", "com.mvp4g.client.Mvp4gEventPasser", "com.mvp4g.client.Mvp4gModule",
				"com.google.gwt.inject.client.GinModules", "com.google.gwt.inject.client.Ginjector", "com.mvp4g.client.event.BaseEventBus",
				"com.mvp4g.client.event.EventFilter", "com.mvp4g.client.event.EventHandlerInterface", "java.util.List",
				"com.mvp4g.client.history.NavigationEventCommand", "com.mvp4g.client.history.NavigationConfirmationInterface",
				"com.google.gwt.core.client.RunAsyncCallback", "com.mvp4g.client.Mvp4gRunAsync" };
		assertArrayEquals( classesToImport, generator.getClassesToImport() );
	}

}

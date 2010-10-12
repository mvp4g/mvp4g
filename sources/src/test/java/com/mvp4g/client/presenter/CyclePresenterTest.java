package com.mvp4g.client.presenter;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.gwt_event.LoadEvent;
import com.mvp4g.client.gwt_event.LoadHandler;
import com.mvp4g.client.gwt_event.UnloadEvent;
import com.mvp4g.client.gwt_event.UnloadHandler;
import com.mvp4g.client.view.CycleView;

public class CyclePresenterTest {

	private class TestCycleView implements CycleView {

		private HandlerManager eventBus = new HandlerManager( this );

		public void createView() {
			//nothing to do			
		}

		public HandlerRegistration addLoadHandler( LoadHandler handler ) {
			return eventBus.addHandler( LoadEvent.TYPE, handler );
		}

		public void fireEvent( GwtEvent<?> event ) {
			eventBus.fireEvent( event );
		}

		public HandlerRegistration addUnloadHandler( UnloadHandler handler ) {
			return eventBus.addHandler( UnloadEvent.TYPE, handler );
		}

	}

	private class TestCyclePresenter extends CyclePresenter<TestCycleView, EventBus> {

		private boolean onLoad, onUnload, onBeforeEvent;

		public void onLoad() {
			super.onLoad();
			onLoad = true;
		}

		public void onUnload() {
			super.onUnload();
			onUnload = true;
		}

		public void onBeforeEvent() {
			super.onBeforeEvent();
			onBeforeEvent = true;
		}

		/**
		 * @return the onLoad
		 */
		public boolean isOnLoad() {
			return onLoad;
		}

		/**
		 * @return the onUnload
		 */
		public boolean isOnUnload() {
			return onUnload;
		}

		/**
		 * @return the onBeforeEvent
		 */
		public boolean isOnBeforeEvent() {
			return onBeforeEvent;
		}

	}

	private TestCycleView view;
	private TestCyclePresenter presenter;

	@Before
	public void setUp() {
		view = new TestCycleView();
		presenter = new TestCyclePresenter();
		presenter.setView( view );
	}

	@Test
	public void testLoadEvent() {
		view.fireEvent( new LoadEvent() );
		assertTrue( presenter.isOnLoad() );
		assertFalse( presenter.isOnUnload() );
	}

	@Test
	public void testUnloadEvent() {
		view.fireEvent( new UnloadEvent() );
		assertTrue( presenter.isOnUnload() );
		assertFalse( presenter.isOnLoad() );
	}
	
	@Test
	public void testBeforeEvent(){
		presenter.setActivated( false );
		presenter.isActivated();
		assertFalse( presenter.isOnBeforeEvent() );
		
		presenter.setActivated( true );
		presenter.isActivated();
		assertTrue( presenter.isOnBeforeEvent() );
	}

}

package com.mvp4g.client.view;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.mvp4g.client.gwt_event.LoadEvent;
import com.mvp4g.client.gwt_event.LoadHandler;
import com.mvp4g.client.gwt_event.UnloadEvent;
import com.mvp4g.client.gwt_event.UnloadHandler;

/**
 * Base implementation of a {@link CycleView}. Fire {@link LoadEvent} and {@link UnloadEvent} when
 * it is attached to/detached from the DOM.
 * 
 * @author plcoirier
 * 
 */
public abstract class BaseCycleView extends Composite implements CycleView {

	public HandlerRegistration addUnloadHandler( UnloadHandler handler ) {
		return addHandler( handler, UnloadEvent.TYPE );
	}

	public HandlerRegistration addLoadHandler( LoadHandler handler ) {
		return addHandler( handler, LoadEvent.TYPE );
	}

	@Override
	public void onLoad() {
		super.onLoad();
		fireEvent( new LoadEvent() );
	}

	@Override
	public void onUnload() {
		super.onUnload();
		fireEvent( new UnloadEvent() );
	}

}

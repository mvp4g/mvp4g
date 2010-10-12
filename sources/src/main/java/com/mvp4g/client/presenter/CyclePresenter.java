package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.gwt_event.LoadHandler;
import com.mvp4g.client.gwt_event.UnloadHandler;
import com.mvp4g.client.view.CycleView;

public class CyclePresenter<V extends CycleView, E extends EventBus> extends LazyPresenter<V, E> implements LoadHandler, UnloadHandler{
	
	@Override
	public void setView(V view){
		super.setView( view );
		view.addLoadHandler( this );
		view.addUnloadHandler( this );
	}

	public void onLoad() {
		
	}

	public void onUnload() {
		
	}
	
	@Override
	public boolean isActivated(){
		boolean activated = super.isActivated();
		if(activated){
			onBeforeEvent();
		}
		return activated;
	}
	
	public void onBeforeEvent(){
		
	}

}

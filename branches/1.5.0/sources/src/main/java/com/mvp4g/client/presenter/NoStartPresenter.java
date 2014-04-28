package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;

public class NoStartPresenter implements PresenterInterface<Object, EventBus> {

	public void setEventBus( EventBus eventBus ) {
		throw new RuntimeException( "This message should never be called" );
	}

	public EventBus getEventBus() {
		throw new RuntimeException( "This message should never be called" );
	}

	public EventBus getTokenGenerator() {
		throw new RuntimeException( "This message should never be called" );
	}

	public void bind() {
		throw new RuntimeException( "This message should never be called" );		
	}

	public boolean isActivated( boolean passive, String eventName, Object... parameters ) {
		throw new RuntimeException( "This message should never be called" );
	}

	public void setActivated( boolean activated ) {
		throw new RuntimeException( "This message should never be called" );		
	}

	public void setView( Object view ) {
		throw new RuntimeException( "This message should never be called" );		
	}

	public Object getView() {
		throw new RuntimeException( "This message should never be called" );
	}

}

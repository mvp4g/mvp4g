package com.mvp4g.client;

public class Presenter implements PresenterInterface {

	protected EventBus eventBus = null;

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}

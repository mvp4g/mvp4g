package com.mvp4g.client.test_tools;

import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

public class Mvp4gModuleStub implements Mvp4gModule {

	private EventBus eventBus;
	private String eventType;
	private Object form;
	private Mvp4gEventPasser<Boolean> passer;

	public Mvp4gModuleStub( EventBus eventBus ) {
		this.eventBus = eventBus;
	}

	public void addConverter( String token, HistoryConverter<?, ?> hc ) {

	}

	public void createAndStartModule() {

	}

	public <T> void dispatchHistoryEvent( String eventType, Mvp4gEventPasser<Boolean> passer ) {
		this.eventType = eventType;
		this.passer = passer;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public Object getStartView() {
		return null;
	}

	public <T> void place( String token, T form ) {
		this.eventType = token;
		this.form = form;
	}

	/**
	 * @return the eventType
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * @return the passer
	 */
	public Mvp4gEventPasser<Boolean> getPasser() {
		return passer;
	}

	/**
	 * @return the form
	 */
	public Object getForm() {
		return form;
	}

}

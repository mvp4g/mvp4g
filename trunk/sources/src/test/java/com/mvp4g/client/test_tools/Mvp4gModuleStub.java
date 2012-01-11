package com.mvp4g.client.test_tools;

import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.NavigationConfirmationInterface;
import com.mvp4g.client.history.NavigationEventCommand;

public class Mvp4gModuleStub implements Mvp4gModule {

	public final String TOKEN = "token";
	
	private EventBus eventBus;
	private String eventType;
	private boolean tokenOnly;
	private Object form;
	private Mvp4gEventPasser passer;

	public Mvp4gModuleStub( EventBus eventBus ) {
		this.eventBus = eventBus;
	}

	public void addConverter( String token, HistoryConverter<?> hc ) {

	}

	public void createAndStartModule() {

	}

	public void dispatchHistoryEvent( String eventType, Mvp4gEventPasser passer ) {
		this.eventType = eventType;
		this.passer = passer;
	}

	public EventBus getEventBus() {
		return eventBus;
	}

	public Object getStartView() {
		return null;
	}

	public String place( String token, String form, boolean tokenOnly ) {
		this.eventType = token;
		this.form = form;
		this.tokenOnly = tokenOnly;
		return TOKEN;
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
	public Mvp4gEventPasser getPasser() {
		return passer;
	}

	/**
	 * @return the form
	 */
	public Object getForm() {
		return form;
	}
	
	/**
	 * @return the tokenOnly
	 */
	public Object getTokenOnly() {
		return tokenOnly;
	}

	public void addConverter( String eventType, String historyName, HistoryConverter<?> hc ) {
		// TODO Auto-generated method stub

	}

	public void clearHistory() {
		this.eventType = null;
		this.form = null;
	}

	public void onForward() {
		// TODO Auto-generated method stub

	}

	public void setParentModule( Mvp4gModule parentModule ) {
		// TODO Auto-generated method stub

	}

	public void confirmEvent( NavigationEventCommand event ) {
		// TODO Auto-generated method stub
		
	}

	public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadChildModule( String childModuleClassName, String eventName, boolean passive, Mvp4gEventPasser passer ) {
		// TODO Auto-generated method stub
		
	}

}

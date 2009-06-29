package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;

/**
 * Interface that defines a presenter.<br/><br/>
 * The only method that a class that wants to be a presenter needs 
 * to implement is the setEventBus method that allows to set 
 * an event bus to the presenter. 
 * 
 * @author plcoirier
 *
 */
public interface PresenterInterface {
	
	/**
	 * Set an event bus to the presenter
	 * 
	 * @param eventBus
	 * 			event bus to set
	 */
	public void setEventBus(EventBus eventBus);

}

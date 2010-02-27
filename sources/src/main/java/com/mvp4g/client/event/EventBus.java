package com.mvp4g.client.event;

/**
 * Interface that defines an event bus. All classes defining an event bus must implement it.
 * 
 * @author PL
 * 
 */
public interface EventBus {

	/**
	 * Indicate for all events if they should be stored or not in browser history when possible
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	public void setHistoryStored( boolean historyStored );

	/**
	 * Indicate for next event that can be stored in history only if it should be stored or not in
	 * browser history.<br/>
	 * <br/>
	 * This method should be called only right before sending an event that could be stored in
	 * browser history.
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	public void setHistoryStoredForNextOne( boolean historyStored );

	/**
	 * Indicate if events are stored in browser history when possible
	 * 
	 * @return
	 */
	public boolean isHistoryStored();
}

package com.mvp4g.client.history;

import com.mvp4g.client.event.EventBus;

/**
 * Interface that defines method to convert a token from/to an event.<br/>
 * 
 * @author plcoirier
 * 
 * @param <T>
 *            type of the object used with the event associated with the converter.
 */
public interface HistoryConverter<T, E extends EventBus> {

	/**
	 * Convert the object of the event to a string in order to store into the URI.<br/>
	 * <br/>
	 * Specific information can also be stored (in a cookie for example)
	 * 
	 * @param eventType
	 *            type of the event
	 * @param form
	 *            object of the event
	 * @return string to store if the URI. If you don't want to store anything in the URI, return
	 *         null or empty string.
	 */
	public String convertToToken( String eventType, T form );

	/**
	 * Convert a token to event's object and trigger the event bus.<br/>
	 * <br/>
	 * Specific information can also be retrieved (from a cookie or server for example).
	 * 
	 * @param eventType
	 *            type of the event that was stored in the token
	 * @param param
	 *            string that was stored in the token, used to retrieve event's object (can be null
	 *            if no information was stored in the URI)
	 * @param eventBus
	 *            event bus of the application
	 */
	public void convertFromToken( String eventType, String param, E eventBus );

}

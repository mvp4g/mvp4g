package com.mvp4g.client.event;

import com.mvp4g.client.history.PlaceService;

/**
 * Base implementation of the event bus. It should only be used by the framework.
 * 
 * @author PL
 * 
 */
public class BaseEventBus implements EventBus {

	private boolean historyStored = true;
	private boolean changeForNextOne = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#isHistoryStored()
	 */
	public boolean isHistoryStored() {
		return historyStored;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setHistoryStored(boolean)
	 */
	public void setHistoryStored( boolean historyStored ) {
		this.historyStored = historyStored;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setHistoryStoredForNextOne(boolean)
	 */
	public void setHistoryStoredForNextOne( boolean historyStored ) {
		if ( historyStored != this.historyStored ) {
			changeForNextOne = true;
			this.historyStored = historyStored;
		}
	}

	/**
	 * Interact with place service when needed
	 * 
	 * @param placeService
	 *            place service to interact with
	 * @param type
	 *            type of the event to store
	 * @param form
	 *            object of the event to store
	 */
	protected void place( PlaceService<? extends EventBus> placeService, String type, Object form ) {
		if ( historyStored ) {
			placeService.place( type, form );
		}
		if ( changeForNextOne ) {
			historyStored = !historyStored;
			changeForNextOne = false;
		}
	}

}

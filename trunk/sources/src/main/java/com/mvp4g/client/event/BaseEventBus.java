package com.mvp4g.client.event;

import com.mvp4g.client.history.PlaceService;

public class BaseEventBus implements EventBus {

	private boolean historyStored = true;
	private boolean changeForNextOne = false;

	public boolean isHistoryStored() {
		return historyStored;
	}

	public void setHistoryStored( boolean historyStored ) {
		this.historyStored = historyStored;
	}

	public void setHistoryStoredForNextOne( boolean historyStored ) {
		if ( historyStored != this.historyStored ) {
			changeForNextOne = true;
			this.historyStored = historyStored;
		}
	}

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

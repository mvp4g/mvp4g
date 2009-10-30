package com.mvp4g.client.event;

public class BaseEventBus implements EventBus {
	
	private boolean historyStored = true;

	public boolean isHistoryStored() {
		return historyStored;
	}

	public void setHistoryStored( boolean historyStored ) {
		this.historyStored = historyStored;
	}

}

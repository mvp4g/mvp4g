package com.mvp4g.client.event;

public interface EventBus {

	public void setHistoryStored( boolean historyStored );
	
	public void setHistoryStoredForNextOne( boolean historyStored);

	public boolean isHistoryStored();
}

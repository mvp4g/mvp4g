package com.mvp4g.example.client.company.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.XmlHistoryConverter;

@History( convertParams = false )
public class CompanyCreationHistoryConverter implements XmlHistoryConverter {

	public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {
		eventBus.dispatch( eventType );
	}

}

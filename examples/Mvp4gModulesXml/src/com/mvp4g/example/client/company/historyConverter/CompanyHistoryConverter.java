package com.mvp4g.example.client.company.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.XmlHistoryConverter;
import com.mvp4g.example.client.company.bean.CompanyBean;

@History
public class CompanyHistoryConverter implements XmlHistoryConverter {

	public void convertFromToken( String eventType, String param, EventBusWithLookup eventBus ) {
		String[] paramTab = param.split( "&" );
		CompanyBean company = new CompanyBean();
		company.setId( Integer.parseInt( paramTab[0].split( "=" )[1] ) );
		company.setName( paramTab[1].split( "=" )[1] );
		eventBus.dispatch( eventType, company );
	}
	
	public String onGoToDisplay( CompanyBean company ){
		return convertCompanyToToken( company );
	}

	private String convertCompanyToToken( CompanyBean company ) {
		return "id=" + company.getId() + "&name=" + company.getName();
	}

	public boolean isCrawlable() {
		return true;
	}

}


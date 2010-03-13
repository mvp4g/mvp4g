package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.bean.CompanyBean;

@History
public class CompanyHistoryConverter implements HistoryConverter<CompanyBean, CompanyEventBus> {

	public void convertFromToken( String eventType, String param, CompanyEventBus eventBus ) {
		String[] paramTab = param.split( "&" );
		CompanyBean company = new CompanyBean();
		company.setId( Integer.parseInt( paramTab[0].split( "=" )[1] ) );
		company.setName( paramTab[1].split( "=" )[1] );
		eventBus.goToDisplay( company );
	}

	public String convertToToken( String eventType, CompanyBean form ) {
		return "id=" + form.getId() + "&name=" + form.getName();
	}

}

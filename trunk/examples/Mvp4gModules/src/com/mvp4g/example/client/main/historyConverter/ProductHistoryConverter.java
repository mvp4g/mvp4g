package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.product.ProductEventBus;
import com.mvp4g.example.client.product.bean.ProductBean;

@History
public class ProductHistoryConverter implements HistoryConverter<ProductEventBus> {

	public void convertFromToken( String eventType, String param, ProductEventBus eventBus ) {
		String[] paramTab = param.split( "&" );
		ProductBean product = new ProductBean();
		product.setId( Integer.parseInt( paramTab[0].split( "=" )[1] ) );
		product.setName( paramTab[1].split( "=" )[1] );
		eventBus.goToDisplay( product );
	}
	
	public String onGoToDisplay( ProductBean product ){
		return convertProductToToken( product );
	}

	public String convertProductToToken( ProductBean product ) {
		return "id=" + product.getId() + "&name=" + product.getName();
	}

}

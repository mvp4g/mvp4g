package com.mvp4g.example.client.main.historyConverter;

import com.mvp4g.client.annotation.History;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.example.client.product.ProductEventBus;
import com.mvp4g.example.client.product.bean.ProductBean;

@History
public class ProductHistoryConverter implements HistoryConverter<ProductBean, ProductEventBus> {

	public void convertFromToken( String eventType, String param, ProductEventBus eventBus ) {
		String[] paramTab = param.split( "&" );
		ProductBean product = new ProductBean();
		product.setId( Integer.parseInt( paramTab[0].split( "=" )[1] ) );
		product.setName( paramTab[1].split( "=" )[1] );
		eventBus.goToDisplay( product );
	}

	public String convertToToken( String eventType, ProductBean form ) {
		return "id=" + form.getId() + "&name=" + form.getName();
	}

}

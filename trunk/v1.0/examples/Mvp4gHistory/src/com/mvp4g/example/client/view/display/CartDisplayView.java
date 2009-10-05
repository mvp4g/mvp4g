package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.CartDisplayViewInterface;

public class CartDisplayView extends Composite implements CartDisplayViewInterface {

	private VerticalPanel mainPanel = new VerticalPanel();

	public CartDisplayView() {
		initWidget( mainPanel );
	}

	public void addProduct( ProductBean product ) {
		mainPanel.add( new HTML( buildElement( product ) ) );

	}

	public void clear() {
		mainPanel.clear();
	}

	private String buildElement( ProductBean product ) {
		StringBuilder element = new StringBuilder( 200 );
		element.append( "<div><span>" );
		element.append( product.getName() );
		element.append( "</span>" );
		element.append( product.getPrice() );
		element.append( "</div><div>" );
		element.append( product.getDescription() );
		element.append( "<div>" );
		return element.toString();
	}

}

package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.display.CartDisplayViewInterface;
import com.mvp4g.example.client.view.BaseView;

public class CartDisplayView extends BaseView implements CartDisplayViewInterface {

	private VerticalPanel mainPanel = new VerticalPanel();

	@Override
	protected Widget createWidget() {
		setStyleName( "display" );
		return mainPanel;
	}

	public void addProduct( ProductBean product ) {
		mainPanel.add( new HTML( buildElement( product ) ) );
	}

	public void clear() {
		mainPanel.clear();
	}

	public Widget getViewWidget() {
		return this;
	}

	private String buildElement( ProductBean product ) {
		StringBuilder element = new StringBuilder( 200 );
		element.append( "<div><span class=\"name\">" );
		element.append( product.getName() );
		element.append( "</span><span class=\"price\" >" );
		element.append( product.getPrice() );
		element.append( "</span></div><div>" );
		element.append( product.getDescription() );
		element.append( "<div>" );
		return element.toString();
	}

}

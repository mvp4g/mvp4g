package com.mvp4g.example.client.presenter.view_interface.display;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.bean.ProductBean;

public interface CartDisplayViewInterface {

	public void clear();

	public void addProduct( ProductBean product );
	
	public Widget getViewWidget();

}

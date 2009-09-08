package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class RootTemplateView extends Composite implements RootTemplateViewInterface {

	private FlexTable table = new FlexTable();

	public RootTemplateView() {

		table.getFlexCellFormatter().setColSpan( 0, 0, 2 );

		initWidget( table );

	}

	public void setLeftBottomWidget( MyWidgetInterface widget ) {
		table.setWidget( 1, 0, widget.getMyWidget() );		
	}

	public void setRightBottomWidget( MyWidgetInterface widget ) {
		table.setWidget( 1, 1, widget.getMyWidget() );
	}

	public void setTopWidget( MyWidgetInterface widget ) {
		table.setWidget( 0, 0, widget.getMyWidget() );
	}

}

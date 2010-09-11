package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.mvp4g.example.client.presenter.RootTemplatePresenter.IRootTemplateView;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class RootTemplateView extends Composite implements IRootTemplateView {

	private FlexTable table = new FlexTable();

	public RootTemplateView() {

		table.getFlexCellFormatter().setColSpan( 0, 0, 2 );

		initWidget( table );

	}

	public void setLeftBottomWidget( IWidget widget ) {
		table.setWidget( 1, 0, widget.getMyWidget() );
	}

	public void setRightBottomWidget( IWidget widget ) {
		table.setWidget( 1, 1, widget.getMyWidget() );
	}

	public void setTopWidget( IWidget widget ) {
		table.setWidget( 0, 0, widget.getMyWidget() );
	}

}

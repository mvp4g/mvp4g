package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.RootTemplatePresenter.RootTemplateViewInterface;

public class RootTemplateView extends BaseView implements RootTemplateViewInterface {

	private Grid table = new Grid( 4, 1 );
	private Label messageBar = new Label();

	@Override
	protected Widget createWidget() {
		messageBar.setStyleName( "messageBar" );
		table.setWidget( 1, 0, messageBar );
		return table;
	}

	public void setBottomWidget( Widget w ) {
		table.setWidget( 3, 0, w );
	}

	public void setMainWidget( Widget w ) {
		table.setWidget( 2, 0, w );
	}

	public void setTopWidget( Widget w ) {
		table.setWidget( 0, 0, w );
	}

	public void clearMainWidget() {
		Widget w = table.getWidget( 2, 0 );
		if ( w != null ) {
			w.removeFromParent();
		}
	}

	public void setMessage( String message ) {
		messageBar.setText( message );
	}

}

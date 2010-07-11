package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.display.BasicBeanDisplayViewInterface;
import com.mvp4g.example.client.view.BaseView;

public class BasicBeanDisplayView extends BaseView implements BasicBeanDisplayViewInterface {

	private Label name = new Label();
	private Label description = new Label();

	@Override
	protected Widget createWidget() {
		VerticalPanel mainPanel = new VerticalPanel();
		setStyleName( "display" );
		name.addStyleName( "name" );
		description.addStyleName( "description" );
		mainPanel.add( name );
		mainPanel.add( description );
		return mainPanel;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void setDescription( String description ) {
		this.description.setText( description );
	}

	public void setName( String name ) {
		this.name.setText( name );
	}

}

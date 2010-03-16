package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.mvp4g.example.client.MainPresenter.MainViewInterface;

public class MainView extends Composite implements MainViewInterface {

	private Button ping = new Button( "Ping" );
	private Label label = new Label();

	public MainView() {
		VerticalPanel vp = new VerticalPanel();
		vp.add( ping );
		vp.add( label );
		initWidget( vp );
	}

	public Button getButton() {
		return ping;
	}

	public Label getLabel() {
		return label;
	}

}

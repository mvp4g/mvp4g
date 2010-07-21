package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.IUserViewInterface;

public class UserDisplayView extends Composite implements IUserViewInterface {

	private Button create = null;
	private Label id = null;
	private Label lastName = null;
	private Label firstName = null;

	public UserDisplayView() {

		id = new Label();
		lastName = new Label();
		firstName = new Label();

		Grid grid = new Grid( 3, 2 );
		grid.setText( 0, 0, "Last Name: " );
		grid.setText( 1, 0, "First Name: " );
		grid.setText( 2, 0, "Generated Id: " );
		grid.setWidget( 0, 1, lastName );
		grid.setWidget( 1, 1, firstName );
		grid.setWidget( 2, 1, id );

		create = new Button( "New" );

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.add( grid );
		mainPanel.add( create );

		initWidget( mainPanel );
	}

	public HasClickHandlers getButton() {
		return create;
	}

	public HasText getFirstName() {
		return firstName;
	}

	public HasText getLastName() {
		return lastName;
	}

	public Widget getViewWidget() {
		return this;
	}

	public HasText getId() {
		return id;
	}
}

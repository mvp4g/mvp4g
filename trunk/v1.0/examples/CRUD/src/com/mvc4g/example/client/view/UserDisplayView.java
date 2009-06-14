package com.mvc4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvc4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvc4g.example.client.view.widget.LabelWithValue;

public class UserDisplayView extends Composite implements
		UserViewInterface {
	
	private Button create = null;
	private LabelWithValue lastName = null;
	private LabelWithValue firstName = null;

	public UserDisplayView() {
		
		lastName = new LabelWithValue();
		firstName = new LabelWithValue();
		
		Grid grid = new Grid(2,2);
		grid.setText(0, 0, "Last Name: ");
		grid.setText(1, 0, "First Name: ");
		grid.setWidget(0, 1, lastName);
		grid.setWidget(1, 1, firstName);
		
		create = new Button("New");
		
		VerticalPanel mainPanel= new VerticalPanel();
		mainPanel.add(grid);
		mainPanel.add(create);
		
		initWidget(mainPanel);
	}

	public HasClickHandlers getButton() {
		return create;
	}

	public HasValue<String> getFirstName() {
		return firstName;
	}

	public HasValue<String> getLastName() {
		return lastName;
	}

	public Widget getViewWidget() {
		return this;
	}

}

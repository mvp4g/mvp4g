package com.mvp4g.example.client.view;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;

public class UserRoleView extends Composite implements UserRoleViewInterface {

	
	public UserRoleView(){		
		initWidget( new CaptionPanel("User Roles") );
	}
	
	public Widget getViewWidget() {
		return this;
	}

}

package com.mvp4g.example.client.main.view;

import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.mvp4g.example.client.util.display.IndexDisplayer;

public class FirefoxMainView extends MainView {

	@Inject
	public FirefoxMainView( IndexDisplayer indexDisplayer ) {
		super( indexDisplayer );
		mainPanel.insert( new Label( "Welcome Firefox Users" ), 0 );
	}

}

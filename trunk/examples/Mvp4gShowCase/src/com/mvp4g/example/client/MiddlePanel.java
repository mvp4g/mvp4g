package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MiddlePanel extends Composite {

	private HTML description = new HTML();
	private Frame demo = new Frame();
	private Frame configuration = new Frame();
	private TabPanel tabPanel = new TabPanel();

	public MiddlePanel() {

		VerticalPanel mainPanel = new VerticalPanel();
		initWidget( mainPanel );

		mainPanel.add( description );

		tabPanel.add( demo, "Demo" );
		tabPanel.add( configuration, "Mvp4g Configuration file" );

		mainPanel.add( tabPanel );

	}

	public void set( MenuItem item ) {
		description.setHTML( "<h2>" + item.getProjectName() + "</h2>" + item.getDescription() );
		demo.setUrl( "http://" + item.getAppId() + ".appspot.com/" );
		configuration.setUrl( "http://mvp4g.googlecode.com/svn/tags/mvp4g-1.0.0/examples/" + item.getProjectName() + "/src/mvp4g-conf.xml" );
		tabPanel.selectTab( 0 );
	}

}

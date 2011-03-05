package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MiddlePanel extends Composite {

	private HTML description = new HTML();
	private Frame demo = new Frame();
	private Frame eventbus = new Frame();
	private Frame configuration = new Frame();
	private TabPanel tabPanel = new TabPanel();

	public MiddlePanel() {

		VerticalPanel mainPanel = new VerticalPanel();
		initWidget( mainPanel );

		mainPanel.add( description );

		tabPanel.add( demo, "Demo" );
		tabPanel.add( eventbus, "Event Bus Interface" );

		mainPanel.add( tabPanel );

	}

	public void set( MenuItem item ) {
		description.setHTML( "<h2>" + item.getProjectName() + "</h2>" + item.getDescription() );
		String appId = item.getAppId();
		if ( appId.startsWith( "http" ) ) {
			demo.setUrl( appId );
		} else {
			demo.setUrl( "http://" + item.getAppId() + ".appspot.com/" );
		}
		eventbus.setUrl( "http://code.google.com/p/mvp4g/source/browse/tags/mvp4g-1.1.0/examples/" + item.getProjectName()
				+ "/src/com/mvp4g/example/client/" + item.getEventBusName() + ".java" );
		tabPanel.selectTab( 0 );

		int nbTab = tabPanel.getTabBar().getTabCount();
		if ( !item.isWithXml() ) {
			if ( nbTab == 3 ) {
				tabPanel.remove( configuration );
			}
		} else {
			configuration.setUrl( "http://mvp4g.googlecode.com/svn/tags/mvp4g-1.1.0/examples/" + item.getProjectName() + "Xml/src/mvp4g-conf.xml" );
			if ( nbTab < 3 ) {
				tabPanel.add( configuration, "Mvp4g XML Configuration" );
			}
		}

	}

}

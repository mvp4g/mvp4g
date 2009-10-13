package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mvp4gShowCase implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		HorizontalPanel mainPanel = new HorizontalPanel();

		final MiddlePanel mPanel = new MiddlePanel();

		Tree menu = new Tree();
		menu.addSelectionHandler( new SelectionHandler<TreeItem>() {

			public void onSelection( SelectionEvent<TreeItem> event ) {
				mPanel.set( (MenuItem)event.getSelectedItem() );
			}

		} );

		initMenu( menu );

		mainPanel.add( menu );
		mainPanel.add( mPanel );

		menu.setSelectedItem( menu.getItem( 0 ) );

		mainPanel.setStyleName( "mvp4gshowcase" );

		RootPanel.get().add( mainPanel );
	}

	private void initMenu( Tree menu ) {
		//TestMvp4g
		String d1 = "A basic example to easily understand how the framework works.";
		menu.addItem( new MenuItem( d1, "testmvp4g", "TestMvp4g" ) );
		
		//EmployeeAdmin
		String d2 = "The GWT PureMvc example (as shown <a href=\"http://employeeadm.appspot.com/\">here</a>) but implemented with the Mvp4g framework.<br/>This example also illustrates how to easily test the presenters using directly JUnit (without GWTTestCase).";
		menu.addItem( new MenuItem( d2, "mvp4gemployeeadmin", "EmployeeAdmin" ) );
		
		//EmployeeAdmin with GXT
		String d3 = "Same example as EmployeeAdmin but bottom frames are implemented with GXT (<a href=\"http://groups.google.com/group/mvp4g/browse_thread/thread/5ba8664afbbda1f7\">see Mvp4g and GXT post</a>).";
		menu.addItem( new MenuItem( d3, "mvp4gemployeeadminwithgxt", "EmployeeAdminWithGXT" ) );
		
		//Mvp4gHistory
		String d4 = "This example shows how history is managed thanks to a Place Service. (<a href=\"http://code.google.com/p/mvp4g/wiki/PlaceServiceInMvp4g\">see the wiki page for more information</a>).<br/><br/>" +
				"<img src=\"beware.gif\" height=15 width=15/> You need to open this application <a href=\"http://mvp4ghistory.appspot.com\">mvp4ghistory.appspot.com</a> in a new window in order to see url modifications.";
		menu.addItem( new MenuItem( d4, "mvp4ghistory", "Mvp4gHistory" ) );
	}
}

package com.mvp4g.example.client.main;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TabBar;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class MainView extends Composite implements MainPresenter.MainViewInterface {

	private Label c = new Label( "Company" );
	private Label p = new Label( "Product" );
	private SimplePanel bodyContainer = new SimplePanel();
	private PopupPanel wait = new PopupPanel();
	private Label message = new Label();
	private TabBar bar = new TabBar();
	private Label clearHistory = new Label( "Clear History" );

	private ListBox startIndex = new ListBox();
	private ListBox lastIndex = new ListBox();

	public MainView() {
		message.setStyleName( "messageBar" );
		message.setVisible( false );

		int i;
		for ( i = 0; i < 5; i++ ) {
			startIndex.addItem( Integer.toString( i ) );
		}
		for ( i = 5; i < 10; i++ ) {
			lastIndex.addItem( Integer.toString( i ) );
		}
		
		startIndex.setSelectedIndex( 0 );
		lastIndex.setSelectedIndex( 0 );

		HorizontalPanel hp = new HorizontalPanel();
		hp.setVerticalAlignment( HasVerticalAlignment.ALIGN_MIDDLE );
		hp.add( new Label( "Start Index: " ) );
		hp.add( startIndex );
		hp.add( new Label( "End Index: " ) );
		hp.add( lastIndex );
		hp.setSpacing( 5 );

		bar.addTab( c );
		bar.addTab( p );

		VerticalPanel mainPanel = new VerticalPanel();
		mainPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		clearHistory.setStyleName( "link" );
		mainPanel.add( clearHistory );
		
		mainPanel.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_LEFT );
		mainPanel.add( hp );
		mainPanel.add( bar );
		mainPanel.add( message );
		mainPanel.add( bodyContainer );
		wait.add( new Label( "Wait" ) );

		initWidget( mainPanel );

		bodyContainer.setWidget( new Label( "Click on one of the tab to start." ) );
	}

	public HasClickHandlers getCompanyMenu() {
		return c;
	}

	public HasClickHandlers getProductMenu() {
		return p;
	}

	public void setBody( Widget newBody ) {
		bodyContainer.setWidget( newBody );
	}

	public void displayErrorMessage( String error ) {
		Window.alert( "Error: " + error );
	}

	public void setWaitVisible( boolean visible ) {
		if ( visible ) {
			wait.setPopupPosition( bodyContainer.getAbsoluteLeft(), bodyContainer.getAbsoluteTop() );
			wait.setPixelSize( bodyContainer.getOffsetWidth(), bodyContainer.getOffsetHeight() );
			wait.show();
		} else {
			wait.hide();
		}
	}

	public void displayText( String text ) {
		message.setText( text );
		message.setVisible( text.length() > 0 );
	}

	public void selectCompanyMenu() {
		bar.selectTab( 0 );
	}

	public void selectProductMenu() {
		bar.selectTab( 1 );
	}

	public void displayAlertMessage( String message ) {
		Window.alert( message );
	}

	public HasClickHandlers getClearHistoryButton() {
		return clearHistory;
	}

	public int getLastIndex() {
		return lastIndex.getSelectedIndex() + 5;
	}

	public int getStartIndex() {
		return startIndex.getSelectedIndex();
	}

}

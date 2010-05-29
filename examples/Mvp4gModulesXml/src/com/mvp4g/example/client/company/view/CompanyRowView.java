package com.mvp4g.example.client.company.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.company.presenter.CompanyRowPresenter.ICompanyRowView;

public class CompanyRowView extends Composite implements ICompanyRowView {

	private Image delete;
	private Image display;
	private Image edit;
	private Image quickEdit;

	private Label name;

	public CompanyRowView() {

		name = new Label();

		display = new Image( "images/display.png" );
		quickEdit = new Image( "images/quickEdit.png" );
		edit = new Image( "images/edit.png" );
		delete = new Image( "images/delete.png" );

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing( 2 );
		hp.add( name );
		hp.add( display );
		hp.add( quickEdit );
		hp.add( edit );
		hp.add( delete );

		initWidget( hp );

	}

	public HasClickHandlers getDelete() {
		return delete;
	}

	public HasClickHandlers getDisplay() {
		return display;
	}

	public HasClickHandlers getEdit() {
		return edit;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void setName( String name ) {
		this.name.setText( name );
	}

	public void alert( String message ) {
		Window.alert( message );		
	}

	public HasClickHandlers getQuickEdit() {
		return quickEdit;
	}

}

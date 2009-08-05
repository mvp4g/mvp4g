package com.mvp4g.example.client.presenter.view_interface.widget_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface MyListBoxInterface extends HasClickHandlers {

	public void setSelectedIndex( int index );

	public int getSelectedIndex();

	public String getSelectedValue();

	public void setSelectedValue( String value );

	public void addItem( String item );

}

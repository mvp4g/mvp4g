package com.mvp4g.example.client.presenter.view_interface.widget_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.dom.client.HasKeyUpHandlers;

public interface MyListBoxInterface extends HasClickHandlers, HasKeyUpHandlers {

	public void setSelectedIndex( int index );

	public int getSelectedIndex();

	public String getSelectedValue();

	public void setSelectedValue( String value );

	public void addItem( String item );

	public void removeItem( String item );

	public void clear();

	public void setEnabled( boolean enabled );

}

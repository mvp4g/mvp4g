package com.mvp4g.example.client.mock.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;

public class MyMockListBox implements MyListBoxInterface {

	private List<String> items = new ArrayList<String>();
	private int selected = -1;
	private ClickHandler clickHandler = null;
	private KeyUpHandler keyUpHandler = null;

	private boolean enabled = true;

	public void addItem( String item ) {
		items.add( item );
	}

	public void clear() {
		items.clear();
	}

	public int getSelectedIndex() {
		return selected;
	}

	public String getSelectedValue() {
		return ( selected == -1 ) ? null : items.get( selected );
	}

	public void removeItem( String item ) {
		int index = items.indexOf( item );
		if ( index > -1 ) {
			if ( index == selected ) {
				selected--;
			}
			items.remove( index );
		}
	}

	public void setSelectedIndex( int index ) {
		selected = index;

	}

	public void setSelectedValue( String value ) {
		selected = items.indexOf( value );
	}

	public void fireEvent( GwtEvent<?> event ) {
	}

	public HandlerRegistration addKeyUpHandler( KeyUpHandler handler ) {
		keyUpHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public ClickHandler getClickHandler() {
		return clickHandler;
	}

	public KeyUpHandler getKeyUpHandler() {
		return keyUpHandler;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

	public HandlerRegistration addClickHandler( ClickHandler handler ) {
		clickHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public List<String> getItems() {
		return items;
	}

}

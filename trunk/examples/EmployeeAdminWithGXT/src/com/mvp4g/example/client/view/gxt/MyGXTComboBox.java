package com.mvp4g.example.client.view.gxt;

import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.FieldEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyListBoxInterface;

public class MyGXTComboBox extends SimpleComboBox<String> implements MyListBoxInterface {

	public MyGXTComboBox( String fieldLabel ) {
		setFieldLabel( fieldLabel );
	}

	public void addItem( String item ) {
		add( item );
	}

	public void clear() {
		removeAll();
	}

	public String getSelectedValue() {
		return getSimpleValue();
	}

	public void removeItem( String item ) {
		remove( item );
	}

	public void setEnabled( boolean enabled ) {
		if ( enabled ) {
			enable();
		} else {
			disable();
		}
	}

	public void setSelectedIndex( int index ) {
		setValue( getStore().getAt( index ) );
	}

	public void setSelectedValue( String value ) {
		setSimpleValue( value );
	}

	public HandlerRegistration addClickHandler( final ClickHandler handler ) {
		addListener( Events.Select, new Listener<FieldEvent>() {

			public void handleEvent( FieldEvent be ) {
				handler.onClick( null );
			}

		} );
		return null;
	}

	public HandlerRegistration addKeyUpHandler( KeyUpHandler handler ) {
		return addHandler( handler, KeyUpEvent.getType() );
	}

}

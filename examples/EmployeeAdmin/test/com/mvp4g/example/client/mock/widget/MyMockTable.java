package com.mvp4g.example.client.mock.widget;

import java.util.Hashtable;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyTableInterface;

public class MyMockTable implements MyTableInterface {

	private ClickHandler clickHandler = null;

	private Map<Integer, Map<Integer, String>> table = new Hashtable<Integer, Map<Integer, String>>();
	private int lastSelected = -1;

	public int getRowCount() {
		return table.size();
	}

	public void removeRow( int row ) {
		table.remove( new Integer( row ) );
	}

	public void selectRow( int row ) {
		lastSelected = row;
	}

	public void setText( int row, int column, String text ) {
		Integer rowInt = new Integer( row );
		Integer colInt = new Integer( column );
		Map<Integer, String> rowMap = table.get( rowInt );
		if ( rowMap == null ) {
			rowMap = new Hashtable<Integer, String>();
			table.put( rowInt, rowMap );
		}
		rowMap.put( colInt, text );

	}

	public void unSelectRow( int row ) {
		if ( lastSelected == row ) {
			lastSelected = -1;
		}
	}

	public HandlerRegistration addClickHandler( ClickHandler handler ) {
		clickHandler = handler;

		return new HandlerRegistration() {
			public void removeHandler() {
			}
		};
	}

	public void fireEvent( GwtEvent<?> event ) {
	}

	public ClickHandler getClickHandler() {
		return clickHandler;
	}

	public int getRowForEvent( ClickEvent event ) {
		return 1;
	}

	public Map<Integer, String> getRow( int row ) {
		return table.get( new Integer( row ) );
	}

	public String getText( int row, int column ) {
		Map<Integer, String> rowMap = table.get( new Integer( row ) );
		return ( rowMap == null ) ? null : rowMap.get( new Integer( column ) );
	}

	public int getLastSelected() {
		return lastSelected;
	}

}

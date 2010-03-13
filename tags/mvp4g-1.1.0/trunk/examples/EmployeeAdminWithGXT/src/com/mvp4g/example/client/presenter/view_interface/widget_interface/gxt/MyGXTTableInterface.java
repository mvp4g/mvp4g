package com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;

public interface MyGXTTableInterface {

	public void addListener( EventType eventType, Listener<? extends BaseEvent> listener );

	public void selectRow( int row );

	public void unSelectRow( int row );

}

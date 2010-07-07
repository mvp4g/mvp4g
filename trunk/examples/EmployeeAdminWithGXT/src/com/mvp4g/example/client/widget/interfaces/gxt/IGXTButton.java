package com.mvp4g.example.client.widget.interfaces.gxt;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.EventType;
import com.extjs.gxt.ui.client.event.Listener;

public interface IGXTButton {

	public void addListener( EventType eventType, Listener<? extends BaseEvent> listener );

	public void setEnabled( boolean enabled );

	public void setVisible( boolean isVisible );

	public void setText( String text );
}

package com.mvp4g.example.client.presenter.view_interface.widget_interface;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface MyButtonInterface extends HasClickHandlers {

	public void setEnabled( boolean enabled );

	public void setVisible( boolean isVisible );

	public void setText( String text );
}

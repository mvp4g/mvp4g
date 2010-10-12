package com.mvp4g.client.gwt_event;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public interface HasLoadHandlers extends HasHandlers {
	
	HandlerRegistration addLoadHandler(LoadHandler handler);

}

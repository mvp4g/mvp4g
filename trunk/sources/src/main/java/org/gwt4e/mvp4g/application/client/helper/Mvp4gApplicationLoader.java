package org.gwt4e.mvp4g.application.client.helper;

import com.google.gwt.user.client.Command;
import com.mvp4g.client.event.EventBus;

// TODO documenation
public interface Mvp4gApplicationLoader<E extends EventBus> {

	/**
	 * Called before a module/code splitter is loaded. To start the code loading, the load command
	 * must be executed. This way, asynchronous action can be made.
	 * 
	 * @param eventBus
	 *            The event bus loading the code (ie in case of child module, the parent event bus).
	 * @param eventName
	 *            The name of the event that requested the module to be loaded, null if the request
	 *            come from a history token change.
	 * @param params
	 *            The objects fired with this event, null is no object are fired with the event or
	 *            if the request come from a history token change.
	 * @param load
	 *            The command to execute when the module needs to be loaded.
	 */
	void load(E eventBus);

}

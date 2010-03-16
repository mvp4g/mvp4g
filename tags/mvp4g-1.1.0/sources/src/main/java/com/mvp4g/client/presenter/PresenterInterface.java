/*
 * Copyright 2010 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;

/**
 * Interface that defines a presenter.<br/>
 * <br/>
 * This interface provides getter and setter for a view and an event bus.<br/>
 * <br/>
 * It is recommended to use directly <code>BasePresenter</code>.
 * 
 * @author plcoirier
 * 
 * @param <V>
 *            Type of the view injected into the presenter
 * @param <E>
 *            Type of the event bus used by the presenter.
 */
public interface PresenterInterface<V, E extends EventBus> {

	/**
	 * Set an event bus to the presenter
	 * 
	 * @param eventBus
	 *            event bus to set
	 */
	public void setEventBus( E eventBus );

	/**
	 * Get the view associated with the presenter
	 * 
	 * @return eventBus manipulated by the presenter.
	 */
	public E getEventBus();

	/**
	 * Sets the view associated with the presenter.
	 * 
	 * @param view
	 *            view to set
	 */
	public void setView( V view );

	/**
	 * Gets the view associated with the presenter.
	 * 
	 * @return view manipulated by the presenter.
	 */
	public V getView();

	/**
	 * Performs required bindings between this Presenter and its View.</p>
	 * 
	 * This is automatically invoked when the View is wired into the Presenter.
	 * 
	 */
	public void bind();

	/**
	 * Call the bind method is needed. Usually, the first time this method is called, the bind
	 * method should be called, the other time nothing should be done.
	 */
	public void bindIfNeeded();

}

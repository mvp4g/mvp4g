/*
 * Copyright 2009 Pierre-Laurent Coirier
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
 * Default implementation of <code>PresenterInterface</code>.<br/>
 * <br/>
 * This implementation has two attribute:
 * <ul>
 * <li>an event bus</li>
 * <li>a view</li>
 * </ul>
 * You should extend this class to create a presenter.<br/>
 * <br/>
 * 
 * @param <V>
 *            Class type of the view injected into the presenter
 * 
 * @author plcoirier
 * 
 */
public class BasePresenter<V, E extends EventBus> implements PresenterInterface<V, E> {

	private boolean binded = false;
	
	protected E eventBus = null;
	protected V view = null;

	public BasePresenter() {
	}

	public V getView() {
		return view;
	}

	public void setView( V view ) {
		this.view = view;
	}

	public void bind() {
		/*
		 * Default implementation does nothing: extensions are responsible for (optionally) defining
		 * binding specifics.
		 */
	}

	public void setEventBus( E eventBus ) {
		this.eventBus = eventBus;
	}

	public E getEventBus() {
		return eventBus;
	}

	public void bindIfNeeded() {
		if(!binded){
			bind();
			binded = true;
		}		
	}

}

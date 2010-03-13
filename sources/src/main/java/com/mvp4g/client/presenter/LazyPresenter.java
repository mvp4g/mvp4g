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
import com.mvp4g.client.view.LazyView;

/**
 * Special type of presenter that will be built only when it handles its first event. To do so, the
 * bind method (that is called only the first time a presenter needs to handle an event) is divided
 * in two:
 * <ul>
 * <li>createPresenter: method where presenter should be built (ie all its attributes should be
 * instantiated). By default, it does nothing.</li>
 * <li>bindView: method where required bindings between this Presenter and its View should be done.
 * By default it does nothing.</li>
 * </ul>
 * A <code>LazyPresenter</code> can only have a view which type is compatible with
 * <code>LazyView</code>. This type of presenter will automatically call the createView method of
 * the <code>LazyView</code> when its bind method is called.
 * 
 * @author plcoirier
 * 
 * @param <V>
 * 			Type of the view injected into the presenter. Must extends <code>LazyView</code>.
 * @param <E>
 * 			Type of the event bus used by the presenter.
 */
public class LazyPresenter<V extends LazyView, E extends EventBus> extends BasePresenter<V, E> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.presenter.BasePresenter#bind()
	 */
	//Method is final. If you need to overwrite this method, then you shouldn't extend LazyPresenter but BasePresenter.
	@Override
	final public void bind() {
		view.createView();
		createPresenter();
		bindView();
	}

	/**
	 * Bind the view to the presenter once both elements have been created.
	 */
	public void bindView() {

	}

	/**
	 * Called when presenter needs to be built.
	 */
	public void createPresenter() {

	}

}

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
 * Default implementation of a <code>PresenterInterface</code>.<br/>
 * <br/>
 * This implementation has one attribute:<ul>
 * <li>a default event bus that is protected
 * in order child classes to have access to it.</li>
 * </ul> 
 * You should extend this class to create a presenter.<br/>
 * 
 * @author plcoirier
 *
 */
public class Presenter implements PresenterInterface {

	protected EventBus eventBus = null;

	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}

}

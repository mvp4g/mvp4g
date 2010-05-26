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
package com.mvp4g.client.event;

import com.mvp4g.client.Mvp4gModule;

/**
 * Base implementation of the event bus. It should only be used by the framework.
 * 
 * @author plcoirier
 * 
 */
public class BaseEventBus implements EventBus {

	public static int logDepth = 0;

	private boolean historyStored = true;
	private boolean changeForNextOne = false;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#isHistoryStored()
	 */
	public boolean isHistoryStored() {
		return historyStored;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setHistoryStored(boolean)
	 */
	public void setHistoryStored( boolean historyStored ) {
		this.historyStored = historyStored;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setHistoryStoredForNextOne(boolean)
	 */
	public void setHistoryStoredForNextOne( boolean historyStored ) {
		if ( historyStored != this.historyStored ) {
			changeForNextOne = true;
			this.historyStored = historyStored;
		}
	}

	/**
	 * Interact with place service when needed thanks to the module
	 * 
	 * @param module
	 *            module that knows the place service
	 * @param type
	 *            type of the event to store
	 * @param form
	 *            object of the event to store
	 */
	protected void place( Mvp4gModule module, String type, String form ) {
		if ( historyStored ) {
			module.place( type, form );
		}
		if ( changeForNextOne ) {
			historyStored = !historyStored;
			changeForNextOne = false;
		}
	}

	/**
	 * Interact with place service to clear history when needed thanks to the module
	 * 
	 * @param module
	 *            module that knows the place service
	 */
	protected void clearHistory( Mvp4gModule module ) {
		if ( historyStored ) {
			module.clearHistory();
		}
		if ( changeForNextOne ) {
			historyStored = !historyStored;
			changeForNextOne = false;
		}
	}

}

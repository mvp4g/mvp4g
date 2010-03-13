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

/**
 * Interface that defines an event bus. All classes defining an event bus must implement it.
 * 
 * @author plcoirier
 * 
 */
public interface EventBus {

	/**
	 * Indicate for all events if they should be stored or not in browser history when possible (ie
	 * when associated with an history converter).
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	public void setHistoryStored( boolean historyStored );

	/**
	 * Indicate for next event that can be stored in history only if it should be stored or not in
	 * browser history.<br/>
	 * <br/>
	 * This method should be called only right before sending an event that could be stored in
	 * browser history.
	 * 
	 * @param historyStored
	 *            true if events should be stored
	 */
	public void setHistoryStoredForNextOne( boolean historyStored );

	/**
	 * Indicate if events are stored in browser history when possible (ie when associated with an
	 * history converter).
	 * 
	 * @return true if events can be stored in browse history
	 */
	public boolean isHistoryStored();
}

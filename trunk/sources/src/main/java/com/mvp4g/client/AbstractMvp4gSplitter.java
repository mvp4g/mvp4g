/*
 * Copyright 2011 Pierre-Laurent Coirier
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
package com.mvp4g.client;

import com.mvp4g.client.event.EventHandlerInterface;

/**
 * Base implementation used by the framework to generate Mvp4g Splitter.
 * 
 * @author plcoirier
 */
public abstract class AbstractMvp4gSplitter {

	@SuppressWarnings( "rawtypes" )
	protected EventHandlerInterface[] handlers;

	private boolean[] activated;

	/**
	 * Build a splitter for a certain number of handlers.
	 * 
	 * @param size
	 *            Number of handers managed by this splitter.
	 */
	protected AbstractMvp4gSplitter( int size ) {
		handlers = new EventHandlerInterface[size];
		activated = new boolean[size];
		for ( int i = 0; i < size; i++ ) {
			activated[i] = true;
		}
	}

	/**
	 * Return true if one of the handlers in the given indexes is activate and:
	 * <ul>
	 * <li>the event is not passive,</li>
	 * <li>the event is passive and one of the handlers is already instantiated.
	 * </ul>
	 * 
	 * @param passive
	 *            True if the event is passive.
	 * @param indexes
	 *            Indexes of the handler to check for activation.
	 * @return True if one of the handler in the given indexes is active.
	 */
	protected boolean isActivated( boolean passive, int[] indexes ) {
		boolean isActivated = false;
		for ( int i = 0; ( i < indexes.length ) && !isActivated; i++ ) {
			// don't handle a regular event if all presenter have been deactivated
			// for passive event, also check if at least one of the handler is already built
			isActivated = activated[indexes[i]] && ( !passive || ( handlers[indexes[i]] != null ) );
		}
		return isActivated;
	}

	/**
	 * Activate or deactivate the handlers with the given indexes. If the handler at the one of the
	 * given indexes is instantiated, also call the setActivated method of this handler.
	 * 
	 * @param isActivated
	 *            True to activate, false to deactivate.
	 * @param indexes
	 *            Indexes of the handlers to activate/deactivate.
	 */
	public void setActivated( boolean isActivated, int[] indexes ) {
		for ( int index : indexes ) {
			activated[index] = isActivated;
			if ( handlers[index] != null ) {
				handlers[index].setActivated( isActivated );
			}
		}
	}

}

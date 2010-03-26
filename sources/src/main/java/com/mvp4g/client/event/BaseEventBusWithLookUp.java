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
package com.mvp4g.client.event;

import com.mvp4g.client.Mvp4gException;

/**
 * Abstract implementation of <code>EventBusWithLookup</code>. This class should be used by the
 * framework and by the <code>XmlPresenter</code>.
 * 
 * @author plcoirier
 * 
 */
public abstract class BaseEventBusWithLookUp extends BaseEventBus implements EventBusWithLookup {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBusWithLookup#dispatch(java.lang.String, java.lang.Object)
	 */
	abstract public void dispatch( String eventType, Object... data);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBusWithLookup#dispatch(java.lang.String)
	 */
	public void dispatch( String eventType ) {
		dispatch( eventType, new Object[0] );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBusWithLookup#dispatch(java.lang.Enum, java.lang.Object)
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType, Object... data ) {
		this.dispatch( enumEventType.toString(), data );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBusWithLookup#dispatch(java.lang.Enum)
	 */
	public <E extends Enum<E>> void dispatch( Enum<E> enumEventType ) {
		this.dispatch( enumEventType.toString(), new Object[0] );
	}

	/**
	 * When a ClassCastException is thrown while executing dispatch method, verify if it is thrown
	 * by this class. If it's the case, then send an Mvp4gException to indicate that an object with
	 * the wrong class type has been sent with the event.
	 * 
	 * @param e
	 *            ClassCastException thrown
	 * @param eventType
	 *            event type dispatch while error is thrown
	 */
	protected void handleClassCastException( ClassCastException e, String eventType ) {
		if ( e.getStackTrace()[0].getClassName().equals( this.getClass().getName() ) ) {
			throw new Mvp4gException( "Class of the object sent with event " + eventType + " is incorrect." );
		}
		throw e;
	}

}

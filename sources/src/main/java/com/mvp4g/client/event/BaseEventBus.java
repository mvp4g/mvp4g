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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;

/**
 * Base implementation of the event bus. It should only be used by the framework.
 * 
 * @author plcoirier
 * 
 */
public abstract class BaseEventBus implements EventBus {

	public static int logDepth = -1;

	private boolean historyStored = true;
	private boolean changeHistoryStoredForNextOne = false;

	private boolean filteringEnabled = true;
	private boolean changeFilteringEnabledForNextOne = false;
	public boolean tokenMode = false;

	private Map<Class<?>, List<EventHandlerInterface<?>>> handlersMap = new HashMap<Class<?>, List<EventHandlerInterface<?>>>();

	private List<EventFilter<?>> filters = new ArrayList<EventFilter<? extends EventBus>>();

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
			changeHistoryStoredForNextOne = true;
			this.historyStored = historyStored;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#isFilterEnabled(boolean)
	 */
	public boolean isFilteringEnabled() {
		return filteringEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setFilterEnabled(boolean)
	 */
	public void setFilteringEnabled( boolean filteringEnabled ) {
		this.filteringEnabled = filteringEnabled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#setFilterEnabledForNextOne(boolean)
	 */
	public void setFilteringEnabledForNextOne( boolean filteringEnabled ) {
		if ( filteringEnabled != this.filteringEnabled ) {
			changeFilteringEnabledForNextOne = true;
			this.filteringEnabled = filteringEnabled;
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
	protected String place( Mvp4gModule module, String type, String form, boolean onlyToken ) {
		String token;
		if ( tokenMode ) {
			tokenMode = false;
			token = module.place( type, form, onlyToken );
		} else {
			token = ( historyStored ) ? module.place( type, form, onlyToken ) : null;
			resetHistoryStored();
		}
		return token;
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
		resetHistoryStored();
	}

	/**
	 * Change history stored flag value if needed
	 */
	private void resetHistoryStored() {
		if ( changeHistoryStoredForNextOne ) {
			historyStored = !historyStored;
			changeHistoryStoredForNextOne = false;
		}
	}

	/**
	 * If filtering is enabled, executes event filters associated with this event bus.
	 * 
	 * @param eventType
	 *            name of the event to filter
	 * @param params
	 *            event parameters for this event
	 */
	protected boolean filterEvent( String eventType, Object... params ) {
		boolean ret = true;
		if ( filteringEnabled ) {
			ret = doFilterEvent( eventType, params );
		}
		if ( changeFilteringEnabledForNextOne ) {
			filteringEnabled = !filteringEnabled;
			changeFilteringEnabledForNextOne = false;
		}
		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#addEventFilter(com.mvp4g.client.event.EventFilter)
	 */
	public void addEventFilter( EventFilter<? extends EventBus> filter ) {
		filters.add( filter );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#removeEventFilter(com.mvp4g.client.event.EventFilter)
	 */
	public void removeEventFilter( EventFilter<? extends EventBus> filter ) {
		filters.remove( filter );
	}

	/**
	 * Performs the actual filtering by calling each associated event filter in turn. If any event
	 * filter returns false, then the event will be canceled.
	 * 
	 * @param eventType
	 *            name of the event to filter
	 * @param params
	 *            event parameters for this event
	 */
	@SuppressWarnings( "unchecked" )
	private boolean doFilterEvent( String eventType, Object[] params ) {
		int filterCount = filters.size();
		EventFilter filter;
		for ( int i = 0; i < filterCount; i++ ) {
			filter = filters.get( i );
			if ( !filter.filterEvent( eventType, params, this ) ) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#addHandler(java.lang.Class, boolean)
	 */
	public <T extends EventHandlerInterface<?>> T addHandler( Class<T> handlerClass, boolean bind ) throws Mvp4gException {
		T handler = createHandler( handlerClass );
		if ( handler == null ) {
			throw new Mvp4gException(
					"Handler with type "
							+ handlerClass.getName()
							+ " couldn't be created by the Mvp4g. Have you forgotten to set multiple attribute to true for this handler or are you trying to create an handler that belongs to another module (another type of event bus injected in this handler)?" );
		}

		if ( bind ) {
			handler.isActivated();
		}

		List<EventHandlerInterface<?>> handlers = handlersMap.get( handlerClass );
		if ( handlers == null ) {
			handlers = new ArrayList<EventHandlerInterface<?>>();
			handlersMap.put( handlerClass, handlers );
		}
		handlers.add( handler );
		return handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.event.EventBus#addHandler(java.lang.Class, boolean)
	 */
	public <T extends EventHandlerInterface<?>> T addHandler( Class<T> handlerClass ) throws Mvp4gException {
		return addHandler( handlerClass, true );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mvp4g.client.event.EventBus#removeHandler(com.mvp4g.client.event.EventHandlerInterface)
	 */
	public <T extends EventHandlerInterface<?>> void removeHandler( T handler ) {
		List<EventHandlerInterface<?>> handlers = handlersMap.get( handler.getClass() );
		if ( handlers != null ) {
			handlers.remove( handler );
		}
	}

	/**
	 * Returns the list of handlers with the given class
	 * 
	 * @param <T>
	 *            type of the handlers
	 * @param handlerClass
	 *            class of the handlers
	 * @return list of handlers
	 */
	@SuppressWarnings( "unchecked" )
	protected <T extends EventHandlerInterface<?>> List<T> getHandlers( Class<T> handlerClass ) {
		return (List<T>)handlersMap.get( handlerClass );
	}

	/**
	 * Create a new instance of the given handler class.
	 * 
	 * @param <T>
	 *            type of the handler
	 * @param handlerClass
	 *            class of the handler
	 * @return new instance created
	 */
	abstract protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass );

}

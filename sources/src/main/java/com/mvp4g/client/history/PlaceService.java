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
package com.mvp4g.client.history;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;

/**
 * Place Service defines the connection between the application and the browser history.<br/>
 * <br/>
 * When an event needs to be stored in the history, the place method of <code>PlaceService</code> is
 * called. This method will transform the event to an history token the following way:<br/>
 * <br/>
 * <b>myurl#eventName?params</b><br/>
 * <br/>
 * where params is the string returned by the handling method of the history converter for the
 * event.<br/>
 * If params is null, then the URL will be:<br/>
 * <br/>
 * <b>myurl#eventName</b><br/>
 * <br/>
 * In case an event of a child module is stored, its history name and history names of all its
 * ascendant except the Root Module will be stored in the token:<br/>
 * <br/>
 * <b>myurl#childModule/subChildModule/eventName?params</b><br/>
 * <br/>
 * By default "?" is used to seperate the event name from the parameters. You can change it thanks
 * to <code>@HistoryConfiguration</code>.<br/>
 * <br/>
 * If the token generated is supposed to be crawlable, then a "!" will be added before the token.
 * 
 * 
 * 
 * @author plcoirier
 * 
 */
public abstract class PlaceService implements ValueChangeHandler<String> {

	public static final String MODULE_SEPARATOR = "/";

	public static final String CRAWLABLE = "!";

	private HistoryProxy history = null;
	private Mvp4gModule module = null;

	@SuppressWarnings( "rawtypes" )
	private Map<String, HistoryConverter> converters = new HashMap<String, HistoryConverter>();
	
	private boolean enabled = true;

	private NavigationConfirmationInterface navigationConfirmation;

	/**
	 * Build a <code>PlaceService</code>.
	 * 
	 */
	public PlaceService() {
		this( DefaultHistoryProxy.INSTANCE );
	}

	/**
	 * Build a <code>PlaceService</code> and inject an <code>HistoryProxy</code> instance.<br/>
	 * <br/>
	 * This constructor is handy when you want to test <code>PlaceService</code> without using the
	 * GWT History class.<br/>
	 * It shouldn't be called otherwise.
	 * 
	 * 
	 * @param history
	 *            history proxy to inject
	 */
	protected PlaceService( HistoryProxy history ) {
		this.history = history;
		history.addValueChangeHandler( this );
	}

	/**
	 * Called when the History token has changed.<br/>
	 * <br/>
	 * Decode the history token and call the convertFromToken method of the history converters
	 * associated with this action stored in the token.<br/>
	 * <br/>
	 * If token is equal to empty string, ask the event bus to dispatch an initEvent.
	 * 
	 * @param event
	 *            event containing the new history token
	 * 
	 */
	public void onValueChange( final ValueChangeEvent<String> event ) {

		confirmEvent( new NavigationEventCommand( module.getEventBus() ) {

			protected void execute() {
				convertToken( event.getValue() );
			}

		} );

	}

	/**
	 * Convert the token to an event
	 * 
	 * @param token
	 *            the token to convert
	 */
	protected void convertToken( String token ) {
		boolean toContinue = false;
		if ( token != null ) {
			if ( token.startsWith( CRAWLABLE ) ) {
				token = token.substring( 1 );
			}
			toContinue = ( token.length() > 0 );
		}

		if ( toContinue ) {
			String[] result = parseToken( token );
			if ( !forwardToChildModuleIfNeeded( result[0], result[1] ) ) {
				dispatchEvent( result[0], result[1], module );
			}
		} else {
			sendInitEvent();
		}
	}

	/**
	 * Parse the token and return a string array. The first element of this array contains the event
	 * name whereas the second element contains the parameters associated to the event.
	 * 
	 * @param token
	 *            token to parse
	 * @return array of string
	 */
	protected String[] parseToken( String token ) {
		String[] result = new String[2];
		int index = token.lastIndexOf( getParamSeparator() );
		result[0] = ( index == -1 ) ? token : token.substring( 0, index );
		result[1] = ( index == -1 ) ? null : token.substring( index + 1 );
		return result;
	}

	/**
	 * Check if this event is a child's module event. If it's the case, forward the token to the child module and return true.
	 *
	 * @param eventName
	 * 			name of the event that was stored in the token
	 * @param param
	 * 			parameters stored in the token
	 * @return
	 * 		true if this child module's event.
	 */
	protected boolean forwardToChildModuleIfNeeded( final String eventName, final String param ) {
		boolean forAChild = eventName.contains( MODULE_SEPARATOR );
		if ( forAChild ) {
			Mvp4gEventPasser passer = new Mvp4gEventPasser( true ) {

				@Override
				public void pass( Mvp4gModule module ) {
					if ( (Boolean)eventObjects[0] ) {
						dispatchEvent( eventName, param, module );
					} else {
						sendNotFoundEvent();
					}
				}
			};
			module.dispatchHistoryEvent( eventName, passer );
		}
		return forAChild;
	}

	/**
	 * Dispatch the event thanks to the history converter.
	 * 
	 * @param historyName
	 * 			name of the event stored in the token
	 * @param param
	 * 			parameters stored in the token			
	 * @param module
	 * 			module to which belongs the event
	 */
	@SuppressWarnings( "unchecked" )
	protected void dispatchEvent( String historyName, String param, Mvp4gModule module ) {
		if ( historyName != null ) {
			@SuppressWarnings( "rawtypes" )
			HistoryConverter converter = converters.get( historyName );
			if ( converter == null ) {
				sendNotFoundEvent();
			} else {
				String[] tab = historyName.split( MODULE_SEPARATOR );
				String finalEventName = tab[tab.length - 1];
				converter.convertFromToken( finalEventName, param, module.getEventBus() );
			}
		} else {
			sendNotFoundEvent();
		}
	}

	/**
	 * Convert an event and its associated parameters to a token.<br/>
	 * 
	 * @param eventName
	 *            name of the event to store
	 * @param param
	 *            string representation of the objects associated with the event that needs to be
	 *            stored in the token
	 * @param onlyToken
	 *            if true, only the token will be generated and browser history won't change
	 * @return the generated token
	 */
	public String place( String eventName, String param, boolean onlyToken ) {
		
		if ( !enabled && !onlyToken ) {
			return null;
		}

		String token = tokenize( eventName, param);
		
		if ( converters.get( eventName ).isCrawlable() ) {
			token = CRAWLABLE + token;
		}
		if ( !onlyToken ) {
			history.newItem( token, false );
		}
		return token;
	}
	
	/**
	 * Transform an event and its parameters to a token
	 * 
	 * @param eventName
	 * 			event's name
	 * @param param
	 * 			event's parameters
	 * @return
	 * 			token to store in the history
	 */
	public String tokenize(String eventName, String param){
		String token = eventName;
		if ( ( param != null ) && ( param.length() > 0 ) ) {
			token = token + getParamSeparator() + param;
		}
		return token;
	}

	/**
	 * Clear the history token stored in the browse history url by adding a new empty token
	 */
	public void clearHistory() {
		history.newItem( "", false );
	}

	/**
	 * Add a converter for an event.
	 * 
	 * @param historyName
	 *            name of the event to store in the token
	 * @param converter
	 *            converter associated with this event
	 */
	@SuppressWarnings( "rawtypes" )
	public void addConverter( String historyName, HistoryConverter converter ) {
		converters.put( historyName, converter );
	}

	/**
	 * @param module
	 *            the module to set
	 */
	public void setModule( Mvp4gModule module ) {
		this.module = module;
	}

	/**
	 * @param navigationConfirmation
	 *            the navigationConfirmation to set
	 */
	public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {
		this.navigationConfirmation = navigationConfirmation;
	}

	/**
	 * Ask for user's confirmation before firing an event
	 * 
	 * @param event
	 *            event to confirm
	 */
	public void confirmEvent( NavigationEventCommand event ) {
		if ( navigationConfirmation == null ) {
			//no need to remove the confirmation, there is none
			event.fireEvent( false );
		} else {
			navigationConfirmation.confirm( event );
		}
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled( boolean enabled ) {
		this.enabled = enabled;
	}

	/**
	 * @return
	 * 		separator used to differenciate the event's name and its parameters
	 */
	protected String getParamSeparator() {
		return "?";
	}

	/**
	 * Call when token retrieved is null or equals to empty string
	 * 
	 * Don't implement this method, the framework will.
	 */
	abstract protected void sendInitEvent();

	/**
	 * Call when token retrieved doesn't correspond to an event
	 * 
	 * Don't implement this method, the framework will.
	 */
	abstract protected void sendNotFoundEvent();

}

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
import com.google.gwt.user.client.History;
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
 * <b>myurl#childModule/subChildModule/eventType?params</b><br/>
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

	public static final String DEFAULT_SEPARATOR = "?";

	/**
	 * Interface to define methods needed to manage history<br/>
	 * <br/>
	 * This interface is needed in order to test the PlaceService without using GWT History class.<br/>
	 * GWT History class can't be used with JUnit test.
	 * 
	 * @author plcoirier
	 * 
	 */
	public interface HistoryProxy {

		public void addValueChangeHandler( ValueChangeHandler<String> handler );

		public void newItem( String historyToken, boolean issueEvent );

	}

	private HistoryProxy history = null;
	private Mvp4gModule module = null;

	@SuppressWarnings( "unchecked" )
	private Map<String, HistoryConverter> converters = new HashMap<String, HistoryConverter>();
	private Map<String, String> toHistoryNames = new HashMap<String, String>();
	private Map<String, String> toEventType = new HashMap<String, String>();

	private String paramSeparator;
	private boolean alwaysAdded;

	private NavigationConfirmationInterface navigationConfirmation;

	/**
	 * Build a <code>PlaceService</code>.
	 * 
	 */
	public PlaceService( String paramSeparator, boolean alwaysAdded ) {
		this( new HistoryProxy() {

			public void addValueChangeHandler( ValueChangeHandler<String> handler ) {
				History.addValueChangeHandler( handler );
			}

			public void newItem( String historyToken, boolean issueEvent ) {
				History.newItem( historyToken, issueEvent );
			}

		}, paramSeparator, alwaysAdded );
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
	protected PlaceService( HistoryProxy history, String paramSeparator, boolean alwaysAdded ) {
		this.history = history;
		history.addValueChangeHandler( this );
		this.paramSeparator = paramSeparator;
		this.alwaysAdded = alwaysAdded;
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
				String token = event.getValue();

				boolean toContinue = false;
				if ( token != null ) {
					if ( token.startsWith( CRAWLABLE ) ) {
						token = token.substring( 1 );
					}
					toContinue = ( token.length() > 0 );
				}

				if ( toContinue ) {

					int index = token.lastIndexOf( paramSeparator );
					final String eventType = ( index == -1 ) ? token : token.substring( 0, index );
					final String param = ( index == -1 ) ? null : token.substring( index + 1 );
					if ( eventType.contains( MODULE_SEPARATOR ) ) {
						Mvp4gEventPasser passer = new Mvp4gEventPasser( true ) {

							@Override
							public void pass( Mvp4gModule module ) {
								if ( (Boolean)eventObjects[0] ) {
									dispatchEvent( eventType, param, module );
								} else {
									sendNotFoundEvent();
								}
							}
						};
						module.dispatchHistoryEvent( eventType, passer );
					} else {
						dispatchEvent( eventType, param, module );
					}
				} else {
					sendInitEvent();
				}
			}

		} );

	}

	@SuppressWarnings( "unchecked" )
	private void dispatchEvent( String historyName, String param, Mvp4gModule module ) {
		String eventType = toEventType.get( historyName );
		if ( eventType != null ) {
			HistoryConverter converter = converters.get( eventType );
			if ( converter == null ) {
				sendNotFoundEvent();
			} else {
				String[] tab = eventType.split( MODULE_SEPARATOR );
				String finalEventType = tab[tab.length - 1];
				converter.convertFromToken( finalEventType, param, module.getEventBus() );
			}
		} else {
			sendNotFoundEvent();
		}
	}

	/**
	 * Convert an event and its associated parameters to a token.<br/>
	 * 
	 * @param eventType
	 *            type of the event to store
	 * @param param
	 *            string representation of the objects associated with the event that needs to be
	 *            stored in the token
	 */
	@SuppressWarnings( "unchecked" )
	public void place( String eventType, String param ) {
		String historyName = toHistoryNames.get( eventType );
		String token;
		if ( ( param == null ) || ( param.length() == 0 ) ) {
			if ( alwaysAdded ) {
				token = historyName + paramSeparator;
			} else {
				token = historyName;
			}
		} else {
			token = historyName + paramSeparator + param;
		}
		HistoryConverter hc = converters.get( eventType );
		if ( hc.isCrawlable() ) {
			token = CRAWLABLE + token;
		}
		history.newItem( token, false );
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
	 * @param eventType
	 *            type of the event
	 * @param historyName
	 *            name of the event to store in the token
	 * @param converter
	 *            converter associated with this event
	 */
	@SuppressWarnings( "unchecked" )
	public void addConverter( String eventType, String historyName, HistoryConverter converter ) {
		converters.put( eventType, converter );
		toHistoryNames.put( eventType, historyName );
		toEventType.put( historyName, eventType );
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
	 * Call when token retrieved is null or equals to empty string
	 */
	abstract protected void sendInitEvent();

	/**
	 * Call when token retrieved doesn't correspond to an event
	 */
	abstract protected void sendNotFoundEvent();

}

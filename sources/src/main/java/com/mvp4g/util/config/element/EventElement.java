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
package com.mvp4g.util.config.element;

import java.util.ArrayList;
import java.util.List;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g Event configuration element.</p>
 * 
 * @author javier
 */
public class EventElement extends Mvp4gElement {

	private List<String> activate, deactivate, handlers, modulesToLoad;

	public EventElement() {
		super( "event" );
	}

	@Override
	public void setValues( String name, String[] values ) throws DuplicatePropertyNameException {
		if ( "activate".equals( name ) ) {
			activate = fillList( activate, values, "activate" );
		} else if ( "deactivate".equals( name ) ) {
			deactivate = fillList( deactivate, values, "deactivate" );
		} else if ( "handlers".equals( name ) ) {
			handlers = fillList( handlers, values, "handlers" );
		} else if ( "modulesToLoad".equals( name ) ) {
			modulesToLoad = fillList( modulesToLoad, values, "modulesToLoad" );
		} else {
			super.setValues( name, values );
		}
	}

	public void setType( String type ) throws DuplicatePropertyNameException {
		setProperty( "type", type );
	}

	public String getType() {
		return getProperty( "type" );
	}

	public void setCalledMethod( String calledMethod ) throws DuplicatePropertyNameException {
		setProperty( "calledMethod", calledMethod );
	}

	public String getCalledMethod() {

		String calledMethod = getProperty( "calledMethod" );
		if ( ( calledMethod == null ) || ( calledMethod.length() == 0 ) ) {
			String type = getType();
			if ( type.length() > 1 ) {
				type = type.substring( 0, 1 ).toUpperCase() + type.substring( 1 );
			} else {
				type = type.toUpperCase();
			}
			calledMethod = "on" + type;
		}

		return calledMethod;
	}

	public void setEventObjectClass( String[] eventObjectClasses ) throws DuplicatePropertyNameException {
		setValues( "eventObjectClass", eventObjectClasses );
	}

	public String[] getEventObjectClass() {
		return getValues( "eventObjectClass" );
	}

	public void setHandlers( String[] handlers ) throws DuplicatePropertyNameException {
		setValues( "handlers", handlers );
	}

	public List<String> getHandlers() {
		return handlers;
	}

	public void setModulesToLoad( String[] modules ) throws DuplicatePropertyNameException {
		setValues( "modulesToLoad", modules );
	}

	public List<String> getModulesToLoad() {
		return modulesToLoad;
	}

	public void setHistory( String history ) throws DuplicatePropertyNameException {
		setProperty( "history", history );
	}

	public String getHistory() {
		return getProperty( "history" );
	}

	public boolean hasHistory() {
		return getHistory() != null;
	}

	public String getForwardToParent() {
		return getProperty( "forwardToParent" );
	}

	public void setForwardToParent( String forwardToParent ) throws DuplicatePropertyNameException {
		setProperty( "forwardToParent", forwardToParent );
	}

	public boolean hasForwardToParent() {
		return Boolean.TRUE.toString().equalsIgnoreCase( getProperty( "forwardToParent" ) );
	}

	@Override
	public String toString() {
		return "[" + getType() + "]";
	}

	@Override
	public String getUniqueIdentifierName() {
		return "type";
	}

	public List<String> getActivate() {
		return activate;
	}

	public void setActivate( String[] presenters ) throws DuplicatePropertyNameException {
		setValues( "activate", presenters );
	}

	public List<String> getDeactivate() {
		return deactivate;
	}

	public void setDeactivate( String[] presenters ) throws DuplicatePropertyNameException {
		setValues( "deactivate", presenters );
	}

	public String getName() {
		String name = getProperty( "name" );
		if ( name == null ) {
			name = getType();
		}
		return name;
	}

	public void setName( String name ) throws DuplicatePropertyNameException {
		setProperty( "name", name );
	}

	public String getNavigationEvent() {
		return getProperty( "navigationEvent" );
	}

	public boolean isNavigationEvent() {
		return Boolean.TRUE.toString().equalsIgnoreCase( getNavigationEvent() );
	}

	public void setNavigationEvent( String navigationEvent ) throws DuplicatePropertyNameException {
		setProperty( "navigationEvent", navigationEvent );
	}

	public String getWithTokenGeneration() {
		return getProperty( "withTokenGeneration" );
	}

	public boolean isWithTokenGeneration() {
		return Boolean.TRUE.toString().equalsIgnoreCase( getWithTokenGeneration() );
	}

	public void setWithTokenGeneration( String withTokenGeneration ) throws DuplicatePropertyNameException {
		setProperty( "withTokenGeneration", withTokenGeneration );
	}

	public String getTokenGenerationFromParent() {
		return getProperty( "tokenGenerationFromParent" );
	}

	public boolean isTokenGenerationFromParent() {
		return Boolean.TRUE.toString().equalsIgnoreCase( getTokenGenerationFromParent() );
	}

	public void setTokenGenerationFromParent( String tokenGenerationFromParent ) throws DuplicatePropertyNameException {
		setProperty( "tokenGenerationFromParent", tokenGenerationFromParent );
	}

	public String getPassive() {
		return getProperty( "passive" );
	}

	public boolean isPassive() {
		return Boolean.TRUE.toString().equalsIgnoreCase( getPassive() );
	}

	public void setPassive( String passive ) throws DuplicatePropertyNameException {
		setProperty( "passive", passive );
	}

	public void setBroadcastTo( String broadcastTo ) throws DuplicatePropertyNameException {
		setProperty( "broadcastTo", broadcastTo );
	}

	public String getBroadcastTo() {
		return getProperty( "broadcastTo" );
	}

	private List<String> fillList( List<String> previousList, String[] values, String propertyName ) throws DuplicatePropertyNameException {
		if ( previousList != null ) {
			throw new DuplicatePropertyNameException( propertyName );
		}
		List<String> newList = new ArrayList<String>();
		for ( String value : values ) {
			newList.add( value );
		}
		return newList;
	}

}
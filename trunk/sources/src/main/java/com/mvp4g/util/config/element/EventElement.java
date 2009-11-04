package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g Event configuration element.</p>
 * 
 * @author javier
 */
public class EventElement extends Mvp4gElement {

	public EventElement() {
		super( "event" );
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

	public void setEventObjectClass( String eventObjectClass ) throws DuplicatePropertyNameException {
		setProperty( "eventObjectClass", eventObjectClass );
	}

	public String getEventObjectClass() {
		return getProperty( "eventObjectClass" );
	}

	public void setHandlers( String[] handlers ) throws DuplicatePropertyNameException {
		setValues( "handlers", handlers );
	}

	public String[] getHandlers() {
		return getValues( "handlers" );
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

	@Override
	public String toString() {
		return "[" + getType() + "]";
	}

	@Override
	public String getUniqueIdentifierName() {
		return "type";
	}
}

package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.DuplicatePropertyNameException;

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
		return getProperty( "calledMethod" );
	}

	public void setEventObjectClass( String eventObjectClass ) throws DuplicatePropertyNameException {
		setProperty( "eventObjectClass", eventObjectClass );
	}

	public String getEventObjectClass() {
		String value = getProperty( "eventObjectClass" );
		if ( value == null ) {
			value = Object.class.getName();
		}
		return value;
	}

	public String getEventParameterString() {
		String value = getProperty( "eventObjectClass" );
		if ( value == null ) {
			return "();";
		}
		return "(form);";
	}

	public void setHandlers( String[] handlers ) throws DuplicatePropertyNameException {
		setValues( "handlers", handlers );
	}

	public String[] getHandlers() {
		return getValues( "handlers" );
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

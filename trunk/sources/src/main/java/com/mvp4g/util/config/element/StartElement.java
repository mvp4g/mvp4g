/**
 * 
 */
package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g Start configuration element.</p>
 * 
 * A <i>start</i> element is composed of two attributes:
 * <ol>
 * <li/><i>view</i>: a required attribute specifying which view to render upon application startup;
 * <li/><i>eventType</i>: an optional attribute specifying the event to be broadcasted upon
 * application startup.
 * </ol>
 * 
 * Because every GWT application always needs to render a view at startup, this element is required.
 * 
 * @author javier
 * 
 */
public class StartElement extends Mvp4gElement {

	private static final String START_ELEMENT_ID = StartElement.class.getName();

	public StartElement() {
		super( "start" );
	}

	@Override
	public String getUniqueIdentifierName() {
		// this element does not have a user-specified identifier: use a global label
		return START_ELEMENT_ID;
	}

	public void setView( String view ) throws DuplicatePropertyNameException {
		setProperty( "view", view );
	}

	public String getView() {
		return getProperty( "view" );
	}

	public void setEventType( String eventType ) throws DuplicatePropertyNameException {
		setProperty( "eventType", eventType );
	}

	public String getEventType() {
		return getProperty( "eventType" );
	}

	public boolean hasEventType() {
		return getEventType() != null;
	}

	public void setHistory( String history ) throws DuplicatePropertyNameException {
		setProperty( "history", history );
	}

	public String getHistory() {
		return getProperty( "history" );
	}

	public boolean hasHistory() {
		return "true".equalsIgnoreCase( getHistory() );
	}

}

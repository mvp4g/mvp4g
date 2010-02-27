/**
 * 
 */
package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g History configuration element.</p>
 * 
 * A <i>history</i> element is composed of two attributes:
 * <ol>
 * <li/><i>package</i>: this element is optional and used by the child tag
 * <li/><i>initEvent</i>: a required attribute that represents the event to send when token fired by
 * history is empty (for example when you go back to the first page).
 * </ol>
 * 
 * This element is optional because not all GWT applications have to manage history.
 * 
 * @author plcoirier
 * 
 */
public class HistoryElement extends Mvp4gElement {

	private static final String HISTORY_ELEMENT_ID = HistoryElement.class.getName();

	public HistoryElement() {
		super( "history" );
	}

	@Override
	public String getUniqueIdentifierName() {
		// this element does not have a user-specified identifier: use a global label
		return HISTORY_ELEMENT_ID;
	}

	public void setInitEvent( String initEvent ) throws DuplicatePropertyNameException {
		setProperty( "initEvent", initEvent );
	}

	public String getInitEvent() {
		return getProperty( "initEvent" );
	}

	public void setNotFoundEvent( String notFoundEvent ) throws DuplicatePropertyNameException {
		setProperty( "notFoundEvent", notFoundEvent );
	}

	public String getNotFoundEvent() {
		String event = getProperty( "notFoundEvent" );
		if ( event == null ) {
			event = getInitEvent();
		}
		return event;
	}

}

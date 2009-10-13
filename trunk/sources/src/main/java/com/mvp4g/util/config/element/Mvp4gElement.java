/**
 * 
 */
package com.mvp4g.util.config.element;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.mvp4g.util.exception.DuplicatePropertyNameException;

/**
 * A representation of an Mvp4g configuration element.
 * <p/>
 * 
 * This is the base class from which all Mvp4g configuration elements derive.
 * 
 * @author javier
 * 
 */
public abstract class Mvp4gElement {

	private String tagName;
	private Properties properties = new Properties();
	private Map<String, String[]> multiValueProperties = new HashMap<String, String[]>();

	public Mvp4gElement( String tagName ) {
		this.tagName = tagName;
	}

	/**
	 * Returns the name of the property that uniquely identifies this element.
	 * 
	 * The value associated with the property name returned by this method must be globally unique
	 * across the entire mvp4g configuration.
	 * 
	 * @return attribute name used as unique identifier.
	 */
	public abstract String getUniqueIdentifierName();

	/**
	 * Returns the value uniquely identifying this element in the configuration.
	 * 
	 * @return if defined, a value uniquely identifying the element, if not defined, an empty
	 *         string.
	 */
	public String getUniqueIdentifier() {
		String uid = properties.getProperty( getUniqueIdentifierName() );
		return uid == null ? "" : uid;
	}

	public String getProperty( String name ) {
		return properties.getProperty( name );
	}

	public void setProperty( String name, String value ) throws DuplicatePropertyNameException {

		if ( name != null && value != null ) {
			failIfPropertyPresent( name );
			properties.setProperty( name, value );
		}
	}

	/**
	 * Returns the name of the XML tag used to represent this element in the Mvp4g configuration
	 * file.
	 * 
	 * @return name of the Mvp4gElement tag.
	 */
	public String getTagName() {
		return tagName;
	}

	public String[] getValues( String name ) {
		String[] values = multiValueProperties.get( name );
		return values != null ? values : new String[] {};
	}

	public void setValues( String name, String[] values ) {

		if ( name != null && values != null ) {
			failIfMultiValuePropertyPresent( name );
			
			//if values is an array containing only ""
			if(values.length == 1 && ((values[0] == null) || (values[0].length() == 0))){
				values = new String[]{};
			}
			
			multiValueProperties.put( name, values );
		}
	}

	private void failIfPropertyPresent( String name ) throws DuplicatePropertyNameException {
		if ( properties.containsKey( name ) ) {
			throw new DuplicatePropertyNameException( tagName, name );
		}
	}

	private void failIfMultiValuePropertyPresent( String name ) throws DuplicatePropertyNameException {
		if ( multiValueProperties.containsKey( name ) ) {
			throw new DuplicatePropertyNameException( tagName, name );
		}
	}

	public int totalProperties() {
		return properties.size();
	}

	public int totalMultiValues() {
		return multiValueProperties.size();
	}

	@Override
	public boolean equals( Object another ) {
		if ( this.getClass().isInstance( another ) ) {
			Mvp4gElement that = (Mvp4gElement)another;
			return this.getUniqueIdentifier().equals( that.getUniqueIdentifier() );
		}
		return false;
	}

	@Override
	public int hashCode() {
		return getUniqueIdentifier().hashCode();
	}
}

/**
 * 
 */
package com.mvp4g.util.config.loader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import com.mvp4g.util.config.element.Mvp4gElement;
import com.mvp4g.util.exception.InvalidMvp4gConfigurationException;

/**
 * A class responsible for loading and validating an Mvp4g configuration element.</p>
 * 
 * This is the base class from which all Mvp4g configuration loaders derive.
 * 
 * @author javier
 * 
 */
abstract class Mvp4gElementLoader<E extends Mvp4gElement> {

	List<HierarchicalConfiguration> elements = new ArrayList<HierarchicalConfiguration>();

	Mvp4gElementLoader( List<HierarchicalConfiguration> elements ) {
		if ( elements != null ) {
			this.elements = elements;
		}
	}

	/**
	 * Specifies a user-friendly identifier for the type of element being loaded.
	 * 
	 * @return a String representation of the element to be used in error messages.
	 */
	abstract String getElementLabel();

	/**
	 * Specifies the list of attribute names expected for each element being loaded.
	 * 
	 * @return names of all required attributes for the element type.
	 */
	abstract String[] getRequiredAttributeNames();

	/**
	 * Creates a new instance of a concrete Mvp4gElement subclass.
	 */
	protected abstract E newElement();

	/**
	 * Specifies a list of attributes that may be optionally present.
	 * 
	 * @return names of optional attributes.
	 */
	String[] getOptionalAttributeNames() {
		return new String[] {};
	}

	/**
	 * Specifies a list of attribute names that contain multiple, comma-separated values.</p>
	 * 
	 * If a name is specified, the attribute is considered required.
	 * 
	 * @return names of multi-value attributes.
	 */
	String[] getMultiValueAttributeNames() {
		return new String[] {};
	}

	/**
	 * Specifies a list of optional attribute names that contain multiple, comma-separated
	 * values.</p>
	 * 
	 * @return names of all optional multi-value attributes.
	 */
	String[] getOptionalMultiValueAttributeNames() {
		return new String[] {};
	}

	/**
	 * Specifies a list of attribute names included in the parent tag of this element.</p>
	 * 
	 * Parent attributes are considered optional: absence of their values do not generate an error.
	 * 
	 * @return names of attributes in parent tag.
	 */
	String[] getParentAttributeNames() {
		return new String[] {};
	}

	/**
	 * Loads a particular set of Mvp4g elements.
	 * 
	 * @return a subset of Mvp4g elements found in the configuration file.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if
	 *             <p/>
	 *             <ul>
	 *             <li/>no elements are found;
	 *             <li/>elements with duplicate identifiers are found;
	 *             <li/>one of the required attribute names is missing.
	 *             </ul>
	 * 
	 */
	public Set<E> loadElements() throws InvalidMvp4gConfigurationException {

		checkForNonEmptyElements();

		return loadExistingElements();
	}

	/**
	 * Loads a particular set of Mvp4g elements if present in the configuration.
	 * 
	 * @return a subset of Mvp4g elements found in the configuration file or an empty set if no
	 *         elements were found.
	 * 
	 * @throws InvalidMvp4gConfigurationException
	 *             if
	 *             <p/>
	 *             <ul>
	 *             <li/>elements with duplicate identifiers are found;
	 *             <li/>one of the required attribute names is missing.
	 *             </ul>
	 * 
	 */
	protected Set<E> loadExistingElements() throws InvalidMvp4gConfigurationException {
		Set<E> loadedElements = new HashSet<E>();

		for ( HierarchicalConfiguration xmlElement : elements ) {

			E newElement = createNewElementFrom( xmlElement );

			checkForDuplicates( loadedElements, newElement );

			loadedElements.add( newElement );
		}
		return loadedElements;
	}

	private E createNewElementFrom( HierarchicalConfiguration xmlConfig ) {
		E element = newElement();
		addParentAttributes( xmlConfig, element );
		addRequiredAttributes( xmlConfig, element );
		addOptionalAttributes( xmlConfig, element );
		addMultiValueAttributes( xmlConfig, element );
		addOptionalMultiValueAttributes( xmlConfig, element );
		return element;
	}

	private void addRequiredAttributes( HierarchicalConfiguration xmlConfig, E element ) {
		for ( String attributeName : getRequiredAttributeNames() ) {
			String attributeValue = getAttribute( xmlConfig, attributeName );
			element.setProperty( attributeName, attributeValue );
		}
	}

	private void addOptionalAttributes( HierarchicalConfiguration xmlConfig, E element ) {
		for ( String attributeName : getOptionalAttributeNames() ) {
			String attributeValue = xmlConfig.getString( "[@" + attributeName + "]" );
			if ( attributeValue != null ) {
				element.setProperty( attributeName, attributeValue );
			}
		}
	}

	private void addMultiValueAttributes( HierarchicalConfiguration xmlConfig, E element ) {
		for ( String attributeName : getMultiValueAttributeNames() ) {
			String[] attributeValues = getAttributeValues( xmlConfig, attributeName );
			element.setValues( attributeName, attributeValues );
		}
	}

	private void addOptionalMultiValueAttributes( HierarchicalConfiguration xmlConfig, E element ) {
		for ( String attributeName : getOptionalMultiValueAttributeNames() ) {
			String[] attributeValues = getOptionalAttributeValues( xmlConfig, attributeName );
			if ( attributeValues.length > 0 ) {
				element.setValues( attributeName, attributeValues );
			}
		}
	}

	private void addParentAttributes( HierarchicalConfiguration xmlConfig, E element ) {
		ConfigurationNode parent = xmlConfig.getRootNode().getParentNode();

		for ( String attributeName : getParentAttributeNames() ) {
			String attributeValue = getOptionalAttribute( parent, attributeName );
			if ( attributeValue.trim().length() > 0 ) {
				// Parent attributes are optional: only add it if a value is found
				element.setProperty( attributeName, attributeValue );
			}
		}
	}

	private String getOptionalAttribute( ConfigurationNode node, String attributeName ) {
		for ( Object object : node.getAttributes( attributeName ) ) {
			ConfigurationNode attribute = (ConfigurationNode)object;
			Object attributeValue = attribute.getValue();
			if ( attributeValue != null ) {
				return attributeValue.toString();
			}
		}
		return "";
	}

	private String getAttribute( HierarchicalConfiguration element, String attributeName ) throws InvalidMvp4gConfigurationException {

		String value = element.getString( "[@" + attributeName + "]" );
		if ( value == null ) {
			String err = getElementLabel() + " '" + attributeName + "' is missing";
			throw new InvalidMvp4gConfigurationException( err );
		}
		return value;
	}

	private String[] getAttributeValues( HierarchicalConfiguration element, String attributeName ) throws InvalidMvp4gConfigurationException {

		String[] values = element.getStringArray( "[@" + attributeName + "]" );
		if ( values == null || values.length == 0 ) {
			String err = getElementLabel() + " '" + attributeName + "' is missing";
			throw new InvalidMvp4gConfigurationException( err );
		}
		return values;
	}

	private String[] getOptionalAttributeValues( HierarchicalConfiguration element, String attributeName ) {
		String[] values = element.getStringArray( "[@" + attributeName + "]" );
		if ( values == null || values.length == 0 ) {
			values = new String[] {};
		}
		return values;
	}

	private void checkForNonEmptyElements() throws InvalidMvp4gConfigurationException {
		if ( elements.isEmpty() ) {
			String err = "No " + getElementLabel() + " elements found in configuration file.";
			throw new InvalidMvp4gConfigurationException( err );
		}
	}

	private void checkForDuplicates( Set<E> loadedElements, E element ) throws InvalidMvp4gConfigurationException {

		if ( loadedElements.contains( element ) ) {
			String err = "Duplicate " + getElementLabel() + " identified by " + "'" + element.getUniqueIdentifierName()
					+ "' found in configuration file.";
			throw new InvalidMvp4gConfigurationException( err );
		}
	}

}

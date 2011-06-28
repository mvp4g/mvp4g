package com.mvp4g.util.test_tools;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.util.test_tools.annotation.gin.TestGinModule;

public class PropertyOracleStub implements PropertyOracle {

	public static final String PROPERTY_OK = "propertyOK";
	public static final String PROPERTY_OK2 = "propertyOK2";
	public static final String PROPERTY_NOT_GIN_MODULE = "propertyNotGinModule";
	
	private Map<String, SelectionProperty> properties = new HashMap<String, SelectionProperty>();

	public PropertyOracleStub() {
		properties.put( PROPERTY_OK, new SelectionPropertyStub( PROPERTY_OK, DefaultMvp4gGinModule.class.getCanonicalName().replace( ".", "$" ) ) );
		properties.put( PROPERTY_OK2, new SelectionPropertyStub( PROPERTY_OK2, TestGinModule.class.getCanonicalName().replace( ".", "$" ) ) );
		properties.put( PROPERTY_NOT_GIN_MODULE, new SelectionPropertyStub( PROPERTY_NOT_GIN_MODULE, String.class.getCanonicalName().replace( ".", "$" ) ) );
	}

	public ConfigurationProperty getConfigurationProperty( String propertyName ) throws BadPropertyValueException {
		// nothing to do
		return null;
	}

	public String getPropertyValue( TreeLogger logger, String propertyName ) throws BadPropertyValueException {
		// nothing to do
		return null;
	}

	public String[] getPropertyValueSet( TreeLogger logger, String propertyName ) throws BadPropertyValueException {
		// TODO Auto-generated method stub
		return null;
	}

	public SelectionProperty getSelectionProperty( TreeLogger logger, String propertyName ) throws BadPropertyValueException {
		SelectionProperty property = properties.get( propertyName );
		if ( property == null ) {
			throw new BadPropertyValueException( "error" );
		}
		return properties.get( propertyName );
	}

}

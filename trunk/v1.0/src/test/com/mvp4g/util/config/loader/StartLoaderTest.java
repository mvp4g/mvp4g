package com.mvp4g.util.config.loader;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.StartElement;

public class StartLoaderTest extends AbstractMvp4gElementLoaderTest<StartElement, StartLoader> {

	
	@Test
	public void testLoadOk() {
		List<String> attributes = convertToList( basicLoader.getRequiredAttributeNames() );
		attributes.addAll( convertToList( basicLoader.getOptionalAttributeNames() ) );
		List<String> multiValues = convertToList( basicLoader.getMultiValueAttributeNames() );
		multiValues.addAll( convertToList( basicLoader.getOptionalMultiValueAttributeNames() ) );
		List<String> parents = convertToList( basicLoader.getParentAttributeNames() );
		
		StartLoader loader = newLoader( xmlBuilder.getConfigAttribute( attributes, multiValues, parents, 1, false, isSingleNode() ) );
		assertEquals( loader.loadElement(), new ArrayList<StartElement>( loader.loadElements() ).get( 0 ) );

	}
	
	@Override
	protected String getTagName() {
		return "start";
	}

	@Override
	protected boolean isSingleNode() {
		return true;
	}

	@Override
	protected StartLoader newLoader( XMLConfiguration xml ) {
		return new StartLoader( xml );
	}
	
	

}

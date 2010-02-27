package com.mvp4g.util.config.loader.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.DebugElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

public class DebugLoaderTest extends AbstractMvp4gElementLoaderTest<DebugElement, DebugLoader> {

	@Override
	protected String getTagName() {
		return "debug";
	}

	@Override
	protected boolean isSingleNode() {
		return true;
	}

	@Override
	protected DebugLoader newLoader( XMLConfiguration xml ) {
		return new DebugLoader( xml );
	}

	@Test
	public void testLoadOk() throws Mvp4gXmlException {
		List<String> attributes = convertToList( basicLoader.getRequiredAttributeNames() );
		attributes.addAll( convertToList( basicLoader.getOptionalAttributeNames() ) );
		List<String> multiValues = convertToList( basicLoader.getMultiValueAttributeNames() );
		multiValues.addAll( convertToList( basicLoader.getOptionalMultiValueAttributeNames() ) );
		List<String> parents = convertToList( basicLoader.getParentAttributeNames() );

		DebugLoader loader = newLoader( xmlBuilder.getConfigAttribute( attributes, multiValues, parents, 1, false, isSingleNode() ) );
		assertEquals( loader.loadElement(), new ArrayList<DebugElement>( loader.loadElements() ).get( 0 ) );

	}

	@Test
	public void testLoadEmpty() throws Mvp4gXmlException {
		DebugLoader loader = newLoader( xmlBuilder.getEmptyConf() );
		assertNull( loader.loadElement() );

	}

}

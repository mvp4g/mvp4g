package com.mvp4g.util.config.loader.xml;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Test;

import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

public class ChildModulesLoaderTest extends AbstractMvp4gElementLoaderTest<ChildModulesElement, ChildModulesLoader> {

	@Override
	protected String getTagName() {
		return "childModules";
	}

	@Override
	protected boolean isSingleNode() {
		return true;
	}

	@Override
	protected ChildModulesLoader newLoader( XMLConfiguration xml ) {
		return new ChildModulesLoader( xml );
	}

	@Test
	public void testLoadOk() throws Mvp4gXmlException {
		List<String> attributes = convertToList( basicLoader.getRequiredAttributeNames() );
		attributes.addAll( convertToList( basicLoader.getOptionalAttributeNames() ) );
		List<String> multiValues = convertToList( basicLoader.getMultiValueAttributeNames() );
		multiValues.addAll( convertToList( basicLoader.getOptionalMultiValueAttributeNames() ) );
		List<String> parents = convertToList( basicLoader.getParentAttributeNames() );

		ChildModulesLoader loader = newLoader( xmlBuilder.getConfigAttribute( attributes, multiValues, parents, 1, false, isSingleNode() ) );
		assertEquals( loader.loadElement(), new ArrayList<ChildModulesElement>( loader.loadElements() ).get( 0 ) );

	}

	@Test
	public void testLoadEmpty() throws Mvp4gXmlException {
		ChildModulesLoader loader = newLoader( xmlBuilder.getEmptyConf() );
		assertNull( loader.loadElement() );

	}

}

/*
 * Copyright 2009 Pierre-Laurent Coirier
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.util.config.loader.xml;

import java.util.ArrayList;
import java.util.Set;

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ChildModulesElement;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

/**
 * A class responsible for loading the Start tag defined in the configuration file.
 * <p/>
 * 
 * @author javier
 * 
 */
public class ChildModulesLoader extends Mvp4gElementLoader<ChildModulesElement> {

	static final String[] REQUIRED_ATTRIBUTES = {};
	static final String[] OPTIONAL_ATTRIBUTES = { "errorEvent", "beforeEvent", "afterEvent" };

	@SuppressWarnings( "unchecked" )
	public ChildModulesLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "childModules" ) );
	}

	@Override
	protected String getElementLabel() {
		return "ChildModules";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected String[] getOptionalAttributeNames() {
		return OPTIONAL_ATTRIBUTES;
	}

	@Override
	protected ChildModulesElement newElement() {
		return new ChildModulesElement();
	}

	public ChildModulesElement loadElement() throws Mvp4gXmlException {
		Set<ChildModulesElement> elements = super.loadElements();
		return ( elements.size() == 0 ) ? null : new ArrayList<ChildModulesElement>( elements ).get( 0 );
	}
}

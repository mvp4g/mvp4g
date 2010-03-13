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

import org.apache.commons.configuration.XMLConfiguration;

import com.mvp4g.util.config.element.ServiceElement;

/**
 * A class responsible for loading all Services defined in the configuration file.
 * 
 * @author javier
 * 
 */
public class ServicesLoader extends Mvp4gElementLoader<ServiceElement> {

	static final String[] REQUIRED_ATTRIBUTES = { "name", "class" };
	static final String[] PARENT_ATTRIBUTES = { "package" };
	static final String[] OPTIONAL_ATTRIBUTES = { "path", "generatedClassName" };

	@SuppressWarnings( "unchecked" )
	public ServicesLoader( XMLConfiguration xmlConfig ) {
		super( xmlConfig.configurationsAt( "services.service" ) );
	}

	@Override
	protected String getElementLabel() {
		return "Service";
	}

	@Override
	protected String[] getRequiredAttributeNames() {
		return REQUIRED_ATTRIBUTES;
	}

	@Override
	protected ServiceElement newElement() {
		return new ServiceElement();
	}

	@Override
	protected String[] getParentAttributeNames() {
		return PARENT_ATTRIBUTES;
	}

	@Override
	String[] getOptionalAttributeNames() {
		return OPTIONAL_ATTRIBUTES;
	}

}

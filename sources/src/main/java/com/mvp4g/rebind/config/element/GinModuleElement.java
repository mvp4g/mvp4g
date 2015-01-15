/*
 * Copyright 2010 Pierre-Laurent Coirier
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
package com.mvp4g.rebind.config.element;

import java.util.List;

public class GinModuleElement extends Mvp4gElement {

	public GinModuleElement() {
		super( "gin" );
	}

	private static final String GIN_ELEMENT_ID = GinModuleElement.class.getName();

	@Override
	public String getUniqueIdentifierName() {
		return GIN_ELEMENT_ID;
	}

	public void setModules( String[] modules ) {
		setFlexibleValues( "modules", modules );
	}

	public List<String> getModules() {
		return getFlexibleValues( "modules" );
	}

	public void setModuleProperties( String[] modules ) {
		setValues( "moduleProperties", modules );
	}

	public String[] getModuleProperties() {
		return getValues( "moduleProperties" );
	}
}

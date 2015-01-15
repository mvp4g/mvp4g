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
package com.mvp4g.rebind.config.element;

import com.mvp4g.client.annotation.History.HistoryConverterType;

/**
 * An Mvp4g History converter configuration element.</p>
 * 
 * @author plcoirier
 */
public class HistoryConverterElement extends Mvp4gWithServicesElement {

	public HistoryConverterElement() {
		super( "historyConverter" );
	}

	public void setType( String type ) {
		setProperty( "type", type );
	}

	public String getType() {
		String type = getProperty( "type" );
		return ( type == null ) ? HistoryConverterType.DEFAULT.name() : type;
	}

}

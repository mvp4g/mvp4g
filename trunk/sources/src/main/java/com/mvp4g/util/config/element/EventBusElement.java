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
package com.mvp4g.util.config.element;


public class EventBusElement {

	private String interfaceClassName = null;
	private String abstractClassName = null;
	private boolean withLookUp = true;

	public EventBusElement( String interfaceClassName, String abstractClassName, boolean withLookUp ) {
		this.interfaceClassName = interfaceClassName;
		this.abstractClassName = abstractClassName;
		this.withLookUp = withLookUp;
	}

	/**
	 * @return the interfaceClassName
	 */
	public String getInterfaceClassName() {
		return interfaceClassName;
	}

	/**
	 * @return the abstractClassName
	 */
	public String getAbstractClassName() {
		return abstractClassName;
	}

	/**
	 * @return the withLookUp
	 */
	public boolean isWithLookUp() {
		return withLookUp;
	}

}

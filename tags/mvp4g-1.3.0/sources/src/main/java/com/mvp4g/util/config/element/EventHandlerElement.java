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

import com.mvp4g.util.exception.element.DuplicatePropertyNameException;

/**
 * An Mvp4g EventHandler configuration element.</p>
 * 
 * @author Dan Persa
 */
public class EventHandlerElement extends Mvp4gWithServicesElement {

    public EventHandlerElement() {
        super("eventHandler");
    }
    
    public EventHandlerElement(String tagName) {
        super(tagName);
    }
    
	public void setMultiple(String multiple) throws DuplicatePropertyNameException{
		setProperty( "multiple", multiple );
	}
	
	public String getMultiple(){
		return getProperty( "multiple" );
	}
	
	public boolean isMultiple(){
		return Boolean.TRUE.toString().equalsIgnoreCase( getMultiple() );
	}
}

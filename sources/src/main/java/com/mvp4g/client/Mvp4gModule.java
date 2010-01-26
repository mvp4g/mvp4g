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
package com.mvp4g.client;

import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;

/**
 * This interface designs how to start the application
 * 
 * @author plcoirier
 * 
 */
@XmlFilePath("mvp4g-conf.xml")
public interface Mvp4gModule {

	/**
	 * Method called to create the module
	 */
	public void createAndStartModule();
	
	public Object getStartView();
	
	public EventBus getEventBus();
	
	public void addConverter(String token, HistoryConverter<?,?> hc);
	
	public <T> void place(String token, T form);
	
	public <T> void dispatchHistoryEvent(String eventType, Mvp4gEventPasser<Boolean> passer);

}

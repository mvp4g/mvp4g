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
package com.mvp4g.client.event;

/**
 * Command used by the event bus to dispatch an event.<br/>
 * <br/>
 * 
 * When an event bus wants to dispatch an event, it calls the execute method of the command. The
 * execute method will then call the right method of the handlers of the event.
 * 
 * @author plcoirier
 * 
 * @param <T>
 *            Class type of the parameter of the handlers method
 */
public interface Command<T> {

	public void execute( T form, boolean storeInHistory );

}

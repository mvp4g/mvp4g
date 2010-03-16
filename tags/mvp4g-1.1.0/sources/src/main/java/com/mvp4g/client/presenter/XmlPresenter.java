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
package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBusWithLookup;

/**
 * Presenter to use when the event bus is defined thanks to an XML file. It's a presenter where
 * event bus type has been set to automatically match the type of the event bus generated thanks to
 * an XML file.
 * 
 * @author plcoirier
 * 
 * @param <V>
 *            Type of the view injected into the presenter. Must extends <code>LazyView</code>.
 */
public class XmlPresenter<V> extends BasePresenter<V, EventBusWithLookup> {

}

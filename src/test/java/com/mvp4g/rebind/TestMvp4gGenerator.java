/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
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

package com.mvp4g.rebind;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class TestMvp4gGenerator {

  Mvp4gGenerator generator;

  @Before
  public void setUp() {
    generator = new Mvp4gGenerator();
  }

  @Test
  public void testClassesToImport() {
    String[] classesToImport = new String[] { "com.mvp4g.client.history.PlaceService",
                                              "com.google.gwt.core.client.GWT",
                                              "com.google.gwt.user.client.History",
                                              "com.google.gwt.user.client.rpc.ServiceDefTarget",
                                              "com.mvp4g.client.presenter.PresenterInterface",
                                              "com.mvp4g.client.event.EventBus",
                                              "com.mvp4g.client.Mvp4gException",
                                              "com.mvp4g.client.history.HistoryConverter",
                                              "com.mvp4g.client.Mvp4gEventPasser",
                                              "com.mvp4g.client.Mvp4gModule",
                                              "com.google.gwt.inject.client.GinModules",
                                              "com.google.gwt.inject.client.Ginjector",
                                              "com.mvp4g.client.event.BaseEventBus",
                                              "com.mvp4g.client.event.EventFilter",
                                              "com.mvp4g.client.event.EventHandlerInterface",
                                              "java.util.List",
                                              "com.mvp4g.client.history.NavigationEventCommand",
                                              "com.mvp4g.client.history.NavigationConfirmationInterface",
                                              "com.google.gwt.core.client.RunAsyncCallback",
                                              "com.mvp4g.client.Mvp4gRunAsync",
                                              "com.google.gwt.user.client.Command",
                                              "com.mvp4g.client.history.HistoryProxyProvider",
                                              "com.mvp4g.client.history.DefaultHistoryProxy" };
    assertArrayEquals(classesToImport,
                      generator.getClassesToImport());
  }

}

/*
 * Copyright 2007 Google Inc.
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
package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;

/**
 * This application demonstrates how to construct a relatively complex user interface, similar to
 * many common email readers. It has no back-end, populating its components with hard-coded data.
 */
public class MailEntryPoint
    implements EntryPoint {

  /**
   * This method constructs the application user interface by instantiating controls and hooking
   * up event handler.
   */
  public void onModuleLoad() {
    // Inject global styles.
    GWT.<GlobalResources>create(GlobalResources.class)
       .css()
       .ensureInjected();

    Mvp4gModule module = (Mvp4gModule) GWT.create(Mvp4gModule.class);
    module.createAndStartModule();
    RootLayoutPanel.get()
                   .add((Widget) module.getStartView());
  }

  interface GlobalResources
      extends ClientBundle {
    @NotStrict
    @Source("resources/global.css")
    CssResource css();
  }
}

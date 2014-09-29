package de.gishmo.mvp4g.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;
import de.gishmo.mvp4g.client.resources.GlobelCSS;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mvp4gHyperlink
    implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
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
    @CssResource.NotStrict
    @Source("resources/global.css")
    GlobelCSS css();
  }
}

package com.mvp4g.example.client.view.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class Contacts_BinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenBundle {
  public com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenCss_style style() {
    return style;
  }
  private void _init0() {
    style = new com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenCss_style() {
    private boolean injected;
    public boolean ensureInjected() {
      if (!injected) {
        injected = true;
        com.google.gwt.dom.client.StyleInjector.inject(getText());
        return true;
      }
      return false;
    }
    public String getName() {
      return "style";
    }
    public String getText() {
      return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GORY5XYOG{padding:" + ("0.5em")  + ";line-height:" + ("150%")  + ";}.GORY5XYPG{display:" + ("block")  + ";}")) : ((".GORY5XYOG{padding:" + ("0.5em")  + ";line-height:" + ("150%")  + ";}.GORY5XYPG{display:" + ("block")  + ";}"));
    }
    public java.lang.String contacts(){
      return "GORY5XYOG";
    }
    public java.lang.String item(){
      return "GORY5XYPG";
    }
  }
  ;
  }
  
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenCss_style style;
  
  static {
    new Contacts_BinderImpl_GenBundle_default_InlineClientBundleGenerator()._init0();
  }
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      style(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("style", style());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'style': return this.@com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

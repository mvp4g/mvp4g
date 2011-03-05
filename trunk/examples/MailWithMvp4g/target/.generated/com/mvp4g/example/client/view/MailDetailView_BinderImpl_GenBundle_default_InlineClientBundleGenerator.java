package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class MailDetailView_BinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenBundle {
  public com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenCss_style style() {
    return style;
  }
  private void _init0() {
    style = new com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenCss_style() {
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
      return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GORY5XYFH{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";background-color:" + ("white")  + ";}.GORY5XYGH{background:" + ("#eee")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";padding:" + ("0.5em")  + ";}.GORY5XYHH{margin-bottom:" + ("0.5em")  + ";}.GORY5XYEH{line-height:" + ("150%")  + ";padding:" + ("20px"+ " " +"10px"+ " " +"20px"+ " " +"40px")  + ";font-family:" + ("\"Times New Roman\""+ ","+ " " +"Times"+ ","+ " " +"serif")  + ";}")) : ((".GORY5XYFH{border:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";background-color:" + ("white")  + ";}.GORY5XYGH{background:" + ("#eee")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#666")  + ";padding:" + ("0.5em")  + ";}.GORY5XYHH{margin-bottom:" + ("0.5em")  + ";}.GORY5XYEH{line-height:" + ("150%")  + ";padding:" + ("20px"+ " " +"40px"+ " " +"20px"+ " " +"10px")  + ";font-family:" + ("\"Times New Roman\""+ ","+ " " +"Times"+ ","+ " " +"serif")  + ";}"));
    }
    public java.lang.String body(){
      return "GORY5XYEH";
    }
    public java.lang.String detail(){
      return "GORY5XYFH";
    }
    public java.lang.String header(){
      return "GORY5XYGH";
    }
    public java.lang.String headerItem(){
      return "GORY5XYHH";
    }
  }
  ;
  }
  
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenCss_style style;
  
  static {
    new MailDetailView_BinderImpl_GenBundle_default_InlineClientBundleGenerator()._init0();
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
      case 'style': return this.@com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

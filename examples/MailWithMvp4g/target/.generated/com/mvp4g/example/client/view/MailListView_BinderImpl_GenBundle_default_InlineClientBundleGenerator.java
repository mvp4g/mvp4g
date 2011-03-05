package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ResourcePrototype;

public class MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator implements com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle {
  public com.google.gwt.resources.client.ImageResource gradient() {
    return gradient;
  }
  public com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_selectionStyle selectionStyle() {
    return selectionStyle;
  }
  public com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_style style() {
    return style;
  }
  private void _init0() {
    gradient = new com.google.gwt.resources.client.impl.ImageResourcePrototype(
    "gradient",
    externalImage,
    0, 0, 1, 64, false, true
  );
    selectionStyle = new com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_selectionStyle() {
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
      return "selectionStyle";
    }
    public String getText() {
      return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GORY5XYAH{background:" + ("#adcce7")  + ";}.GORY5XYAH td{border-top:" + ("1px"+ " " +"solid"+ " " +"#88a4d6")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#7b97d0")  + ";}")) : ((".GORY5XYAH{background:" + ("#adcce7")  + ";}.GORY5XYAH td{border-top:" + ("1px"+ " " +"solid"+ " " +"#88a4d6")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#7b97d0")  + ";}"));
    }
    public java.lang.String selectedRow(){
      return "GORY5XYAH";
    }
  }
  ;
    style = new com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_style() {
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
      return com.google.gwt.i18n.client.LocaleInfo.getCurrentLocale().isRTL() ? ((".GORY5XYCH{border-right:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";cursor:" + ("pointer")  + ";cursor:" + ("hand")  + ";}.GORY5XYBH{height:" + ((MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getURL() + "\") -" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getLeft() + "px -" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getTop() + "px  repeat-x")  + ";background-color:" + ("#d3d6dd")  + ";table-layout:" + ("fixed")  + ";width:" + ("100%")  + ";height:") + (("100%")  + ";}.GORY5XYBH td{font-weight:" + ("bold")  + ";text-shadow:" + ("#fff"+ " " +"0"+ " " +"2px"+ " " +"2px")  + ";padding:" + ("2px"+ " " +"10px"+ " " +"1px"+ " " +"0")  + ";border-top:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";}.GORY5XYDH{table-layout:" + ("fixed")  + ";width:" + ("100%")  + ";}.GORY5XYDH td{border-top:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("2px"+ " " +"10px"+ " " +"2px"+ " " +"0") ) + (";}")) : ((".GORY5XYCH{border-left:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";cursor:" + ("pointer")  + ";cursor:" + ("hand")  + ";}.GORY5XYBH{height:" + ((MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getHeight() + "px")  + ";overflow:" + ("hidden")  + ";background:" + ("url(\"" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getURL() + "\") -" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getLeft() + "px -" + (MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator.this.gradient()).getTop() + "px  repeat-x")  + ";background-color:" + ("#d3d6dd")  + ";table-layout:" + ("fixed")  + ";width:" + ("100%")  + ";height:") + (("100%")  + ";}.GORY5XYBH td{font-weight:" + ("bold")  + ";text-shadow:" + ("#fff"+ " " +"0"+ " " +"2px"+ " " +"2px")  + ";padding:" + ("2px"+ " " +"0"+ " " +"1px"+ " " +"10px")  + ";border-top:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#999")  + ";}.GORY5XYDH{table-layout:" + ("fixed")  + ";width:" + ("100%")  + ";}.GORY5XYDH td{border-top:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";border-bottom:" + ("1px"+ " " +"solid"+ " " +"#fff")  + ";padding:" + ("2px"+ " " +"0"+ " " +"2px"+ " " +"10px") ) + (";}"));
    }
    public java.lang.String header(){
      return "GORY5XYBH";
    }
    public java.lang.String outer(){
      return "GORY5XYCH";
    }
    public java.lang.String table(){
      return "GORY5XYDH";
    }
  }
  ;
  }
  
  private static java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype> resourceMap;
  private static final java.lang.String externalImage = "data:image/png;base64,/9j/4AAQSkZJRgABAgAAZABkAAD/7AARRHVja3kAAQAEAAAAZAAA/+4ADkFkb2JlAGTAAAAAAf/bAIQAAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQICAgICAgICAgICAwMDAwMDAwMDAwEBAQEBAQECAQECAgIBAgIDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMDAwMD/8AAEQgAQAABAwERAAIRAQMRAf/EAGUAAQEBAAAAAAAAAAAAAAAAAAYFCgEBAQEBAAAAAAAAAAAAAAAAAgMBABAAAgECBgMAAAAAAAAAAAAAAAEU8FGRobHRAmJSohMRAAICAgMBAAAAAAAAAAAAAAABERIhUfBh4QL/2gAMAwEAAhEDEQA/ANNkjtkLHH4YRZap8RV6OlBmVx8tBV+tBlBmT2eW4pYcaQZmKmitUSsE5Tv7FsaDZBuS7aCqwyQvs71gPJtGf//Z";
  private static com.google.gwt.resources.client.ImageResource gradient;
  private static com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_selectionStyle selectionStyle;
  private static com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_style style;
  
  static {
    new MailListView_BinderImpl_GenBundle_default_InlineClientBundleGenerator()._init0();
  }
  public ResourcePrototype[] getResources() {
    return new ResourcePrototype[] {
      gradient(), 
      selectionStyle(), 
      style(), 
    };
  }
  public ResourcePrototype getResource(String name) {
    if (GWT.isScript()) {
      return getResourceNative(name);
    } else {
      if (resourceMap == null) {
        resourceMap = new java.util.HashMap<java.lang.String, com.google.gwt.resources.client.ResourcePrototype>();
        resourceMap.put("gradient", gradient());
        resourceMap.put("selectionStyle", selectionStyle());
        resourceMap.put("style", style());
      }
      return resourceMap.get(name);
    }
  }
  private native ResourcePrototype getResourceNative(String name) /*-{
    switch (name) {
      case 'gradient': return this.@com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle::gradient()();
      case 'selectionStyle': return this.@com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle::selectionStyle()();
      case 'style': return this.@com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle::style()();
    }
    return null;
  }-*/;
}

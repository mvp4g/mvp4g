package com.mvp4g.example.client.view;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.CssResource.Import;

public interface TopPanel_BinderImpl_GenBundle extends ClientBundle {
  @Source("uibinder:com.mvp4g.example.client.view.TopPanel_BinderImpl_GenCss_style.css")
  TopPanel_BinderImpl_GenCss_style style();

  @Source("../resources/logo.png")
  ImageResource logo();
}

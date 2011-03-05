package com.mvp4g.example.client.view;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.CssResource.Import;

public interface MailListView_BinderImpl_GenBundle extends ClientBundle {
  @Source("uibinder:com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_style.css")
  MailListView_BinderImpl_GenCss_style style();

  @Source("uibinder:com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_selectionStyle.css")
  MailListView_BinderImpl_GenCss_selectionStyle selectionStyle();

  @Source("../resources/gradient_bg_dark.png")
  @ImageOptions(repeatStyle=ImageResource.RepeatStyle.Horizontal)
  ImageResource gradient();
}

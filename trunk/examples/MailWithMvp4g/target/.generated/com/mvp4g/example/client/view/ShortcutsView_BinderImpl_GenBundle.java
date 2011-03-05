package com.mvp4g.example.client.view;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.resources.client.ImageResource.ImageOptions;
import com.google.gwt.resources.client.CssResource.Import;

public interface ShortcutsView_BinderImpl_GenBundle extends ClientBundle {
  @Source("uibinder:com.mvp4g.example.client.view.ShortcutsView_BinderImpl_GenCss_style.css")
  ShortcutsView_BinderImpl_GenCss_style style();

  @Source("../resources/mailboxesgroup.png")
  ImageResource mailboxesgroup();
  @Source("../resources/contactsgroup.png")
  ImageResource contactsgroup();
  @Source("../resources/tasksgroup.png")
  ImageResource tasksgroup();
  @Source("../resources/gradient_bg_dark.png")
  @ImageOptions(repeatStyle=ImageResource.RepeatStyle.Horizontal)
  ImageResource gradient();
}

package com.mvp4g.example.client.view.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.FlowPanel;

public class Tasks_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.FlowPanel, com.mvp4g.example.client.view.widget.Tasks>, com.mvp4g.example.client.view.widget.Tasks.Binder {

  public com.google.gwt.user.client.ui.FlowPanel createAndBindUi(final com.mvp4g.example.client.view.widget.Tasks owner) {

    com.mvp4g.example.client.view.widget.Tasks_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.widget.Tasks_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.widget.Tasks_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.widget.Tasks_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    com.google.gwt.user.client.ui.FlowPanel f_FlowPanel1 = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);

    f_FlowPanel1.setStyleName("" + style.tasks() + "");



    owner.style = style;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_FlowPanel1;
  }
}

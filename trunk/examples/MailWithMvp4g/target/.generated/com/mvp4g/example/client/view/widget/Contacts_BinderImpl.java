package com.mvp4g.example.client.view.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class Contacts_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.mvp4g.example.client.view.widget.Contacts>, com.mvp4g.example.client.view.widget.Contacts.Binder {

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.mvp4g.example.client.view.widget.Contacts owner) {

    com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.widget.Contacts_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    com.google.gwt.user.client.ui.FlowPanel panel = (com.google.gwt.user.client.ui.FlowPanel) GWT.create(com.google.gwt.user.client.ui.FlowPanel.class);

    panel.setStyleName("" + style.contacts() + "");



    owner.panel = panel;
    owner.style = style;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return panel;
  }
}

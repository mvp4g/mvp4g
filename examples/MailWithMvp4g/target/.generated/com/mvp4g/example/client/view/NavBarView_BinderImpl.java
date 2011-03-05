package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class NavBarView_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.mvp4g.example.client.view.NavBarView>, com.mvp4g.example.client.view.NavBarView.Binder {

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.mvp4g.example.client.view.NavBarView owner) {

    com.mvp4g.example.client.view.NavBarView_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.NavBarView_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.NavBarView_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.NavBarView_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Anchor newerButton = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
    com.google.gwt.dom.client.SpanElement countLabel = null;
    java.lang.String domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
    java.lang.String domId2 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Anchor olderButton = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel("<span id='" + domId0 + "'></span> <span id='" + domId1 + "'></span> <span id='" + domId2 + "'></span>");

    newerButton.setHTML("&lt; newer");
    newerButton.setStyleName("" + style.anchor() + "");
    newerButton.setHref("javascript:;");
    olderButton.setHTML("older &gt;");
    olderButton.setStyleName("" + style.anchor() + "");
    olderButton.setHref("javascript:;");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
    com.google.gwt.user.client.Element domId0Element = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    countLabel = com.google.gwt.dom.client.Document.get().getElementById(domId1).cast();
    countLabel.removeAttribute("id");
    com.google.gwt.user.client.Element domId2Element = com.google.gwt.dom.client.Document.get().getElementById(domId2).cast();
    attachRecord0.detach();
    f_HTMLPanel1.addAndReplaceElement(newerButton, domId0Element);
    f_HTMLPanel1.addAndReplaceElement(olderButton, domId2Element);


    owner.countLabel = countLabel;
    owner.newerButton = newerButton;
    owner.olderButton = olderButton;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_HTMLPanel1;
  }
}

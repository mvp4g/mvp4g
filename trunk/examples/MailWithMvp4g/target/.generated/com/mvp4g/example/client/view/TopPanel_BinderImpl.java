package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class TopPanel_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.mvp4g.example.client.view.TopPanel>, com.mvp4g.example.client.view.TopPanel.Binder {

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.mvp4g.example.client.view.TopPanel owner) {

    com.mvp4g.example.client.view.TopPanel_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.TopPanel_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.TopPanel_BinderImpl_GenBundle.class);
    com.google.gwt.resources.client.ImageResource logo = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.logo();
    com.mvp4g.example.client.view.TopPanel_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Anchor signOutLink = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
    java.lang.String domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.Anchor aboutLink = (com.google.gwt.user.client.ui.Anchor) GWT.create(com.google.gwt.user.client.ui.Anchor.class);
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel1 = new com.google.gwt.user.client.ui.HTMLPanel("<div class='" + "" + style.logo() + "" + "'></div> <div class='" + "" + style.statusDiv() + "" + "'> <div> <b>Welcome back, foo@example.com</b> </div> <div class='" + "" + style.linksDiv() + "" + "'> <span id='" + domId0 + "'></span>   <span id='" + domId1 + "'></span> </div> </div>");

    signOutLink.setHTML("Sign Out");
    signOutLink.setHref("javascript:;");
    aboutLink.setHTML("About");
    aboutLink.setHref("javascript:;");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel1.getElement());
    com.google.gwt.user.client.Element domId0Element = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    com.google.gwt.user.client.Element domId1Element = com.google.gwt.dom.client.Document.get().getElementById(domId1).cast();
    attachRecord0.detach();
    f_HTMLPanel1.addAndReplaceElement(signOutLink, domId0Element);
    f_HTMLPanel1.addAndReplaceElement(aboutLink, domId1Element);


    owner.aboutLink = aboutLink;
    owner.signOutLink = signOutLink;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_HTMLPanel1;
  }
}

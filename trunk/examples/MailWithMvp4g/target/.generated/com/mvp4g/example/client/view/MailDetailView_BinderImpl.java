package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class MailDetailView_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.mvp4g.example.client.view.MailDetailView>, com.mvp4g.example.client.view.MailDetailView.Binder {

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.mvp4g.example.client.view.MailDetailView owner) {

    com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.MailDetailView_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    com.google.gwt.dom.client.DivElement subject = null;
    java.lang.String domId0 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.dom.client.SpanElement sender = null;
    java.lang.String domId1 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.dom.client.SpanElement recipient = null;
    java.lang.String domId2 = com.google.gwt.dom.client.Document.get().createUniqueId();
    com.google.gwt.user.client.ui.HTMLPanel f_HTMLPanel2 = new com.google.gwt.user.client.ui.HTMLPanel("<div class='" + "" + style.headerItem() + "" + "' id='" + domId0 + "'></div> <div class='" + "" + style.headerItem() + "" + "'><b>From:</b> <span id='" + domId1 + "'></span></div> <div class='" + "" + style.headerItem() + "" + "'><b>To:</b> <span id='" + domId2 + "'></span></div>");
    com.google.gwt.user.client.ui.HTML body = (com.google.gwt.user.client.ui.HTML) GWT.create(com.google.gwt.user.client.ui.HTML.class);
    com.google.gwt.user.client.ui.ScrollPanel f_ScrollPanel3 = (com.google.gwt.user.client.ui.ScrollPanel) GWT.create(com.google.gwt.user.client.ui.ScrollPanel.class);
    com.google.gwt.user.client.ui.DockLayoutPanel f_DockLayoutPanel1 = new com.google.gwt.user.client.ui.DockLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);

    f_HTMLPanel2.setStyleName("" + style.header() + "");
    f_DockLayoutPanel1.addNorth(f_HTMLPanel2, 6);
    body.setStyleName("" + style.body() + "");
    body.setWordWrap(true);
    f_ScrollPanel3.add(body);
    f_DockLayoutPanel1.add(f_ScrollPanel3);
    f_DockLayoutPanel1.setStyleName("" + style.detail() + "");

    UiBinderUtil.TempAttachment attachRecord0 = UiBinderUtil.attachToDom(f_HTMLPanel2.getElement());
    subject = com.google.gwt.dom.client.Document.get().getElementById(domId0).cast();
    subject.removeAttribute("id");
    sender = com.google.gwt.dom.client.Document.get().getElementById(domId1).cast();
    sender.removeAttribute("id");
    recipient = com.google.gwt.dom.client.Document.get().getElementById(domId2).cast();
    recipient.removeAttribute("id");
    attachRecord0.detach();


    owner.body = body;
    owner.recipient = recipient;
    owner.sender = sender;
    owner.subject = subject;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_DockLayoutPanel1;
  }
}

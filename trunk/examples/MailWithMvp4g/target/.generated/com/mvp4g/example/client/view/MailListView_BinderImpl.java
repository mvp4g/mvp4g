package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.Widget;

public class MailListView_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.Widget, com.mvp4g.example.client.view.MailListView>, com.mvp4g.example.client.view.MailListView.Binder {

  public com.google.gwt.user.client.ui.Widget createAndBindUi(final com.mvp4g.example.client.view.MailListView owner) {

    com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.MailListView_BinderImpl_GenBundle.class);
    com.google.gwt.resources.client.ImageResource gradient = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.gradient();
    com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    com.mvp4g.example.client.view.MailListView_BinderImpl_GenCss_selectionStyle selectionStyle = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.selectionStyle();
    com.google.gwt.user.client.ui.FlexTable header = (com.google.gwt.user.client.ui.FlexTable) GWT.create(com.google.gwt.user.client.ui.FlexTable.class);
    com.google.gwt.user.client.ui.FlexTable table = (com.google.gwt.user.client.ui.FlexTable) GWT.create(com.google.gwt.user.client.ui.FlexTable.class);
    com.google.gwt.user.client.ui.ScrollPanel f_ScrollPanel2 = (com.google.gwt.user.client.ui.ScrollPanel) GWT.create(com.google.gwt.user.client.ui.ScrollPanel.class);
    com.google.gwt.user.client.ui.DockLayoutPanel f_DockLayoutPanel1 = new com.google.gwt.user.client.ui.DockLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);

    header.setStyleName("" + style.header() + "");
    header.setCellSpacing(0);
    header.setCellPadding(0);
    f_DockLayoutPanel1.addNorth(header, 2);
    table.setStyleName("" + style.table() + "");
    table.setCellSpacing(0);
    table.setCellPadding(0);
    f_ScrollPanel2.add(table);
    f_DockLayoutPanel1.add(f_ScrollPanel2);
    f_DockLayoutPanel1.setStyleName("" + style.outer() + "");



    owner.header = header;
    owner.selectionStyle = selectionStyle;
    owner.table = table;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.selectionStyle().ensureInjected();

    return f_DockLayoutPanel1;
  }
}

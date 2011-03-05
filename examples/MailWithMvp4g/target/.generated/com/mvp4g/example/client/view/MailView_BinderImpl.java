package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.DockLayoutPanel;

public class MailView_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.DockLayoutPanel, com.mvp4g.example.client.view.MailView>, com.mvp4g.example.client.view.MailView.Binder {

  public com.google.gwt.user.client.ui.DockLayoutPanel createAndBindUi(final com.mvp4g.example.client.view.MailView owner) {

    com.mvp4g.example.client.view.MailView_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.MailView_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.MailView_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.TopPanel topPanel = owner.topPanel;
    com.mvp4g.example.client.view.ShortcutsView shortcuts = owner.shortcuts;
    com.mvp4g.example.client.view.MailListView mailList = owner.mailList;
    com.mvp4g.example.client.view.MailDetailView mailDetail = owner.mailDetail;
    com.google.gwt.user.client.ui.SplitLayoutPanel f_SplitLayoutPanel2 = (com.google.gwt.user.client.ui.SplitLayoutPanel) GWT.create(com.google.gwt.user.client.ui.SplitLayoutPanel.class);
    com.google.gwt.user.client.ui.DockLayoutPanel f_DockLayoutPanel1 = new com.google.gwt.user.client.ui.DockLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);

    f_DockLayoutPanel1.addNorth(topPanel, 5);
    f_SplitLayoutPanel2.addWest(shortcuts, 192);
    f_SplitLayoutPanel2.addNorth(mailList, 200);
    f_SplitLayoutPanel2.add(mailDetail);
    f_DockLayoutPanel1.add(f_SplitLayoutPanel2);




    return f_DockLayoutPanel1;
  }
}

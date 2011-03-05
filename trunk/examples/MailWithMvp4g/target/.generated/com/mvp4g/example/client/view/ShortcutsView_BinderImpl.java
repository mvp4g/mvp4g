package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiBinderUtil;
import com.google.gwt.user.client.ui.StackLayoutPanel;

public class ShortcutsView_BinderImpl implements UiBinder<com.google.gwt.user.client.ui.StackLayoutPanel, com.mvp4g.example.client.view.ShortcutsView>, com.mvp4g.example.client.view.ShortcutsView.Binder {

  public com.google.gwt.user.client.ui.StackLayoutPanel createAndBindUi(final com.mvp4g.example.client.view.ShortcutsView owner) {

    com.mvp4g.example.client.view.ShortcutsView_BinderImpl_GenBundle clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay = (com.mvp4g.example.client.view.ShortcutsView_BinderImpl_GenBundle) GWT.create(com.mvp4g.example.client.view.ShortcutsView_BinderImpl_GenBundle.class);
    com.mvp4g.example.client.view.ShortcutsView_BinderImpl_GenCss_style style = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style();
    com.google.gwt.resources.client.ImageResource mailboxesgroup = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.mailboxesgroup();
    com.google.gwt.resources.client.ImageResource contactsgroup = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.contactsgroup();
    com.google.gwt.resources.client.ImageResource tasksgroup = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.tasksgroup();
    com.google.gwt.resources.client.ImageResource gradient = clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.gradient();
    com.mvp4g.example.client.view.widget.Mailboxes mailboxes = (com.mvp4g.example.client.view.widget.Mailboxes) GWT.create(com.mvp4g.example.client.view.widget.Mailboxes.class);
    com.mvp4g.example.client.view.widget.Tasks tasks = (com.mvp4g.example.client.view.widget.Tasks) GWT.create(com.mvp4g.example.client.view.widget.Tasks.class);
    com.mvp4g.example.client.view.widget.Contacts contacts = (com.mvp4g.example.client.view.widget.Contacts) GWT.create(com.mvp4g.example.client.view.widget.Contacts.class);
    com.google.gwt.user.client.ui.StackLayoutPanel f_StackLayoutPanel1 = new com.google.gwt.user.client.ui.StackLayoutPanel(com.google.gwt.dom.client.Style.Unit.EM);

    f_StackLayoutPanel1.add(mailboxes, "<div class='" + "" + style.stackHeader() + "" + "'><div class='" + "" + style.mailboxesIcon() + "" + "'></div> Mailboxes</div>", true, 3);
    f_StackLayoutPanel1.add(tasks, "<div class='" + "" + style.stackHeader() + "" + "'><div class='" + "" + style.tasksIcon() + "" + "'></div> Tasks</div>", true, 3);
    f_StackLayoutPanel1.add(contacts, "<div class='" + "" + style.stackHeader() + "" + "'><div class='" + "" + style.contactsIcon() + "" + "'></div> Contacts</div>", true, 3);
    f_StackLayoutPanel1.setStyleName("" + style.shortcuts() + "");



    owner.contacts = contacts;
    owner.mailboxes = mailboxes;
    owner.tasks = tasks;
    clientBundleFieldNameUnlikelyToCollideWithUserSpecifiedFieldOkay.style().ensureInjected();

    return f_StackLayoutPanel1;
  }
}

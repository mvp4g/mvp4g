package com.mvp4g.example.client.ui.detail;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IMailDetailView
    extends IsWidget,
            ReverseViewInterface<IMailDetailView.IMailDetailPresenter> {

  void setSubject(String subject);

  ;

  void setSender(String sender);

  void setRecipient(String recipient);

  void setBody(String body);

  interface IMailDetailPresenter {
  }

}

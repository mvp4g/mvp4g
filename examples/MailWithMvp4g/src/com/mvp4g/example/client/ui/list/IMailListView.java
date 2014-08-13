package com.mvp4g.example.client.ui.list;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.bean.MailItem;

import java.util.List;

public interface IMailListView
    extends IsWidget,
            ReverseViewInterface<IMailListView.IMailListPresenter> {

  void clearEmails();

  void setEmails(List<MailItem> emails);

  void selectRow(int row,
                 boolean selected);

  interface IMailListPresenter {

    void doSelect(MailItem mail);

  }

}

package com.mvp4g.example.client.presenter.interfaces;

import com.mvp4g.client.view.ReverseViewInterface;

public interface IMailListView
  extends ReverseViewInterface<IMailListView.IMailListPresenter> {

  interface IMailListPresenter {

    void onTableClick(int row);

  }

  void clearEmails();

  void setRow(int row,
              String sender,
              String email,
              String subject);

  void selectRow(int row,
                 boolean selected);

}

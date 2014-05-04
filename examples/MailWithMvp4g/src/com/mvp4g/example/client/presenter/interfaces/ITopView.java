package com.mvp4g.example.client.presenter.interfaces;

import com.mvp4g.client.view.ReverseViewInterface;

public interface ITopView
  extends ReverseViewInterface<ITopView.ITopPresenter> {

  interface ITopPresenter {
    void onAboutButtonClick();

    void onSignOutLinkClick();
  }

  void showAboutDialog();

  void showAlert(String message);
}

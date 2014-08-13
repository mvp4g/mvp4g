package com.mvp4g.example.client.ui.top;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.client.view.ReverseViewInterface;

public interface ITopView
    extends IsWidget,
            ReverseViewInterface<ITopView.ITopPresenter> {

  void showAboutDialog();

  void showAlert(String message);

  interface ITopPresenter {
    void onAboutButtonClick();

    void onSignOutLinkClick();
  }
}

package com.mvp4g.example.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.presenter.interfaces.ITopView;
import com.mvp4g.example.client.presenter.interfaces.ITopView.ITopPresenter;
import com.mvp4g.example.client.view.TopView;

@Presenter(view = TopView.class)
public class TopPresenter
  extends BasePresenter<ITopView, MailEventBus>
  implements ITopPresenter {

  public void onAboutButtonClick() {
    view.showAboutDialog();
  }

  public void onSignOutLinkClick() {
    view.showAlert("If this were implemented, you would be signed out now.");
  }

}

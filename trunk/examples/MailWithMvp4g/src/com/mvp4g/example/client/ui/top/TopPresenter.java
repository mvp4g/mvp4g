package com.mvp4g.example.client.ui.top;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.ui.top.ITopView.ITopPresenter;

@Presenter(view = ITopView.class)
public class TopPresenter
    extends BasePresenter<ITopView, MailEventBus>
    implements ITopPresenter {

  @Override
  public void bind() {
    eventBus.setTopView(view.asWidget());
  }

  public void onAboutButtonClick() {
    view.showAboutDialog();
  }

  public void onSignOutLinkClick() {
    view.showAlert("If this were implemented, you would be signed out now.");
  }

}

package com.mvp4g.example.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.presenter.interfaces.IMailView;
import com.mvp4g.example.client.presenter.interfaces.IMailView.IMailPresenter;
import com.mvp4g.example.client.view.MailView;


@Presenter(view = MailView.class)
public class MailPresenter
  extends BasePresenter<IMailView, MailEventBus>
  implements IMailPresenter {

}

package com.mvp4g.example.client.presenter.display;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.presenter.view_interface.display.BasicBeanDisplayViewInterface;
import com.mvp4g.example.client.view.display.DealDisplayView;

@Presenter(view = DealDisplayView.class)
public class DealDisplayPresenter
  extends LazyPresenter<DealDisplayPresenter.DealDisplayViewInterface, MyEventBus> {

  public interface DealDisplayViewInterface
    extends BasicBeanDisplayViewInterface {

    void setCode(String code);

  }

  public void onDisplayDeal(DealBean deal) {
    view.setName(deal.getName());
    view.setDescription(deal.getDescription());
    view.setCode(deal.getCode());
    eventBus.changeMainWidget(view);
  }

}

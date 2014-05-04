package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.History;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.DealBean;

@History
public class DealHistoryConverter
  extends AbstractHistoryConverter<DealBean> {

  public String onDisplayDeal(DealBean deal) {
    return convertToToken(deal);
  }

  @Override
  void serviceCall(String id,
                   AsyncCallback<DealBean> callback) {
    service.getDealDetails(id,
                           callback);
  }

  @Override
  void sendEvent(MyEventBus eventBus,
                 DealBean result) {
    eventBus.displayDeal(result);
  }

}

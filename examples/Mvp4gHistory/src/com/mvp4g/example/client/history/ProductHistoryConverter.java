package com.mvp4g.example.client.history;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.History;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.ProductBean;

@History
public class ProductHistoryConverter
  extends AbstractHistoryConverter<ProductBean> {

  public String onDisplayProduct(ProductBean product) {
    return convertToToken(product);
  }

  @Override
  void serviceCall(String id,
                   AsyncCallback<ProductBean> callback) {
    service.getProductDetails(id,
                              callback);
  }

  @Override
  void sendEvent(MyEventBus eventBus,
                 ProductBean result) {
    eventBus.displayProduct(result);
  }

}

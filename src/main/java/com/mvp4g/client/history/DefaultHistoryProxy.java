package com.mvp4g.client.history;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.History;

public class DefaultHistoryProxy
  implements HistoryProxy {

  public static final DefaultHistoryProxy INSTANCE = new DefaultHistoryProxy();

  private DefaultHistoryProxy() {
    //this class is a singleton
  }

  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
    return History.addValueChangeHandler(handler);
  }

  public void back() {
    History.back();
  }

  public void fireCurrentHistoryState() {
    History.fireCurrentHistoryState();
  }

  public void forward() {
    History.forward();
  }

  public String getToken() {
    return History.getToken();
  }

  public void newItem(String historyToken) {
    History.newItem(historyToken);
  }

  public void newItem(String historyToken,
                      boolean issueEvent) {
    History.newItem(historyToken,
                    issueEvent);
  }

}

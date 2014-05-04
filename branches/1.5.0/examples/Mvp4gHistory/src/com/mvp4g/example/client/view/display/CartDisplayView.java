package com.mvp4g.example.client.view.display;

import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.display.CartDisplayPresenter.CartDisplayViewInterface;
import com.mvp4g.example.client.view.BaseView;

public class CartDisplayView
  extends BaseView
  implements CartDisplayViewInterface {

  private VerticalPanel mainPanel = new VerticalPanel();

  @Override
  protected Widget createWidget() {
    setStyleName("display");
    return mainPanel;
  }

  public void clear() {
    mainPanel.clear();
  }

  public Widget getViewWidget() {
    return this;
  }

  public void addProduct(String name,
                         String price,
                         String description) {
    mainPanel.add(new HTML(buildElement(name,
                                        price,
                                        description)));

  }

  private String buildElement(String name,
                              String price,
                              String description) {
    StringBuilder element = new StringBuilder(200);
    element.append("<div><span class=\"name\">");
    element.append(name);
    element.append("</span><span class=\"price\" >");
    element.append(price);
    element.append("</span></div><div>");
    element.append(description);
    element.append("<div>");
    return element.toString();
  }

}

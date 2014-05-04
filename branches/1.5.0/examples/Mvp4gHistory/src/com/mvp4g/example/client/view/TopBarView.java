package com.mvp4g.example.client.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.presenter.TopBarPresenter.TopBarViewInterface;

public class TopBarView
  extends BaseView
  implements TopBarViewInterface {

  private ListBox deals    = new ListBox();
  private ListBox products = new ListBox();

  private Button showDeal    = new Button("SHOW");
  private Button showProduct = new Button("SHOW");

  private CheckBox save = new CheckBox("Save in History");

  @Override
  protected Widget createWidget() {
    HorizontalPanel mainPanel = new HorizontalPanel();

    save.setValue(true);

    mainPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
    mainPanel.add(new Label("Products :"));
    mainPanel.add(products);
    mainPanel.add(showProduct);
    mainPanel.add(new Label("Deals :"));
    mainPanel.add(deals);
    mainPanel.add(showDeal);
    mainPanel.add(save);

    mainPanel.setStyleName("bar");

    return mainPanel;
  }

  public HasClickHandlers getShowDealButton() {
    return showDeal;
  }

  public HasClickHandlers getShowProductButton() {
    return showProduct;
  }

  public HasValue<Boolean> getSave() {
    return save;
  }

  public Widget getViewWidget() {
    return this;
  }

  public void addDeal(String text,
                      String value) {
    deals.addItem(text,
                  value);
  }

  public void addProduct(String text,
                         String value) {
    products.addItem(text,
                     value);
  }

  public String getSelectedDeal() {
    return deals.getValue(deals.getSelectedIndex());
  }

  public String getSelectedProduct() {
    return products.getValue(products.getSelectedIndex());
  }

  public void setSelectedDeal(int index) {
    deals.setSelectedIndex(index);
  }

  public void setSelectedProduct(int index) {
    products.setSelectedIndex(index);
  }

}

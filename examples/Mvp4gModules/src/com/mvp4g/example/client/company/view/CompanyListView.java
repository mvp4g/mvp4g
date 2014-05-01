package com.mvp4g.example.client.company.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.company.presenter.CompanyListPresenter.CompanyListViewInterface;

public class CompanyListView
  extends Composite
  implements CompanyListViewInterface {

  private Hyperlink createButton, goToProduct;
  private VerticalPanel table;
  private CheckBox      filter, moduleHistory, applicationHistory;

  public HasClickHandlers getCreateButton() {
    return createButton;
  }

  public void removeCompany(int row) {
    table.remove(row);
  }

  public void clearTable() {
    table.clear();
  }

  public void createView() {
    table = new VerticalPanel();
    createButton = new Hyperlink("Create Company",
                                 "");
    goToProduct = new Hyperlink("Go To Products",
                                "");
    filter = new CheckBox("Filter Company EventBus events");
    moduleHistory = new CheckBox("Disable Company Module History");
    applicationHistory = new CheckBox("Disable Application History");

    VerticalPanel mainPanel = new VerticalPanel();
    mainPanel.add(table);
    mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
    mainPanel.add(createButton);
    mainPanel.add(goToProduct);
    mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
    mainPanel.add(moduleHistory);
    mainPanel.add(applicationHistory);
    mainPanel.add(filter);

    initWidget(mainPanel);
  }

  public void addCompany(IsWidget w) {
    table.add(w);
  }

  public HasValue<Boolean> isFiltered() {
    return filter;
  }

  public void alert(String msg) {
    Window.alert(msg);
  }

  public void setGoToCreationToken(String token) {
    createButton.setTargetHistoryToken(token);
  }

  public void setGoToProductsToken(String token) {
    goToProduct.setTargetHistoryToken(token);
  }

  @Override
  public HasValue<Boolean> isDisabledApplicationHistory() {
    return applicationHistory;
  }

  @Override
  public HasValue<Boolean> isDisabledModuleHistory() {
    return moduleHistory;
  }

}

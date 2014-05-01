package com.mvp4g.example.client.company.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.company.presenter.CompanyNameSelectorPresenter;

public class CompanyNameSelectorView
  extends DialogBox
  implements CompanyNameSelectorPresenter.CompanyNameSelectorViewInterface {

  private ListBox names;
  private Button  select;

  public ListBox getNames() {
    return names;
  }

  public HasClickHandlers getSelectButton() {
    return select;
  }

  public void createView() {
    setText("Name Selector");

    names = new ListBox();
    select = new Button("Select");

    HorizontalPanel mainPanel = new HorizontalPanel();
    mainPanel.add(new Label("Name: "));
    mainPanel.add(names);
    mainPanel.add(select);

    setWidget(mainPanel);

  }

}

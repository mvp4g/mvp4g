package com.mvp4g.example.client.product.view;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.product.presenter.AbstractProductPresenter.ProductViewInterface;

public abstract class AbstractProductView
  extends SimplePanel
  implements ProductViewInterface {

  private Button leftButton  = null;
  private Button rightButton = null;

  public HasClickHandlers getLeftButton() {
    return leftButton;
  }

  public HasClickHandlers getRightButton() {
    return rightButton;
  }

  public void createView() {
    leftButton = new Button(getLeftButtonName());
    rightButton = new Button(getRightButtonName());

    Grid grid = new Grid(2,
                         2);
    grid.setText(0,
                 0,
                 "Name :");
    grid.setWidget(0,
                   1,
                   createAndGetNameWidget());

    HorizontalPanel buttons = new HorizontalPanel();
    buttons.add(leftButton);
    buttons.add(rightButton);

    grid.setWidget(1,
                   1,
                   buttons);

    setWidget(grid);
  }

  public void alert(String message) {
    Window.alert(message);
  }

  abstract protected String getLeftButtonName();

  abstract protected String getRightButtonName();

  abstract protected Widget createAndGetNameWidget();

}

package com.mvp4g.example.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.presenter.UserRolePresenter.IUserRoleView;
import com.mvp4g.example.client.widget.impl.MyButton;
import com.mvp4g.example.client.widget.impl.MyListBox;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class UserRoleView
  extends Composite
  implements IUserRoleView {

  private MyListBox selectedRoles = new MyListBox();
  private MyListBox rolesChoices  = new MyListBox();

  private MyButton add    = new MyButton("Add");
  private MyButton remove = new MyButton("Remove");

  public UserRoleView() {

    selectedRoles.setVisibleItemCount(10);
    selectedRoles.setWidth("100%");

    CaptionPanel cp = new CaptionPanel("User Roles");

    HorizontalPanel hp = new HorizontalPanel();
    hp.setSpacing(3);
    hp.add(rolesChoices);
    hp.add(add);
    hp.add(remove);

    VerticalPanel vp = new VerticalPanel();
    vp.setWidth("100%");
    vp.setSpacing(2);
    vp.add(selectedRoles);
    vp.add(hp);

    cp.add(vp);

    initWidget(cp);
  }

  public IWidget getViewWidget() {
    return this;
  }

  public void displayError(String error) {
    Window.alert(error);
  }

  public IButton getAddButton() {
    return add;
  }

  public IButton getRemoveButton() {
    return remove;
  }

  public IListBox getRoleChoiceListBox() {
    return rolesChoices;
  }

  public IListBox getSelectedRolesListBox() {
    return selectedRoles;
  }

  public Widget getMyWidget() {
    return this;
  }

}

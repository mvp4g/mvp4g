package com.mvp4g.example.client.ui.user.role;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.shared.dto.UserBean;

//This presenter illustrates how you can test your presenter
//by creating your own mock (without any external mock library)
@Presenter(view = UserRoleView.class)
public class UserRolePresenter
    extends BasePresenter<IUserRoleView, EmployeeAdminWithGXTEventBus>
    implements IUserRoleView.IUserRolePresenter {

  private UserBean user;

  @Inject
  private UserServiceAsync service;

  public void onStart() {
    eventBus.setRoleView(view.asWidget());
  }

  public void onSelectUser(UserBean user) {
    this.user = user;
    view.showUser(user);
  }

  public void onCreateNewUser(UserBean user) {
    this.user = user;
    view.clear();
    view.disable();
  }

  public void onUnselectUser() {
    view.clear();
    view.disable();
  }

  @Override
  public void doAddRole(String role) {
    user.getRoles()
        .add(role);
    updateUser();
  }

  @Override
  public void doRemoveRole(String role) {
    user.getRoles()
        .remove(role);
    updateUser();
  }

  private void updateUser() {
    service.updateUser(user,
                       new AsyncCallback<UserBean>() {
                         @Override
                         public void onFailure(Throwable caught) {
                           Window.alert("PANIC!");
                         }

                         @Override
                         public void onSuccess(UserBean result) {
                           view.clear();
                           view.disable();
                           eventBus.selectUser(result);
                         }
                       });
  }
}

package com.mvp4g.example.client.ui.user.profile;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.shared.dto.UserBean;


@Presenter(view = UserProfileView.class)
public class UserProfilePresenter
    extends BasePresenter<IUserProfileView, EmployeeAdminWithGXTEventBus>
    implements IUserProfileView.IUserProfilePresenter {

  private UserBean user;
  private UserBean userCopy = new UserBean();

  @Inject
  private UserServiceAsync service;

  @Override
  public void bind() {
  }

  @Override
  public void doCancel() {
    eventBus.unselectUser();
  }

  @Override
  public void doCreateUser(UserBean user) {
    service.createUser(user,
                       new AsyncCallback<UserBean>() {
                         @Override
                         public void onFailure(Throwable caught) {
                           Window.alert("PANIC!");
                         }

                         @Override
                         public void onSuccess(UserBean result) {
                           eventBus.unselectUser();
                           eventBus.showUserList();
                         }
                       });
  }

  @Override
  public void doUpdateUser(UserBean user) {
    service.updateUser(user,
                       new AsyncCallback<UserBean>() {
                         @Override
                         public void onFailure(Throwable caught) {
                           Window.alert("PANIC!");
                         }

                         @Override
                         public void onSuccess(UserBean result) {
                           eventBus.unselectUser();
                           eventBus.showUserList();
                         }
                       });
  }

  public void onStart() {
    eventBus.setProfileView(view.asWidget());
  }

  public void onSelectUser(UserBean user) {
    this.user = user;
    userCopy.copy(user);

    view.showUser(user,
                  false);
  }

  public void onUnselectUser() {
    view.clear();
  }

  public void onCreateNewUser(UserBean user) {
    this.user = user;
    view.clear();
    view.showUser(this.user,
                  true);
  }
}

package com.mvp4g.example.client.ui.user.profile;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.example.shared.dto.UserBean;

/**
 * Created by hoss on 01.08.14.
 */
public interface IUserProfileView
    extends IsWidget {

  public void clear();

  public void showUser(UserBean user,
                       boolean create);

  public interface IUserProfilePresenter {

    public void doCancel();

    public void doCreateUser(UserBean user);

    public void doUpdateUser(UserBean user);

  }
}

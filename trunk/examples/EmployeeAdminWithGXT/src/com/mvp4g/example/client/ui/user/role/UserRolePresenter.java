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
  private boolean enabled = false;

  @Inject
  private UserServiceAsync service;

//	@Override
//	public void bind() {
//		IButton add = view.getAddButton();
//		add.addClickHandler( new ClickHandler() {
//
//			public void onClick( ClickEvent event ) {
//				addRole( view.getRoleChoiceListBox().getSelectedValue() );
//			}
//
//		} );
//
//		IButton remove = view.getRemoveButton();
//		remove.addClickHandler( new ClickHandler() {
//
//			public void onClick( ClickEvent event ) {
//				deleteRole( view.getSelectedRolesListBox().getSelectedValue() );
//			}
//
//		} );
//
//		IListBox selectedRoles = view.getSelectedRolesListBox();
//		selectedRoles.addClickHandler( new ClickHandler() {
//
//			public void onClick( ClickEvent event ) {
//				enableRemoveButton();
//			}
//
//		} );
//		selectedRoles.addKeyUpHandler( new KeyUpHandler() {
//
//			public void onKeyUp( KeyUpEvent event ) {
//				enableRemoveButton();
//			}
//
//		} );
//
//		IListBox rolesChoices = view.getRoleChoiceListBox();
//		rolesChoices.addClickHandler( new ClickHandler() {
//
//			public void onClick( ClickEvent event ) {
//				enabledAddButton();
//			}
//
//		} );
//		rolesChoices.addKeyUpHandler( new KeyUpHandler() {
//
//			public void onKeyUp( KeyUpEvent event ) {
//				enabledAddButton();
//			}
//
//		} );
//		rolesChoices.addItem( "--None Selected--" );
//		for ( String role : ROLES ) {
//			rolesChoices.addItem( role );
//		}
//
//		disable();
//
//	}

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
//                           eventBus.selectUser(result);
                         }
                       });
  }
}

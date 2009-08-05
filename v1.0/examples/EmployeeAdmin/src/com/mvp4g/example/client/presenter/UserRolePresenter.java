package com.mvp4g.example.client.presenter;

import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserRoleViewInterface;

public class UserRolePresenter extends Presenter<UserRoleViewInterface> {

	public void onStart(){
		eventBus.dispatch( EventsEnum.CHANGE_RIGHT_BOTTOM_WIDGET, view.getViewWidget() );
	}
	
	public void onSelectUser(UserBean user){
		//nothing
	}
	
	public void onUserUpdated( UserBean user ) {
		//nothing
	}
	
	public void onUserCreated( UserBean user ) {
		//nothing
	}
	
	public void onCreateNewUser( UserBean user){
		
	}
	
}

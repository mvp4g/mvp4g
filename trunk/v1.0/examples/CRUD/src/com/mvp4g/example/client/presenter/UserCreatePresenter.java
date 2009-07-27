package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvp4g.example.client.service.UserServiceAsync;

public class UserCreatePresenter extends Presenter<UserViewInterface> implements Constants{
	
	private UserServiceAsync userService = null;
	
	@Override
	public void bind(){
		view.getButton().addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				UserBean user = new UserBean();
				user.setFirstName(view.getFirstName().getValue());
				user.setLastName(view.getLastName().getValue());
				createUser( user );
			}
			
		});
	}
	
	void createUser( UserBean user ) {
        AsyncCallback<UserBean> callback = new AsyncCallback<UserBean>() {
            public void onFailure( Throwable caught ) {
                    String details = caught.getMessage();
                    System.out.println( "Error: " + details );
            }

            public void onSuccess( UserBean user ) {
				eventBus.dispatch(USER_CREATED, user);
            }
        };
        userService.create( user, callback );
	}
	
	public void onStart(){
		view.getLastName().setValue("");
		view.getFirstName().setValue("");
		eventBus.dispatch(CHANGE_BODY, view.getViewWidget());
	}
	
	
	public void setUserService( UserServiceAsync userService ) {
		this.userService = userService;
	}

}

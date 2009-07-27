package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.event.Event;
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
				Event e = new Event(USER_CREATED, user);
				eventBus.dispatch(e);
            }
        };
        userService.create( user, callback );
	}
	
	public void onStart(){
		Event e = new Event(CHANGE_BODY, view.getViewWidget() );
		view.getLastName().setValue("");
		view.getFirstName().setValue("");
		eventBus.dispatch(e);
	}
	
	
	public void setUserService( UserServiceAsync userService ) {
		this.userService = userService;
	}

}

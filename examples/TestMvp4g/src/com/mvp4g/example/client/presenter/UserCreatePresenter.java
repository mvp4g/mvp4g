package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.client.view.UserCreateView;

@Presenter( view = UserCreateView.class )
public class UserCreatePresenter extends BasePresenter<UserViewInterface, TestEventBus> {

	private UserServiceAsync userService = null;

	@Override
	public void bind() {
		view.getButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				UserBean user = new UserBean();
				user.setFirstName( view.getFirstName().getValue() );
				user.setLastName( view.getLastName().getValue() );
				createUser( user );
			}

		} );
	}

	void createUser( UserBean user ) {
		AsyncCallback<UserBean> callback = new AsyncCallback<UserBean>() {
			public void onFailure( Throwable caught ) {
				String details = caught.getMessage();
				System.out.println( "Error: " + details );
			}

			public void onSuccess( UserBean user ) {
				eventBus.userCreated( user );
			}
		};
		userService.create( user, callback );
	}

	public void onStart() {
		view.getLastName().setValue( "" );
		view.getFirstName().setValue( "" );
		eventBus.changeBody( view.getViewWidget() );
	}

	@InjectService
	public void setUserService( UserServiceAsync userService ) {
		this.userService = userService;
	}

}

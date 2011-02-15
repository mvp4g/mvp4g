package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.IUserViewInterface;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.client.view.UserCreateView;

@Presenter( view = UserCreateView.class )
public class UserCreatePresenter extends BasePresenter<IUserViewInterface, TestEventBus> {

	@Inject
	private UserServiceAsync userService = null;

	@Override
	public void bind() {
		view.getButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				UserBean user = new UserBean();
				user.setFirstName( view.getFirstName().getText() );
				user.setLastName( view.getLastName().getText() );
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
		view.getLastName().setText( "" );
		view.getFirstName().setText( "" );
		eventBus.changeBody( view.getViewWidget() );
	}

}

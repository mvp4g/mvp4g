package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvp4g.example.client.view.UserDisplayView;

@Presenter( view = UserDisplayView.class )
public class UserDisplayPresenter extends BasePresenter<UserViewInterface, TestEventBus> {

	@Override
	public void bind() {
		view.getButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.start();
			}

		} );
	}

	public void setUserBean( UserBean user ) {
		view.getId().setText( user.getId().toString() );
		view.getLastName().setValue( user.getLastName() );
		view.getFirstName().setValue( user.getFirstName() );
	}

	public void display() {
		eventBus.changeBody( view.getViewWidget() );
		eventBus.displayMessage( "User created" );
	}

	public void onUserCreated( UserBean user ) {
		setUserBean( user );
		display();
	}

}

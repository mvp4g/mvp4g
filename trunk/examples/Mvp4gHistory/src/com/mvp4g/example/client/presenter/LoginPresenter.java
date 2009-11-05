package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.presenter.view_interface.LoginViewInterface;
import com.mvp4g.example.client.view.LoginView;

@Presenter(view=LoginView.class)
public class LoginPresenter extends BasePresenter<LoginViewInterface, MyEventBus> {

	@Override
	public void bind() {
		view.getLoginButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				String username = view.getUserName().getText();
				if ( ( username == null ) || ( username.length() == 0 ) ) {
					eventBus.displayMessage( "Please enter your username" );
				} else {
					Cookies.setCookie( Constants.USERNAME, username );
					eventBus.login( username );
					eventBus.displayMessage( "" );
				}
			}

		} );
	}

	public void onStart() {
		String username = Cookies.getCookie( Constants.USERNAME );
		if ( username == null ) {
			eventBus.changeBottomWidget( view.getViewWidget() );
		} else {
			eventBus.login( username );
		}
	}

}

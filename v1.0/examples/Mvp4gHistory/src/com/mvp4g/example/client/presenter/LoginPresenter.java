package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Cookies;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.presenter.view_interface.LoginViewInterface;

public class LoginPresenter extends Presenter<LoginViewInterface> {

	@Override
	public void bind() {
		view.getLoginButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				String username = view.getUserName().getText();
				if ( ( username == null ) || ( username.length() == 0 ) ) {
					eventBus.dispatch( EventsEnum.DISPLAY_MESSAGE, "Please enter your username" );
				} else {
					Cookies.setCookie( Constants.USERNAME, username );
					eventBus.dispatch( EventsEnum.LOGIN, username );
				}
			}

		} );
	}

	public void onStart() {
		String username = Cookies.getCookie( Constants.USERNAME );
		if ( username == null ) {
			eventBus.dispatch( EventsEnum.CHANGE_BOTTOM_WIDGET, view );
		} else {
			eventBus.dispatch( EventsEnum.LOGIN, username );
		}
	}

}

package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserViewInterface;

public class UserDisplayPresenter extends Presenter<UserViewInterface>
		implements Constants {

	@Override
	public void bind() {
		view.getButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.dispatch(START);
			}

		});
	}

	public void setUserBean(UserBean user) {
		view.getId().setText(user.getId().toString());
		view.getLastName().setValue(user.getLastName());
		view.getFirstName().setValue(user.getFirstName());
	}

	public void display() {
		eventBus.dispatch(CHANGE_BODY, view.getViewWidget());
		eventBus.dispatch(DISPLAY_MESSAGE, "User created");
	}

	public void onUserCreated(UserBean user) {
		setUserBean(user);
		display();
	}

}

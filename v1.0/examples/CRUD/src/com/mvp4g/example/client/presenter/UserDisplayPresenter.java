package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.event.Event;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserViewInterface;

public class UserDisplayPresenter extends Presenter<UserViewInterface> implements Constants{
	
	@Override
	public void bind(){
		view.getButton().addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Event e = new Event(START);
				eventBus.dispatch(e);			
			}
			
		});
	}
	
	public void setUserBean(UserBean user){
		view.getId().setText(user.getId().toString());
		view.getLastName().setValue(user.getLastName());
		view.getFirstName().setValue(user.getFirstName());
	}
	
	public void display(){
		Event e = new Event(CHANGE_BODY, view.getViewWidget() );
		eventBus.dispatch(e);
		
		Event e2 = new Event(DISPLAY_MESSAGE, "User created");
		eventBus.dispatch(e2);
	}
	
	public void onUserCreated(UserBean user){
		setUserBean(user);
		display();		
	}

}

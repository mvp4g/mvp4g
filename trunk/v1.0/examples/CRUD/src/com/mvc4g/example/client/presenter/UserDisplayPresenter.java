package com.mvc4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvc4g.example.client.Constants;
import com.mvc4g.example.client.bean.UserBean;
import com.mvc4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvc4g.example.client.view.UserDisplayView;
import com.mvp4g.client.event.Event;
import com.mvp4g.client.presenter.Presenter;

public class UserDisplayPresenter extends Presenter implements Constants{
	
	private UserViewInterface view = null;
	
	public UserDisplayPresenter(){
		this.view = new UserDisplayView();
		bind();		
	}
	
	public void bind(){
		view.getButton().addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Event e = new Event(START);
				eventBus.dispatch(e);			
			}
			
		});
	}
	
	public void setUserBean(UserBean user){
		view.getLastName().setValue(user.getLastName());
		view.getFirstName().setValue(user.getFirstName());
	}
	
	public void display(){
		Event e = new Event(CHANGE_BODY, view);
		eventBus.dispatch(e);		
	}
	
	public void onCreateUser(UserBean user){
		setUserBean(user);
		display();		
	}

}

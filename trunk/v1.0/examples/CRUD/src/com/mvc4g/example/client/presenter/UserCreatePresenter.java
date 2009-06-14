package com.mvc4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvc4g.example.client.Constants;
import com.mvc4g.example.client.bean.UserBean;
import com.mvc4g.example.client.presenter.view_interface.UserViewInterface;
import com.mvc4g.example.client.view.UserCreateView;
import com.mvp4g.client.Event;
import com.mvp4g.client.Presenter;

public class UserCreatePresenter extends Presenter implements Constants{
	
	private UserViewInterface view = null;	
	
	public UserCreatePresenter(){
		this.view = new UserCreateView();
		bind();		
	}
	
	public void bind(){
		view.getButton().addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				UserBean user = new UserBean();
				user.setFirstName(view.getFirstName().getValue());
				user.setLastName(view.getLastName().getValue());
				Event e1 = new Event(CREATE_USER, user);
				eventBus.dispatch(e1);
				
				Event e2 = new Event(DISPLAY_MESSAGE, "User Created");
				eventBus.dispatch(e2);
			}
			
		});
	}
	
	public void onStart(Object form){
		Event e = new Event(CHANGE_BODY, view);
		view.getLastName().setValue("");
		view.getFirstName().setValue("");
		eventBus.dispatch(e);
	}

}

package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.RootPresenter;
import com.mvp4g.example.client.presenter.UserCreatePresenter;
import com.mvp4g.example.client.presenter.UserDisplayPresenter;

//start view is the view that will be automatically added to the RootPanel/RootLayoutPanel
@Events( startPresenter = RootPresenter.class )
public interface TestEventBus extends EventBus {

	//framework will automatically call onUserCreated method of UserDisplayPresenter instance
	//when the event userCreated is fired
	@Event( handlers = UserDisplayPresenter.class )
	public void userCreated( UserBean user );

	@Event( handlers = RootPresenter.class )
	public void changeBody( Widget widget );

	@Event( handlers = RootPresenter.class )
	public void displayMessage( String message );

	@Start
	@Event( handlers = { RootPresenter.class, UserCreatePresenter.class } )
	public void start();

}

package com.mvp4g.example.client;

import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Start;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.RootTemplatePresenter;
import com.mvp4g.example.client.presenter.UserListPresenter;
import com.mvp4g.example.client.presenter.UserProfilePresenter;
import com.mvp4g.example.client.presenter.UserRolePresenter;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.view.RootTemplateView;

@Events( startView = RootTemplateView.class )
public interface EmployeeAdminWithGXTEventBus extends EventBus {

	@Event( handlers = RootTemplatePresenter.class )
	public void changeTopWidget( MyWidgetInterface widget );

	@Event( handlers = RootTemplatePresenter.class )
	public void changeLeftBottomWidget( MyWidgetInterface widget );

	@Event( handlers = RootTemplatePresenter.class )
	public void changeRightBottomWidget( MyWidgetInterface widget );

	@Event( handlers = { UserProfilePresenter.class, UserRolePresenter.class } )
	public void createNewUser( UserBean user );

	@Event( handlers = { UserProfilePresenter.class, UserRolePresenter.class } )
	public void selectUser( UserBean user );

	@Event( handlers = { UserProfilePresenter.class, UserRolePresenter.class, UserListPresenter.class } )
	public void unselectUser();

	@Event( handlers = UserListPresenter.class )
	public void userCreated( UserBean user );

	@Event( handlers = { UserRolePresenter.class, UserListPresenter.class } )
	public void userUpdated( UserBean user );

	@Start
	@Event( handlers = { UserProfilePresenter.class, UserRolePresenter.class, UserListPresenter.class } )
	public void start();

}

package com.mvp4g.example.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;
import com.mvp4g.example.client.view.RootTemplateView;

@Presenter( view = RootTemplateView.class )
public class RootTemplatePresenter extends BasePresenter<RootTemplateViewInterface, EmployeeAdminWithGXTEventBus> {

	public void onChangeTopWidget( MyWidgetInterface widget ) {
		view.setTopWidget( widget );
	}

	public void onChangeLeftBottomWidget( MyWidgetInterface widget ) {
		view.setLeftBottomWidget( widget );
	}

	public void onChangeRightBottomWidget( MyWidgetInterface widget ) {
		view.setRightBottomWidget( widget );
	}

}

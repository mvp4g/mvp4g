package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.view.RootTemplateView;

@Presenter( view = RootTemplateView.class )
public class RootTemplatePresenter extends BasePresenter<RootTemplateViewInterface, MyEventBus> {

	public void onChangeTopWidget( Widget widget ) {
		view.setTopWidget( widget );
	}

	public void onChangeBottomWidget( Widget widget ) {
		view.setBottomWidget( widget );
	}

	public void onChangeMainWidget( Widget widget ) {
		view.setMainWidget( widget );
	}

	public void onDisplayMessage( String message ) {
		view.getMessageBar().setText( message );
	}

	public void onInit() {
		view.clearMainWidget();
		view.getMessageBar().setText( "" );
	}

}

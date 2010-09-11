package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.presenter.view_interface.IRootViewInterface;
import com.mvp4g.example.client.view.RootView;

@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<IRootViewInterface, TestEventBus> {

	public void onChangeBody( Widget newPage ) {
		view.setBody( newPage );
	}

	public void onDisplayMessage( String message ) {
		view.getMessage().setText( message );
	}

	public void onStart() {
		view.getMessage().setText( "" );
	}

}

package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.presenter.view_interface.RootViewInterface;
import com.mvp4g.example.client.view.RootView;

@Presenter( view = RootView.class )
public class RootPresenter extends BasePresenter<RootViewInterface, TestEventBus> {

	public void onChangeBody( Widget newPage ) {
		Panel body = view.getBody();
		body.clear();
		body.add( newPage );
	}

	public void onDisplayMessage( String message ) {
		view.getMessage().setValue( message );
	}

	public void onStart() {
		view.getMessage().setValue( "" );
	}

}

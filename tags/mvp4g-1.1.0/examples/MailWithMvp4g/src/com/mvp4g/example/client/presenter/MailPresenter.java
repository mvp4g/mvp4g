package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.view.MailView;

@Presenter( view = MailView.class )
public class MailPresenter extends BasePresenter<MailPresenter.IMailView, MailEventBus> {

	public interface IMailView {

		public void setNorthWidget( Widget w );

		public void setMiddleNorthWidget( Widget w );

		public void setMiddleCenterWidget( Widget w );

		public void setMiddleWestWidget( Widget w );

	}

	public void onSetNorth( Widget w ) {
		view.setNorthWidget( w );
	}

	public void onSetMiddleNorth( Widget w ) {
		view.setMiddleNorthWidget( w );
	}

	public void onSetMiddleCenter( Widget w ) {
		view.setMiddleCenterWidget( w );
	}

	public void onSetMiddleWest( Widget w ) {
		view.setMiddleWestWidget( w );
	}

}

package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.presenter.view_interface.AccountViewInterface;
import com.mvp4g.example.client.view.AccountView;

@Presenter( view = AccountView.class )
public class AccountPresenter extends LazyPresenter<AccountViewInterface, MyEventBus> {

	private String username = null;

	@Override
	public void bindView() {
		view.getShowCart().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.displayCart( username );
			}

		} );
	}

	public void onLogin( String username ) {
		this.username = username;
		view.getUsername().setText( username );
		eventBus.changeBottomWidget( view.getViewWidget() );
	}

}

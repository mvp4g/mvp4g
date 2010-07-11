package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.view.AccountView;
import com.mvp4g.example.client.widget.IView;

@Presenter( view = AccountView.class )
public class AccountPresenter extends LazyPresenter<AccountPresenter.AccountViewInterface, MyEventBus> {
	
	public interface AccountViewInterface extends LazyView, IView {

		 HasClickHandlers getShowCart();

		 void setUsername(String username);
		 
	}

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
		view.setUsername( username );
		eventBus.changeBottomWidget( view );
	}

}

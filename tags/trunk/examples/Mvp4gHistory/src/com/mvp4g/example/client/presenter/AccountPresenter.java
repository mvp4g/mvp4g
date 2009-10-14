package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.presenter.view_interface.AccountViewInterface;

public class AccountPresenter extends Presenter<AccountViewInterface> {
	
	private String username = null;
		
	@Override
	public void bind(){
		view.getShowCart().addClickHandler( new ClickHandler(){

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( EventsEnum.DISPLAY_CART, username );				
			}
			
		});
	}
	
	public void onLogin(String username){
		this.username = username;
		view.getUsername().setText( username );
		eventBus.dispatch( EventsEnum.CHANGE_BOTTOM_WIDGET, view );
	}
	
}

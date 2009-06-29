package com.mvc4g.example.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvc4g.example.client.presenter.view_interface.RootViewInterface;
import com.mvc4g.example.client.view.RootView;
import com.mvp4g.client.presenter.Presenter;


public class RootPresenter extends Presenter{
	
	private RootViewInterface view = null;
		
	public RootPresenter(){
		this.view = new RootView();
		RootPanel.get().add(view.getViewWidget());
	}
	
	public void onChangeBody(Widget newPage){
		Panel body = view.getBody();
		body.clear();
		body.add(newPage);		
	}
	
	public void onDisplayMessage(String message){
		view.getMessage().setValue(message);
	}
	
	public void onStart(){
		view.getMessage().setValue("");
	}

}

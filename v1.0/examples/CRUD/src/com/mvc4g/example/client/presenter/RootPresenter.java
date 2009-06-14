package com.mvc4g.example.client.presenter;

import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvc4g.example.client.presenter.view_interface.RootViewInterface;
import com.mvc4g.example.client.view.RootView;
import com.mvp4g.client.Presenter;


public class RootPresenter extends Presenter{
	
	private RootViewInterface view = null;
		
	public RootPresenter(){
		this.view = new RootView();
		RootPanel.get().add(view.getViewWidget());
	}
	
	public void onChangeBody(Object form){
		Panel body = view.getBody();
		body.clear();
		body.add((Widget) form);		
	}
	
	public void onDisplayMessage(Object form){
		view.getMessage().setValue((String) form);
	}
	
	public void onStart(Object form){
		view.getMessage().setValue("");
	}

}

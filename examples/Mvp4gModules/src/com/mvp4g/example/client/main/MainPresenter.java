package com.mvp4g.example.client.main;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = MainView.class)
public class MainPresenter extends
		BasePresenter<MainPresenter.MainViewInterface, MainEventBus> {

	public Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();

	public interface MainViewInterface {

		public HasClickHandlers getCompanyMenu();

		public HasClickHandlers getProductMenu();

		public void setBody(Widget newBody);
		
		public void displayErrorMessage(String error);
		public void setWaitVisible(boolean visible);
	
	}

	public void bind() {
		view.getCompanyMenu().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.goToCompany();
			}
		});
		view.getProductMenu().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.goToProduct();
			}
		});	
		
	}

	public void onChangeBody(Widget w) {
		view.setBody(w);
	}
	
	public void onErrorOnLoad(Throwable reason){
		view.displayErrorMessage(reason.getMessage());
	}
	
	public void onBeforeLoad(){
		view.setWaitVisible(true);
	}
	
	public void onAfterLoad(){
		view.setWaitVisible(false);
	}
	
	

}

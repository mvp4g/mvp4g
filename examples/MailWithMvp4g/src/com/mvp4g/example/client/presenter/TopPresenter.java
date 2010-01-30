package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.view.TopPanel;

@Presenter(view=TopPanel.class)
public class TopPresenter extends
		BasePresenter<TopPresenter.ITopView, MailEventBus> {

	public interface ITopView {
		HasClickHandlers getAboutButton();

		HasClickHandlers getSignOutLink();
		
		Widget getViewWidget();
		
		void showAboutDialog();
		
		void showAlert(String message);
	}

	@Override
	public void bind() {
		view.getAboutButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.showAboutDialog();
			}
		});

		view.getSignOutLink().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				view.showAlert("If this were implemented, you would be signed out now.");
			}
		});
	}
	
	public void onStart(){
		eventBus.setNorth(view.getViewWidget());
	}

}

package com.mvp4g.example.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter(view = MainView.class)
public class MainPresenter extends
		BasePresenter<MainPresenter.MainViewInterface, EventBus> {

	PingService pingService;

	public interface MainViewInterface {
		public Label getLabel();

		public Button getButton();
	}

	@Override
	public void bind() {
		view.getButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				pingService.ping("Hello", new RequestCallback() {

					public void onError(Request arg0, Throwable arg1) {

					}

					public void onResponseReceived(Request arg0, Response resp) {
						view.getLabel().setText(resp.getText());
					}
				});
			}
		});
	}

	@InjectService
	public void setPingService(PingService pingService) {
		this.pingService = pingService;
	}

}

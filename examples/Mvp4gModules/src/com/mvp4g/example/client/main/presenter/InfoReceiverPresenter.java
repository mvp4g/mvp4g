package com.mvp4g.example.client.main.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.main.view.InfoReceiverView;

@Presenter( view = InfoReceiverView.class, multiple = true )
public class InfoReceiverPresenter extends BasePresenter<InfoReceiverPresenter.IInfoReceiverView, MainEventBus> {

	public interface IInfoReceiverView {

		void show();

		void hide();

		void setInfo( String[] info );

		HasClickHandlers getClose();

	}

	@Override
	public void bind() {
		view.getClose().addClickHandler( new ClickHandler() {

			@Override
			public void onClick( ClickEvent event ) {
				view.hide();
				eventBus.removeHandler( InfoReceiverPresenter.this );
			}

		} );
	}

	public void onBroadcastInfo( String[] info ) {
		view.setInfo( info );
		view.show();
	}

}

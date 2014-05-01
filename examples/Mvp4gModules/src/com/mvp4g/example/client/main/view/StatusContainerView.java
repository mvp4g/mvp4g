package com.mvp4g.example.client.main.view;

import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.inject.Inject;
import com.mvp4g.example.client.main.presenter.StatusContainerPresenter.IStatusContainerView;

public class StatusContainerView extends PopupPanel implements IStatusContainerView {

	@Inject
	public StatusContainerView(TimeView time, DateView date){
		VerticalPanel vp = new VerticalPanel();
		vp.add( date );
		vp.add( time );
		setWidget( vp );
		
		setAutoHideEnabled( true );
		setAutoHideOnHistoryEventsEnabled( true );
	}
	
	@Override
	public void showPopup() {
		center();
	}

}

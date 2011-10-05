package com.mvp4g.example.client.time.view;

import com.google.gwt.user.client.ui.PopupPanel;
import com.mvp4g.example.client.time.presenter.TimePresenter.ITimeView;

public class TimeView extends PopupPanel implements ITimeView {

	public TimeView(){
		setAutoHideEnabled( true );
	}
	
	@Override
	public void showTime() {
		getElement().setInnerText( "It's 5 o'clock!" );
		show();
	}

}

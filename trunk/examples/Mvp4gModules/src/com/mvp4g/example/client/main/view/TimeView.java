package com.mvp4g.example.client.main.view;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Singleton;
import com.mvp4g.example.client.main.presenter.TimePresenter.ITimeView;

@Singleton
public class TimeView extends Composite implements ITimeView {

	private Label status = new Label();

	public TimeView() {
		initWidget( status );
	}

	@Override
	public void setTime( Date date ) {
		status.setText( "Time: " + DateTimeFormat.getFormat( PredefinedFormat.TIME_FULL ).format( date ) );
	}

}

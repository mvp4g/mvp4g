package com.mvp4g.example.client.main.view;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DateTimeFormat.PredefinedFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Singleton;
import com.mvp4g.example.client.main.presenter.DatePresenter.IDateView;

@Singleton
public class DateView extends Composite implements IDateView {

	private Label status = new Label();

	public DateView() {
		initWidget( status );
	}

	@Override
	public void setDate( Date date ) {
		status.setText( "Date: " + DateTimeFormat.getFormat( PredefinedFormat.DATE_FULL ).format( date ) );
	}

}

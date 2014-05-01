package com.mvp4g.example.client.company.view;

import com.google.gwt.user.client.ui.PopupPanel;
import com.mvp4g.example.client.company.presenter.CompanyTimePresenter.ICompanyTimeView;

public class CompanyTimeView extends PopupPanel implements ICompanyTimeView {

	public CompanyTimeView(){
		setAutoHideEnabled( true );
	}
	
	@Override
	public void showTime() {
		getElement().setInnerText( "It's 5 o'clock!" );
		show();
	}

}

package com.mvp4g.example.client.presenter.interfaces;

import com.mvp4g.client.view.ReverseViewInterface;


public interface INavBarView extends ReverseViewInterface<INavBarView.INavBarPresenter>{
	
	interface INavBarPresenter {
		void onNewerButtonClick();
		
		void onOlderButtonClick();
	}

	void setNavText( String text );
	
	void setOlderVisible(boolean visible);
	
	void setNewerVisible(boolean visible);
}
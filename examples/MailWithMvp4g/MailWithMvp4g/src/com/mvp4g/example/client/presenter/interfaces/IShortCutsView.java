package com.mvp4g.example.client.presenter.interfaces;

import com.google.gwt.user.client.ui.IsWidget;
import com.mvp4g.example.client.presenter.ShortCutsPresenter.FOLDER_TYPE;

public interface IShortCutsView {

	interface IShortCutsPresenter {
		void onContactClick(int index, IsWidget contactWidget);
	}

	void addContact( String name, int index );

	void addTask( String task );

	void addFolder( FOLDER_TYPE folder );

	void showContactPopup( String name, String email, IsWidget widget );
}

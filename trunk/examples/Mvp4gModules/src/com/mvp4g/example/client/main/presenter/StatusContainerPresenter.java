package com.mvp4g.example.client.main.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.main.StatusSplitter;
import com.mvp4g.example.client.main.view.StatusContainerView;

@Presenter( view = StatusContainerView.class, async = StatusSplitter.class )
public class StatusContainerPresenter extends BasePresenter<StatusContainerPresenter.IStatusContainerView, MainEventBus> {

	public interface IStatusContainerView {

		void showPopup();

	}

	public void onShowStatus( String info) {
		view.showPopup();
	}

}

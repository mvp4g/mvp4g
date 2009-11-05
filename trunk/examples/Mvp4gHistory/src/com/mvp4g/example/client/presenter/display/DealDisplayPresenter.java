package com.mvp4g.example.client.presenter.display;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.presenter.view_interface.display.DealDisplayViewInterface;
import com.mvp4g.example.client.view.display.DealDisplayView;

@Presenter( view = DealDisplayView.class )
public class DealDisplayPresenter extends BasePresenter<DealDisplayViewInterface, MyEventBus> {

	public void onDisplayDeal( DealBean deal ) {
		view.getName().setText( deal.getName() );
		view.getDescription().setText( deal.getDescription() );
		view.getCode().setText( deal.getCode() );
		eventBus.changeMainWidget( view.getViewWidget() );
	}

}

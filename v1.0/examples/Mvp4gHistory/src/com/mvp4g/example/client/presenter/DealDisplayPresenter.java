package com.mvp4g.example.client.presenter;

import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.presenter.view_interface.display.DealDisplayViewInterface;

public class DealDisplayPresenter extends Presenter<DealDisplayViewInterface> {
	
	public void onDisplayDeal( DealBean deal){
		view.getName().setText(deal.getName());
		view.getDescription().setText( deal.getDescription() );
		view.getCode().setText( deal.getCode() );
		eventBus.dispatch( EventsEnum.CHANGE_MAIN_WIDGET, view );
	}

}

package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.view.RootTemplateView;

@Presenter( view = RootTemplateView.class )
public class RootTemplatePresenter extends LazyPresenter<RootTemplateViewInterface, MyEventBus> {

	public void onChangeTopWidget( Widget widget ) {
		view.setTopWidget( widget );
	}

	public void onChangeBottomWidget( Widget widget ) {
		view.setBottomWidget( widget );
	}

	public void onChangeMainWidget( Widget widget ) {
		view.setMainWidget( widget );
	}

	public void onDisplayMessage( String message ) {
		view.getMessageBar().setText( message );
	}

	public void onInit() {
		view.clearMainWidget();
		view.getMessageBar().setText( "" );
	}

	public void onNotFound() {
		eventBus.init();
		view.getMessageBar().setText( "Page not found" );
	}

	public void onDisplayProduct( ProductBean bean ) {
		view.getMessageBar().setText( "" );
	}

	public void onDisplayDeal( DealBean bean ) {
		view.getMessageBar().setText( "" );
	}

	public void onDisplayCart( String username ) {
		view.getMessageBar().setText( "" );
	}

}

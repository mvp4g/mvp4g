package com.mvp4g.example.client.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.MyEventBus;
import com.mvp4g.example.client.bean.DealBean;
import com.mvp4g.example.client.bean.ProductBean;
import com.mvp4g.example.client.view.RootTemplateView;
import com.mvp4g.example.client.widget.IView;

@Presenter( view = RootTemplateView.class )
public class RootTemplatePresenter extends LazyPresenter<RootTemplatePresenter.RootTemplateViewInterface, MyEventBus> {

	public interface RootTemplateViewInterface extends LazyView {

		void setTopWidget( Widget w );

		void clearMainWidget();

		void setMainWidget( Widget w );

		void setBottomWidget( Widget w );

		void setMessage( String message );

	}

	public void onChangeTopWidget( IView top ) {
		view.setTopWidget( top.getViewWidget() );
	}

	public void onChangeBottomWidget( IView bottom ) {
		view.setBottomWidget( bottom.getViewWidget() );
	}

	public void onChangeMainWidget( IView main ) {
		view.setMainWidget( main.getViewWidget() );
	}

	public void onDisplayMessage( String message ) {
		view.setMessage( message );
	}

	public void onInit() {
		view.clearMainWidget();
		view.setMessage( "" );
	}

	public void onNotFound() {
		eventBus.init();
		view.setMessage( "Page not found" );
	}

	public void onDisplayProduct( ProductBean bean ) {
		view.setMessage( "" );
	}

	public void onDisplayDeal( DealBean bean ) {
		view.setMessage( "" );
	}

	public void onDisplayCart( String username ) {
		view.setMessage( "" );
	}

}

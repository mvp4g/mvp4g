package com.mvp4g.example.client.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.view.RootTemplateView;
import com.mvp4g.example.client.widget.interfaces.IWidget;

@Presenter( view = RootTemplateView.class )
public class RootTemplatePresenter extends BasePresenter<RootTemplatePresenter.IRootTemplateView, EmployeeAdminWithGXTEventBus> {
	
	public interface IRootTemplateView {

		 void setTopWidget( IWidget widget );

		 void setLeftBottomWidget( IWidget widget );

		 void setRightBottomWidget( IWidget widget );

	}

	public void onChangeTopWidget( IWidget widget ) {
		view.setTopWidget( widget );
	}

	public void onChangeLeftBottomWidget( IWidget widget ) {
		view.setLeftBottomWidget( widget );
	}

	public void onChangeRightBottomWidget( IWidget widget ) {
		view.setRightBottomWidget( widget );
	}

}

package com.mvp4g.example.client.presenter;

import com.mvp4g.client.presenter.XmlPresenter;
import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class RootTemplatePresenter extends XmlPresenter<RootTemplateViewInterface> {

	public void onChangeTopWidget( MyWidgetInterface widget ) {
		view.setTopWidget( widget );
	}

	public void onChangeLeftBottomWidget( MyWidgetInterface widget ) {
		view.setLeftBottomWidget( widget );
	}

	public void onChangeRightBottomWidget( MyWidgetInterface widget ) {
		view.setRightBottomWidget( widget );
	}

}

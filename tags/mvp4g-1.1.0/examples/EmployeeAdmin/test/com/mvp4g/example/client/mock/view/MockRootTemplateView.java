package com.mvp4g.example.client.mock.view;

import com.mvp4g.example.client.presenter.view_interface.RootTemplateViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public class MockRootTemplateView implements RootTemplateViewInterface {

	private MyWidgetInterface topWidget = null;
	private MyWidgetInterface leftBottomWidget = null;
	private MyWidgetInterface rightBottomWidget = null;

	public MyWidgetInterface getTopWidget() {
		return topWidget;
	}

	public void setTopWidget( MyWidgetInterface topWidget ) {
		this.topWidget = topWidget;
	}

	public MyWidgetInterface getLeftBottomWidget() {
		return leftBottomWidget;
	}

	public void setLeftBottomWidget( MyWidgetInterface leftBottomWidget ) {
		this.leftBottomWidget = leftBottomWidget;
	}

	public MyWidgetInterface getRightBottomWidget() {
		return rightBottomWidget;
	}

	public void setRightBottomWidget( MyWidgetInterface rightBottomWidget ) {
		this.rightBottomWidget = rightBottomWidget;
	}

}

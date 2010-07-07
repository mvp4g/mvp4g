package com.mvp4g.example.client.mock.view;

import com.mvp4g.example.client.presenter.RootTemplatePresenter.IRootTemplateView;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public class MockRootTemplateView implements IRootTemplateView {

	private IWidget topWidget = null;
	private IWidget leftBottomWidget = null;
	private IWidget rightBottomWidget = null;

	public IWidget getTopWidget() {
		return topWidget;
	}

	public void setTopWidget( IWidget topWidget ) {
		this.topWidget = topWidget;
	}

	public IWidget getLeftBottomWidget() {
		return leftBottomWidget;
	}

	public void setLeftBottomWidget( IWidget leftBottomWidget ) {
		this.leftBottomWidget = leftBottomWidget;
	}

	public IWidget getRightBottomWidget() {
		return rightBottomWidget;
	}

	public void setRightBottomWidget( IWidget rightBottomWidget ) {
		this.rightBottomWidget = rightBottomWidget;
	}

}

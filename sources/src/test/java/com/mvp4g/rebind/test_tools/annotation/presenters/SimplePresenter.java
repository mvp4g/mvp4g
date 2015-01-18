package com.mvp4g.rebind.test_tools.annotation.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

@Presenter( view = Object.class )
public class SimplePresenter<V, E extends EventBus>
		extends BasePresenter<V, E> {

	private boolean bindCalled = false;

	public void bind() {
		this.bindCalled = true;
	}

	public boolean isBindCalled() {
		return bindCalled;
	}

}

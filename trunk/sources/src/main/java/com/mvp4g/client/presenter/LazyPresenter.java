package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.LazyView;

public class LazyPresenter<V extends LazyView, E extends EventBus> extends BasePresenter<V, E> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mvp4g.client.presenter.BasePresenter#bind()
	 */
	//Method is final. If you need to overwrite this method, then you shouldn't extend LazyPresenter but BasePresenter.
	@Override
	final public void bind() {
		view.createView();
		createPresenter();
		bindView();
	}

	/**
	 * Bind the view to the presenter once both elements have been created.
	 */
	public void bindView() {

	}

	/**
	 * Called when presenter needs to be built.
	 */
	public void createPresenter() {

	}

}

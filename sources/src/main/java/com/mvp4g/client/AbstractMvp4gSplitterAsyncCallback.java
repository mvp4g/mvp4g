package com.mvp4g.client;

import com.google.gwt.core.client.RunAsyncCallback;

public abstract class AbstractMvp4gSplitterAsyncCallback implements RunAsyncCallback {

	private boolean[] indexesToBuild;

	public AbstractMvp4gSplitterAsyncCallback( boolean[] indexesToBuild ) {
		this.indexesToBuild = indexesToBuild;
	}

	@Override
	public void onFailure( Throwable arg0 ) {
		//do nothing
	}

	@Override
	public void onSuccess() {
		buildPresenters( indexesToBuild );
		afterOnSuccess();
	}

	abstract protected void buildPresenters( boolean[] indexesToBuild );

	abstract protected void afterOnSuccess();

}

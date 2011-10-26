package com.mvp4g.example.client;

import com.google.gwt.core.client.RunAsyncCallback;

public abstract class AbstractMvp4gSplitterAsyncCallback implements RunAsyncCallback {

	//private boolean[] indexesToBuild;

	public AbstractMvp4gSplitterAsyncCallback( ) {
		//this.indexesToBuild = indexesToBuild;
	}

	@Override
	public void onFailure( Throwable arg0 ) {
		//do nothing
	}

	@Override
	public void onSuccess() {
		buildPresenters( );
		afterOnSuccess();
	}

	abstract protected void buildPresenters(  );

	abstract protected void afterOnSuccess();

}

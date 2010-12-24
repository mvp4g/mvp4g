package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;

@Presenter( view = Object.class )
public class Handler1 extends BasePresenter<Object, TestEventBus> {

	public void onEvent1OK() {

	}

	public void onEvent2OK() {

	}
	
	public void onEvent3OK( String attr1, int attr2 ){
		
	}
	
	public void onEventWrongParameters( String attr ){
		
	}

	public void onEventWrongParameters2( String attr, String attr2 ){
		
	}

}

package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.TestEventBus;

@Presenter( view = TestMvp4gView.class )
public class TestMvp4gPresenter1 extends BasePresenter<TestMvp4gPresenter1.TestMvp4gViewInterface, TestEventBus> {

	public interface TestMvp4gViewInterface {

	}

	public void onEvent1( String event ) {
		//do nothing
	}

	public void onEvent2( String event ) {
		//do nothing
	}

	public void onEvent3( String event ) {
		//do nothing
	}

	public void onEvent4( String event ) {
		//do nothing
	}

	public void onEvent5( String event ) {
		//do nothing
	}

	public void onEvent6( String event ) {
		//do nothing
	}

	public void onEvent7( String event ) {
		//do nothing
	}

	public void onEvent8( String event ) {
		//do nothing
	}

	public void onEvent9( String event ) {
		//do nothing
	}

	public void onEvent10( String event ) {
		//do nothing
	}

}

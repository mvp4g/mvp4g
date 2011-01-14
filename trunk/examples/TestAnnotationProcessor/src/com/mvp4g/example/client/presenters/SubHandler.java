package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.view.View;

@Presenter( view = View.class )
public class SubHandler extends AbstractHandler<TestEventBus> {

	public void onEvent2OK() {

	}

}

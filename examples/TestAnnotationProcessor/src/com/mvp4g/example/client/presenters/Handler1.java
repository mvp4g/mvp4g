package com.mvp4g.example.client.presenters;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.TestEventBus;
import com.mvp4g.example.client.view.View1;

@Presenter( view = View1.class )
public class Handler1 extends TestBasePresenter<Handler1.IView1, TestEventBus> {
	
	public interface IView1 {
		
	}

	public void onEvent1OK() {

	}
	
	public void onEventParentOK() {

	}	

	public void onEvent2OK() {

	}
	
	public void onEvent2OK(String param) {

	}
	
	public void onEvent3OK( String attr1, int attr2 ){
		
	}
	
	public void onEventWrongParameters( String attr ){
		
	}

	public void onEventWrongParameters2( String attr, String attr2 ){
		
	}

}

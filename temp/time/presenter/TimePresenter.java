package com.mvp4g.example.client.time.presenter;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.time.TimeEventBus;
import com.mvp4g.example.client.time.view.TimeView;

@Presenter(view = TimeView.class)
public class TimePresenter extends BasePresenter<TimePresenter.ITimeView, TimeEventBus>{
	
	public interface ITimeView {
		void showTime();
	}
	
	public void onShowStatus(){
		view.showTime();
	}
	

}

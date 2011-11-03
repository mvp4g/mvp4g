package com.mvp4g.example.client.company.presenter;

import com.mvp4g.client.SingleSplitter;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.view.CompanyTimeView;

@Presenter( view = CompanyTimeView.class, async = SingleSplitter.class )
public class CompanyTimePresenter extends BasePresenter<CompanyTimePresenter.ICompanyTimeView, CompanyEventBus> {

	public interface ICompanyTimeView {
		void showTime();
	}

	public void onShowStatus(String status) {
		view.showTime();
	}

}

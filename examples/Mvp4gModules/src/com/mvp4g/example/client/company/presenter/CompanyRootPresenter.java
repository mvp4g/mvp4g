package com.mvp4g.example.client.company.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.view.CompanyRootView;

@Presenter(view = CompanyRootView.class)
public class CompanyRootPresenter
		extends
		LazyPresenter<CompanyRootPresenter.CompanyRootViewInterface, CompanyEventBus> {

	public interface CompanyRootViewInterface extends LazyView {

		public void displayText(String text);

		public void setBody(Widget newBody);

	}

	public void onDisplayMessage(String message) {
		view.displayText(message);
	}

	public void onChangeBody(Widget body) {
		view.displayText("");
		view.setBody(body);
	}

}

package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.view.CompanyCreationView;

@Presenter(view = CompanyCreationView.class)
public class CompanyCreationPresenter extends AbstractCompanyPresenter {

	public void onGoToCreation() {
		eventBus.changeBody(view.getViewWidget());
	}

	@Override
	protected void clickOnLeftButton(ClickEvent event) {
		company = new CompanyBean();
		fillBean();
		service.createCompany(company, new AsyncCallback<Void>() {

			public void onSuccess(Void result) {
				eventBus.companyCreated(company);
				eventBus.displayMessage("Creation Succeeded");
			}

			public void onFailure(Throwable caught) {
				eventBus.displayMessage("Creation Failed");
			}
		});
	}

	@Override
	protected void clickOnRightButton(ClickEvent event) {
		clear();
	}

}

package com.mvp4g.example.client.company;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.presenter.CompanyCreationPresenter;
import com.mvp4g.example.client.company.presenter.CompanyDisplayPresenter;
import com.mvp4g.example.client.company.presenter.CompanyEditPresenter;
import com.mvp4g.example.client.company.presenter.CompanyListPresenter;
import com.mvp4g.example.client.company.view.CompanyListView;

@Events(startView=CompanyListView.class, module=CompanyModule.class)
public interface CompanyEventBus extends EventBus {
	
	@Event(handlers=CompanyListPresenter.class)
	public void goToCompany();
	
	@Event(handlers=CompanyCreationPresenter.class)
	public void goToCreation();
	
	@Event(handlers=CompanyListPresenter.class)
	public void goToList();
	
	@Event(handlers=CompanyEditPresenter.class)
	public void goToEdit(CompanyBean company);
	
	@Event(handlers=CompanyDisplayPresenter.class)
	public void goToDisplay(CompanyBean company);
	
	@Event(forwardToParent=true)
	public void displayMessage(String message);

	@Event(forwardToParent=true)
	public void changeBody(Widget body);
	
	@Event(handlers={CompanyListPresenter.class, CompanyDisplayPresenter.class})
	public void companyCreated(CompanyBean newBean);
	
	@Event(handlers=CompanyListPresenter.class)
	public void companyDeleted(CompanyBean newBean);
	

}

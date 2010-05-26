package com.mvp4g.example.client.company;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.company.bean.CompanyBean;
import com.mvp4g.example.client.company.handler.CompanyListHandler;
import com.mvp4g.example.client.company.history.CompanyCreationHistoryConverter;
import com.mvp4g.example.client.company.history.CompanyHistoryConverter;
import com.mvp4g.example.client.company.presenter.CompanyCreationPresenter;
import com.mvp4g.example.client.company.presenter.CompanyDisplayPresenter;
import com.mvp4g.example.client.company.presenter.CompanyEditPresenter;
import com.mvp4g.example.client.company.presenter.CompanyListPresenter;
import com.mvp4g.example.client.company.presenter.CompanyNameSelectorPresenter;
import com.mvp4g.example.client.company.view.CompanyListView;
import com.mvp4g.example.client.product.presenter.ProductCreationPresenter;

@Events( startView = CompanyListView.class, module = CompanyModule.class )
@Debug( logLevel = LogLevel.DETAILED )
public interface CompanyEventBus extends EventBus {

	@Event( handlers = CompanyListPresenter.class )
	public void goToCompany( int start, int end );

	@Event( handlers = CompanyCreationPresenter.class, activate = CompanyCreationPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class }, historyConverter = CompanyCreationHistoryConverter.class, historyName = "create" )
	public void goToCreation();

	@Event( handlers = CompanyListPresenter.class )
	public void goToList();

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for activate
	@Event( handlers = CompanyEditPresenter.class, activate = { CompanyEditPresenter.class, ProductCreationPresenter.class }, deactivate = {
			CompanyCreationPresenter.class, CompanyDisplayPresenter.class } )
	public void goToEdit( CompanyBean company );

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for deactivate
	@Event( handlers = CompanyDisplayPresenter.class, historyConverter = CompanyHistoryConverter.class, activate = CompanyDisplayPresenter.class, deactivate = {
			CompanyCreationPresenter.class, CompanyEditPresenter.class, ProductCreationPresenter.class }, historyName = "" )
	public void goToDisplay( CompanyBean company );

	@Event( forwardToParent = true )
	public void displayMessage( String message );

	@Event( forwardToParent = true )
	public void changeBody( Widget body );

	@Event( forwardToParent = true )
	public void selectCompanyMenu();

	//CompanyCreationPresenter deactivate itself (see clickOnLeftButton method of this class)
	@Event( handlers = { CompanyListPresenter.class, CompanyDisplayPresenter.class }, activate = CompanyDisplayPresenter.class, deactivate = CompanyEditPresenter.class )
	public void companyCreated( CompanyBean newBean );

	@Event( handlers = CompanyListPresenter.class )
	public void companyDeleted( CompanyBean newBean );

	@Event( handlers = CompanyNameSelectorPresenter.class )
	public void displayNameSelector();

	@Event( handlers = { CompanyCreationPresenter.class, CompanyEditPresenter.class, CompanyDisplayPresenter.class } )
	public void nameSelected( String name );

	@Event( handlers = CompanyListPresenter.class )
	public void companyListRetrieved( List<CompanyBean> companies );
	
	@Event(handlers = CompanyListHandler.class)
	public void getCompanyList(final int start, int end );

}

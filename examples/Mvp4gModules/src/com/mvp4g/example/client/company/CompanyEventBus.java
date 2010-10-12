package com.mvp4g.example.client.company;

import java.util.List;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.Forward;
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
import com.mvp4g.example.client.company.presenter.CompanyRowPresenter;
import com.mvp4g.example.client.company.view.CompanyListView;
import com.mvp4g.example.client.product.presenter.ProductCreationPresenter;

@Events( startView = CompanyListView.class, module = CompanyModule.class )
@Debug( logLevel = LogLevel.DETAILED )
@Filters( filterClasses = CompanyEventFilter.class, filterForward = false )
public interface CompanyEventBus extends EventBus {

	/* Navigation Events */
	@Event( handlers = CompanyListPresenter.class, activate = CompanyRowPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class, CompanyCreationPresenter.class }, navigationEvent = true )
	public void goToCompany( int start, int end );

	@Event( handlers = CompanyListPresenter.class, activate = CompanyRowPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class, CompanyCreationPresenter.class } )
	public void backToList();

	@Event( handlers = CompanyCreationPresenter.class, activate = CompanyCreationPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class }, historyConverter = CompanyCreationHistoryConverter.class, historyName = "create", navigationEvent = true )
	public void goToCreation();

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for deactivate
	@Event( handlers = CompanyDisplayPresenter.class, historyConverter = CompanyHistoryConverter.class, activate = CompanyDisplayPresenter.class, deactivate = {
			CompanyCreationPresenter.class, CompanyEditPresenter.class, ProductCreationPresenter.class, CompanyRowPresenter.class }, historyName = "", navigationEvent = true )
	public void goToDisplay( CompanyBean company );

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for activate
	@Event( handlers = CompanyEditPresenter.class, activate = { CompanyEditPresenter.class, ProductCreationPresenter.class }, deactivate = {
			CompanyCreationPresenter.class, CompanyDisplayPresenter.class, CompanyRowPresenter.class }, navigationEvent = true )
	public void goToEdit( CompanyBean company );

	/* Business Events */
	@Event( handlers = CompanyListPresenter.class )
	public void companyCreated( CompanyBean newBean );

	@Event( handlers = CompanyListPresenter.class )
	public void companyDeleted( CompanyBean newBean );

	@Event( handlers = CompanyRowPresenter.class )
	public void companyUpdated( CompanyBean newBean );

	@Event( handlers = CompanyNameSelectorPresenter.class )
	public void displayNameSelector();

	@Event( handlers = { CompanyCreationPresenter.class, CompanyEditPresenter.class, CompanyDisplayPresenter.class, CompanyRowPresenter.class } )
	public void nameSelected( String name );

	@Event( handlers = CompanyListPresenter.class )
	public void companyListRetrieved( List<CompanyBean> companies );

	@Event( handlers = CompanyListHandler.class )
	public void getCompanyList( final int start, int end );

	@Forward
	@Event( handlers = CompanyListPresenter.class )
	public void forward();

	@Event( forwardToParent = true )
	public void displayMessage( String message );

	@Event( forwardToParent = true )
	public void changeBody( Widget body );

	@Event( forwardToParent = true )
	public void selectCompanyMenu();

}

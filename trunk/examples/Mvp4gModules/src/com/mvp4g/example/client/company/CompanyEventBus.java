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
	void goToCompany( int start, int end );

	@Event( handlers = CompanyListPresenter.class, activate = CompanyRowPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class, CompanyCreationPresenter.class } )
	void backToList();

	@Event( handlers = CompanyCreationPresenter.class, activate = CompanyCreationPresenter.class, deactivate = { CompanyEditPresenter.class,
			CompanyDisplayPresenter.class }, historyConverter = CompanyCreationHistoryConverter.class, historyName = "create", navigationEvent = true )
	String goToCreation();

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for deactivate
	@Event( handlers = CompanyDisplayPresenter.class, historyConverter = CompanyHistoryConverter.class, activate = CompanyDisplayPresenter.class, deactivate = {
			CompanyCreationPresenter.class, CompanyEditPresenter.class, ProductCreationPresenter.class, CompanyRowPresenter.class }, historyName = "", navigationEvent = true )
	void goToDisplay( CompanyBean company );

	//I have ProductCreationPresenter.class here just to test if Mvp4g ignores useless presenter for activate
	@Event( handlers = CompanyEditPresenter.class, activate = { CompanyEditPresenter.class, ProductCreationPresenter.class }, deactivate = {
			CompanyCreationPresenter.class, CompanyDisplayPresenter.class, CompanyRowPresenter.class }, navigationEvent = true )
	void goToEdit( CompanyBean company );

	/* Business Events */
	@Event( handlers = CompanyListPresenter.class, activate = CompanyListPresenter.class )
	void companyCreated( CompanyBean newBean );

	@Event( handlers = CompanyListPresenter.class, activate = CompanyListPresenter.class )
	void companyDeleted( CompanyBean newBean );

	@Event( handlers = CompanyRowPresenter.class, activate = CompanyRowPresenter.class )
	void companyUpdated( CompanyBean newBean );

	@Event( handlers = CompanyNameSelectorPresenter.class )
	void displayNameSelector();

	@Event( handlers = { CompanyCreationPresenter.class, CompanyEditPresenter.class, CompanyDisplayPresenter.class, CompanyRowPresenter.class } )
	void nameSelected( String name );

	@Event( handlers = CompanyListPresenter.class )
	void companyListRetrieved( List<CompanyBean> companies );

	@Event( handlers = CompanyListHandler.class )
	void getCompanyList( final int start, int end );

	@Forward
	@Event( handlers = CompanyListPresenter.class )
	void forward();

	@Event( forwardToParent = true )
	void displayMessage( String message );

	@Event( forwardToParent = true )
	void changeBody( Widget body );

	@Event( forwardToParent = true )
	void selectCompanyMenu();

	@Event( handlers = CompanyListPresenter.class, passive = true )
	void hasBeenThere();

	@Event( forwardToParent = true )
	String goToProduct( Integer start, Integer end );

}

package com.mvp4g.example.client.main;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.DisplayChildModuleView;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.history.ClearHistory;
import com.mvp4g.example.client.Mvp4gGinModule;
import com.mvp4g.example.client.company.CompanyModule;
import com.mvp4g.example.client.main.historyConverter.MenuHistoryConverter;
import com.mvp4g.example.client.product.ProductModule;

@Events( startView = MainView.class, historyOnStart = true, debug = true, ginModule=Mvp4gGinModule.class )
@ChildModules( { @ChildModule( moduleClass = CompanyModule.class ),
		@ChildModule( moduleClass = ProductModule.class, async = false, autoDisplay = false ) } )
public interface MainEventBus extends EventBusWithLookup {

	@Event( modulesToLoad = CompanyModule.class, historyConverter = MenuHistoryConverter.class, handlers = MainPresenter.class, historyName="companies" )
	public void goToCompany(int start, int end);

	//use Integer instead of int here just to test passing object, in real project, you should have int
	@Event( modulesToLoad = ProductModule.class, historyConverter = MenuHistoryConverter.class, handlers = MainPresenter.class )
	public void goToProduct(Integer start, Integer end);

	@DisplayChildModuleView( CompanyModule.class )
	@Event( handlers = MainPresenter.class )
	public void changeBody( Widget newBody );

	@LoadChildModuleError
	@Event( handlers = MainPresenter.class )
	public void errorOnLoad( Throwable reason );

	@BeforeLoadChildModule
	@Event( handlers = MainPresenter.class )
	public void beforeLoad();

	@AfterLoadChildModule
	@Event( handlers = MainPresenter.class )
	public void afterLoad();

	@Event( handlers = MainPresenter.class )
	public void displayMessage( String message );

	@InitHistory
	@Event
	public void onStart();

	@Event( handlers = MainPresenter.class )
	public void selectProductMenu();

	@Event( handlers = MainPresenter.class )
	public void selectCompanyMenu();

	@Event( handlers = MainPresenter.class, historyConverter = ClearHistory.class )
	public void clearHistory();
	
}

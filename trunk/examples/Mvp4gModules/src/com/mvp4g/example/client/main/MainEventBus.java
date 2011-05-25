package com.mvp4g.example.client.main;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Debug;
import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.Filters;
import com.mvp4g.client.annotation.InitHistory;
import com.mvp4g.client.annotation.PlaceService;
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
import com.mvp4g.example.client.main.presenter.InfoReceiverPresenter;
import com.mvp4g.example.client.main.presenter.MainPresenter;
import com.mvp4g.example.client.main.view.MainView;
import com.mvp4g.example.client.product.ProductModule;
import com.mvp4g.example.client.util.HasBeenThereHandler;

@Events( startView = MainView.class, historyOnStart = true, ginModules = Mvp4gGinModule.class )
@Debug( logLevel = LogLevel.DETAILED, logger = CustomLogger.class )
@ChildModules( { @ChildModule( moduleClass = CompanyModule.class ),
		@ChildModule( moduleClass = ProductModule.class, async = false, autoDisplay = false ) } )
@Filters( filterClasses = {}, forceFilters = true )
@PlaceService( CustomPlaceService.class )
public interface MainEventBus extends EventBusWithLookup {

	/* Navigation events */
	@Event( modulesToLoad = CompanyModule.class, historyConverter = MenuHistoryConverter.class, handlers = MainPresenter.class, name = "companies", navigationEvent = true )
	void goToCompany( int start, int end );

	//use Integer instead of int here just to test passing object, in real project, you should have int
	@Event( modulesToLoad = ProductModule.class, historyConverter = MenuHistoryConverter.class, handlers = MainPresenter.class, navigationEvent = true )
	String goToProduct( Integer start, Integer end );

	/* Business events */
	@DisplayChildModuleView( CompanyModule.class )
	@Event( handlers = MainPresenter.class )
	void changeBody( Widget newBody );

	@LoadChildModuleError
	@Event( handlers = MainPresenter.class )
	void errorOnLoad( Throwable reason );

	@BeforeLoadChildModule
	@Event( handlers = MainPresenter.class )
	void beforeLoad();

	@AfterLoadChildModule
	@Event( handlers = MainPresenter.class )
	void afterLoad();

	@Event( handlers = MainPresenter.class )
	void displayMessage( String message );

	@InitHistory
	@Event
	void start();

	@Event( handlers = MainPresenter.class )
	void selectProductMenu();

	@Event( handlers = MainPresenter.class )
	void selectCompanyMenu();

	@Event( handlers = MainPresenter.class, historyConverter = ClearHistory.class )
	void clearHistory();

	@Event( broadcastTo = HasBeenThereHandler.class, passive = true )
	void hasBeenThere();

	//this event is just here to validate array
	@Event( broadcastTo = HasBeenThereHandler.class, passive = true, generate = InfoReceiverPresenter.class )
	void broadcastInfo( String[] info );

}

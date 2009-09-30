package com.mvp4g.example.client.lib;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.mvp4g.client.Mvp4gStarter;
import com.mvp4g.client.event.Command;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.example.client.Service;
import com.mvp4g.example.client.ServiceAsync;
import com.mvp4g.example.client.history.DealHistoryConverter;
import com.mvp4g.example.client.history.ProductHistoryConverter;
import com.mvp4g.example.client.history.ShowCartConverter;
import com.mvp4g.example.client.presenter.AccountPresenter;
import com.mvp4g.example.client.presenter.CartDisplayPresenter;
import com.mvp4g.example.client.presenter.DealDisplayPresenter;
import com.mvp4g.example.client.presenter.LoginPresenter;
import com.mvp4g.example.client.presenter.ProductDisplayPresenter;
import com.mvp4g.example.client.presenter.RootTemplatePresenter;
import com.mvp4g.example.client.presenter.TopBarPresenter;
import com.mvp4g.example.client.view.AccountView;
import com.mvp4g.example.client.view.CartDisplayView;
import com.mvp4g.example.client.view.DealDisplayView;
import com.mvp4g.example.client.view.LoginView;
import com.mvp4g.example.client.view.ProductDisplayView;
import com.mvp4g.example.client.view.RootTemplateView;
import com.mvp4g.example.client.view.TopBarView;



public class MyMvp4gStarter implements Mvp4gStarter {

	public void start() {
		EventBus eventBus = new EventBus();

		//Create the views
		final RootTemplateView rootTemplateView = new RootTemplateView();
		final LoginView loginView = new LoginView();
		final TopBarView topBarView = new TopBarView();
		final DealDisplayView dealDisplayView = new DealDisplayView();
		final AccountView accountView = new AccountView();
		final ProductDisplayView productDisplayView = new ProductDisplayView();
		final CartDisplayView cartDisplayView = new CartDisplayView();

		//Create the services
		final ServiceAsync service = GWT.create( Service.class );

		//Create the presenters
		final TopBarPresenter topBar = new TopBarPresenter();
		topBar.setEventBus( eventBus );
		topBar.setView( topBarView );
		topBar.setService( service );
		final DealDisplayPresenter dealDisplay = new DealDisplayPresenter();
		dealDisplay.setEventBus( eventBus );
		dealDisplay.setView( dealDisplayView );
		final CartDisplayPresenter cartDisplay = new CartDisplayPresenter();
		cartDisplay.setEventBus( eventBus );
		cartDisplay.setView( cartDisplayView );
		cartDisplay.setService( service );
		final ProductDisplayPresenter productDisplay = new ProductDisplayPresenter();
		productDisplay.setEventBus( eventBus );
		productDisplay.setView( productDisplayView );
		final LoginPresenter loginPresenter = new LoginPresenter();
		loginPresenter.setEventBus( eventBus );
		loginPresenter.setView( loginView );
		final AccountPresenter account = new AccountPresenter();
		account.setEventBus( eventBus );
		account.setView( accountView );
		final RootTemplatePresenter rootTemplate = new RootTemplatePresenter();
		rootTemplate.setEventBus( eventBus );
		rootTemplate.setView( rootTemplateView );

		//NEW
		final PlaceService placeService = new PlaceService( eventBus );

		//NEW generate the converters
		final ProductHistoryConverter conv1 = new ProductHistoryConverter();
		conv1.setService( service );
		final DealHistoryConverter conv2 = new DealHistoryConverter();
		conv2.setService( service );
		final ShowCartConverter conv3 = new ShowCartConverter();
		
		//Create the events
		Command cmddisplayProduct = new Command<com.mvp4g.example.client.bean.ProductBean>() {
			public void execute( com.mvp4g.example.client.bean.ProductBean form ) {
				//NEW
				placeService.place( "displayProduct", form );

				productDisplay.onDisplayProduct( form );
				topBar.onDisplayProduct( form );

			}
		};
		eventBus.addEvent( "displayProduct", cmddisplayProduct );
		//NEW
		placeService.addConverter( "displayProduct", conv1 );

		Command cmdstart = new Command<java.lang.Object>() {
			public void execute( java.lang.Object form ) {
				topBar.onStart();
				loginPresenter.onStart();				
			}
		};
		eventBus.addEvent( "start", cmdstart );
		
		Command cmdinit = new Command<java.lang.Object>() {
			public void execute( java.lang.Object form ) {
				rootTemplate.onInit();		
				topBar.onInit();
			}
		};
		eventBus.addEvent( "init", cmdinit );
		
		Command cmdlogin = new Command<java.lang.String>() {
			public void execute( java.lang.String form ) {
				account.onLogin( form );
			}
		};
		eventBus.addEvent( "login", cmdlogin );
		Command cmddisplayCart = new Command<String>() {
			public void execute( String form ) {
				
				//NEW
				placeService.place( "displayCart", form );
				
				cartDisplay.onDisplayCart( form );
			}
		};
		eventBus.addEvent( "displayCart", cmddisplayCart );
		
		//NEW
		placeService.addConverter( "displayCart", conv3 );
		
		Command cmdchangeTopWidget = new Command<com.google.gwt.user.client.ui.Widget>() {
			public void execute( com.google.gwt.user.client.ui.Widget form ) {
				rootTemplate.onChangeTopWidget( form );
			}
		};
		eventBus.addEvent( "changeTopWidget", cmdchangeTopWidget );
		Command cmddisplayDeal = new Command<com.mvp4g.example.client.bean.DealBean>() {
			public void execute( com.mvp4g.example.client.bean.DealBean form ) {
				//NEW
				placeService.place( "displayDeal", form );
				
				dealDisplay.onDisplayDeal( form );
				topBar.onDisplayDeal( form );
			}
		};
		
		//NEW
		placeService.addConverter( "displayDeal", conv2 );		
		
		eventBus.addEvent( "displayDeal", cmddisplayDeal );
		Command cmdchangeMainWidget = new Command<com.google.gwt.user.client.ui.Widget>() {
			public void execute( com.google.gwt.user.client.ui.Widget form ) {
				rootTemplate.onChangeMainWidget( form );
			}
		};
		eventBus.addEvent( "changeMainWidget", cmdchangeMainWidget );
		Command cmddisplayMessage = new Command<java.lang.String>() {
			public void execute( java.lang.String form ) {
				rootTemplate.onDisplayMessage( form );
			}
		};
		eventBus.addEvent( "displayMessage", cmddisplayMessage );
		Command cmdchangeBottomWidget = new Command<com.google.gwt.user.client.ui.Widget>() {
			public void execute( com.google.gwt.user.client.ui.Widget form ) {
				rootTemplate.onChangeBottomWidget( form );
			}
		};
		eventBus.addEvent( "changeBottomWidget", cmdchangeBottomWidget );
		RootPanel.get().add( rootTemplateView );

		//NEW
		placeService.setInitEvent( "init" );
		
		eventBus.dispatch( "start" );
		History.fireCurrentHistoryState();
		
	};

}

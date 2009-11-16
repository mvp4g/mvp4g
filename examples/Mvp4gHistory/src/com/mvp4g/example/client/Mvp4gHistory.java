package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gStarter;

public class Mvp4gHistory implements EntryPoint {

	public void onModuleLoad() {
		Mvp4gStarter starter = GWT.create( Mvp4gStarter.class );
		//Mvp4gStarter starter = new MyMvp4gStarter();
		starter.start();

	}
	
}

/*
class Mvp4gStarterImpl implements Mvp4gStarter {
	
	private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.MyEventBus {
	}

	public void start() {
		final com.mvp4g.example.client.view.LoginView com_mvp4g_example_client_presenter_LoginPresenterView = new com.mvp4g.example.client.view.LoginView();
		final com.mvp4g.example.client.view.AccountView com_mvp4g_example_client_presenter_AccountPresenterView = new com.mvp4g.example.client.view.AccountView();
		final com.mvp4g.example.client.view.display.CartDisplayView com_mvp4g_example_client_presenter_display_CartDisplayPresenterView = new com.mvp4g.example.client.view.display.CartDisplayView();
		final com.mvp4g.example.client.view.TopBarView com_mvp4g_example_client_presenter_TopBarPresenterView = new com.mvp4g.example.client.view.TopBarView();
		final com.mvp4g.example.client.view.RootTemplateView com_mvp4g_example_client_presenter_RootTemplatePresenterView = new com.mvp4g.example.client.view.RootTemplateView();
		final com.mvp4g.example.client.view.display.DealDisplayView com_mvp4g_example_client_presenter_display_DealDisplayPresenterView = new com.mvp4g.example.client.view.display.DealDisplayView();
		final com.mvp4g.example.client.view.display.ProductDisplayView com_mvp4g_example_client_presenter_display_ProductDisplayPresenterView = new com.mvp4g.example.client.view.display.ProductDisplayView();

		final com.mvp4g.example.client.ServiceAsync com_mvp4g_example_client_Service = GWT.create( com.mvp4g.example.client.Service.class );

		final PlaceService<com.mvp4g.example.client.MyEventBus> placeService = new PlaceService<com.mvp4g.example.client.MyEventBus>() {
			protected void sendInitEvent() {
				getEventBus().init();
			}
		};
		final com.mvp4g.example.client.history.ShowCartConverter com_mvp4g_example_client_history_ShowCartConverter = new com.mvp4g.example.client.history.ShowCartConverter();
		final com.mvp4g.example.client.history.DealHistoryConverter com_mvp4g_example_client_history_DealHistoryConverter = new com.mvp4g.example.client.history.DealHistoryConverter();
		com_mvp4g_example_client_history_DealHistoryConverter.setService( com_mvp4g_example_client_Service );
		final com.mvp4g.example.client.history.ProductHistoryConverter com_mvp4g_example_client_history_ProductHistoryConverter = new com.mvp4g.example.client.history.ProductHistoryConverter();
		com_mvp4g_example_client_history_ProductHistoryConverter.setService( com_mvp4g_example_client_Service );

		final com.mvp4g.example.client.presenter.LoginPresenter com_mvp4g_example_client_presenter_LoginPresenter = new com.mvp4g.example.client.presenter.LoginPresenter();
		com_mvp4g_example_client_presenter_LoginPresenter.setView( com_mvp4g_example_client_presenter_LoginPresenterView );
		final com.mvp4g.example.client.presenter.display.CartDisplayPresenter com_mvp4g_example_client_presenter_display_CartDisplayPresenter = new com.mvp4g.example.client.presenter.display.CartDisplayPresenter();
		com_mvp4g_example_client_presenter_display_CartDisplayPresenter.setView( com_mvp4g_example_client_presenter_display_CartDisplayPresenterView );
		com_mvp4g_example_client_presenter_display_CartDisplayPresenter.setService( com_mvp4g_example_client_Service );
		final com.mvp4g.example.client.presenter.AccountPresenter com_mvp4g_example_client_presenter_AccountPresenter = new com.mvp4g.example.client.presenter.AccountPresenter();
		com_mvp4g_example_client_presenter_AccountPresenter.setView( com_mvp4g_example_client_presenter_AccountPresenterView );
		final com.mvp4g.example.client.presenter.display.ProductDisplayPresenter com_mvp4g_example_client_presenter_display_ProductDisplayPresenter = new com.mvp4g.example.client.presenter.display.ProductDisplayPresenter();
		com_mvp4g_example_client_presenter_display_ProductDisplayPresenter
				.setView( com_mvp4g_example_client_presenter_display_ProductDisplayPresenterView );
		final com.mvp4g.example.client.presenter.TopBarPresenter com_mvp4g_example_client_presenter_TopBarPresenter = new com.mvp4g.example.client.presenter.TopBarPresenter();
		com_mvp4g_example_client_presenter_TopBarPresenter.setView( com_mvp4g_example_client_presenter_TopBarPresenterView );
		com_mvp4g_example_client_presenter_TopBarPresenter.setService( com_mvp4g_example_client_Service );
		final com.mvp4g.example.client.presenter.RootTemplatePresenter com_mvp4g_example_client_presenter_RootTemplatePresenter = new com.mvp4g.example.client.presenter.RootTemplatePresenter();
		com_mvp4g_example_client_presenter_RootTemplatePresenter.setView( com_mvp4g_example_client_presenter_RootTemplatePresenterView );
		final com.mvp4g.example.client.presenter.display.DealDisplayPresenter com_mvp4g_example_client_presenter_display_DealDisplayPresenter = new com.mvp4g.example.client.presenter.display.DealDisplayPresenter();
		com_mvp4g_example_client_presenter_display_DealDisplayPresenter.setView( com_mvp4g_example_client_presenter_display_DealDisplayPresenterView );

		AbstractEventBus eventBus = new AbstractEventBus() {
			public void init() {
				com_mvp4g_example_client_presenter_RootTemplatePresenter.onInit();
				com_mvp4g_example_client_presenter_TopBarPresenter.onInit();
			}

			public void displayProduct( com.mvp4g.example.client.bean.ProductBean form ) {
				place( placeService, "displayProduct", form );
				com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.onDisplayProduct( form );
				com_mvp4g_example_client_presenter_TopBarPresenter.onDisplayProduct( form );
			}

			public void start() {
				com_mvp4g_example_client_presenter_TopBarPresenter.onStart();
				com_mvp4g_example_client_presenter_LoginPresenter.onStart();
			}

			public void login( java.lang.String form ) {
				com_mvp4g_example_client_presenter_AccountPresenter.onLogin( form );
			}

			public void displayCart( java.lang.String form ) {
				place( placeService, "displayCart", form );
				com_mvp4g_example_client_presenter_display_CartDisplayPresenter.onDisplayCart( form );
			}

			public void changeTopWidget( com.google.gwt.user.client.ui.Widget form ) {
				com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeTopWidget( form );
			}

			public void displayDeal( com.mvp4g.example.client.bean.DealBean form ) {
				place( placeService, "displayDeal", form );
				com_mvp4g_example_client_presenter_display_DealDisplayPresenter.onDisplayDeal( form );
				com_mvp4g_example_client_presenter_TopBarPresenter.onDisplayDeal( form );
			}

			public void changeMainWidget( com.google.gwt.user.client.ui.Widget form ) {
				com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeMainWidget( form );
			}

			public void displayMessage( java.lang.String form ) {
				com_mvp4g_example_client_presenter_RootTemplatePresenter.onDisplayMessage( form );
			}

			public void changeBottomWidget( com.google.gwt.user.client.ui.Widget form ) {
				com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeBottomWidget( form );
			}
		};
		placeService.addConverter( "displayProduct", com_mvp4g_example_client_history_ProductHistoryConverter );
		placeService.addConverter( "displayCart", com_mvp4g_example_client_history_ShowCartConverter );
		placeService.addConverter( "displayDeal", com_mvp4g_example_client_history_DealHistoryConverter );
		com_mvp4g_example_client_presenter_LoginPresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_display_CartDisplayPresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_AccountPresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_TopBarPresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_RootTemplatePresenter.setEventBus( eventBus );
		com_mvp4g_example_client_presenter_display_DealDisplayPresenter.setEventBus( eventBus );
		placeService.setEventBus( eventBus );
		RootPanel.get().add( com_mvp4g_example_client_presenter_RootTemplatePresenterView );
		eventBus.start();
		History.fireCurrentHistoryState();
	};
}*/

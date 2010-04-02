package com.mvp4g.example.client.main;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.History;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.history.PlaceService;
import com.mvp4g.example.client.Mvp4gGinModule;
import com.mvp4g.example.client.main.historyConverter.MenuHistoryConverter;

public class Mvp4gModuleImpl implements Mvp4gModule {

	private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBusWithLookUp implements com.mvp4g.example.client.main.MainEventBus {
	}

	@GinModules( Mvp4gGinModule.class )
	public interface Mvp4gGinjector extends Ginjector {

		MainPresenter getcom_mvp4g_example_client_main_MainPresenter();
		MainView getcom_mvp4g_example_client_main_MainView();
		MenuHistoryConverter getcom_mvp4g_example_client_main_historyConverter_MenuHistoryConverter();

	}

	private Object startView = null;
	protected AbstractEventBus eventBus = null;
	protected Mvp4gModule itself = this;
	private PlaceService placeService = null;
	public java.util.Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new java.util.HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();

	private void loadcom_mvp4g_example_client_company_CompanyModule( final Mvp4gEventPasser passer ) {
		eventBus.beforeLoad();
		GWT.runAsync( new com.google.gwt.core.client.RunAsyncCallback() {
			public void onSuccess() {
				eventBus.afterLoad();
				com.mvp4g.example.client.company.CompanyModule newModule = (com.mvp4g.example.client.company.CompanyModule)modules
						.get( com.mvp4g.example.client.company.CompanyModule.class );
				if ( newModule == null ) {
					newModule = GWT.create( com.mvp4g.example.client.company.CompanyModule.class );
					modules.put( com.mvp4g.example.client.company.CompanyModule.class, newModule );
					newModule.setParentModule( itself );
					newModule.createAndStartModule();
				}
				eventBus.changeBody( (com.google.gwt.user.client.ui.Widget)newModule.getStartView() );
				if ( passer != null )
					passer.pass( newModule );
			}

			public void onFailure( Throwable reason ) {
				eventBus.afterLoad();
				eventBus.errorOnLoad( reason );
			}
		} );
	}

	private void loadcom_mvp4g_example_client_product_ProductModule( final Mvp4gEventPasser passer ) {
		com.mvp4g.example.client.product.ProductModule newModule = (com.mvp4g.example.client.product.ProductModule)modules
				.get( com.mvp4g.example.client.product.ProductModule.class );
		if ( newModule == null ) {
			newModule = GWT.create( com.mvp4g.example.client.product.ProductModule.class );
			modules.put( com.mvp4g.example.client.product.ProductModule.class, newModule );
			newModule.setParentModule( itself );
			newModule.createAndStartModule();
		}
		if ( passer != null )
			passer.pass( newModule );
	}

	public void addConverter( String token, HistoryConverter<?> hc ) {
		placeService.addConverter( token, hc );
	}

	public void clearHistory() {
		placeService.clearHistory();
	}

	public void place( String token, String form ) {
		placeService.place( token, form );
	}

	public void dispatchHistoryEvent( String eventType, final Mvp4gEventPasser passer ) {
		int index = eventType.indexOf( PlaceService.MODULE_SEPARATOR );
		if ( index > -1 ) {
			String moduleHistoryName = eventType.substring( 0, index );
			String nextToken = eventType.substring( index + 1 );
			Mvp4gEventPasser nextPasser = new Mvp4gEventPasser( nextToken ) {
				public void pass( Mvp4gModule module ) {
					module.dispatchHistoryEvent( (String)eventObjects[0], passer );
				}
			};
			if ( "company".equals( moduleHistoryName ) ) {
				loadcom_mvp4g_example_client_company_CompanyModule( nextPasser );
				return;
			}
			if ( "product".equals( moduleHistoryName ) ) {
				loadcom_mvp4g_example_client_product_ProductModule( nextPasser );
				return;
			}
			passer.setEventObject( false );
			passer.pass( this );
		} else {
			passer.pass( this );
		}
	}

	public void createAndStartModule() {

		Mvp4gGinjector injector = GWT.create( Mvp4gGinjector.class );

		final com.mvp4g.example.client.main.MainView com_mvp4g_example_client_main_MainPresenterView = injector.getcom_mvp4g_example_client_main_MainView();

		placeService = new PlaceService() {
			protected void sendInitEvent() {
				eventBus.onStart();
			}

			protected void sendNotFoundEvent() {
				eventBus.onStart();
			}
		};
		final com.mvp4g.client.history.ClearHistory com_mvp4g_client_history_ClearHistory = new com.mvp4g.client.history.ClearHistory();
		final com.mvp4g.example.client.main.historyConverter.MenuHistoryConverter com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter = injector.getcom_mvp4g_example_client_main_historyConverter_MenuHistoryConverter();

		final com.mvp4g.example.client.main.MainPresenter com_mvp4g_example_client_main_MainPresenter = injector.getcom_mvp4g_example_client_main_MainPresenter();
		com_mvp4g_example_client_main_MainPresenter.setView( com_mvp4g_example_client_main_MainPresenterView );

		eventBus = new AbstractEventBus() {
			public void goToProduct( java.lang.Integer attr0, java.lang.Integer attr1 ) {
				loadcom_mvp4g_example_client_product_ProductModule( new Mvp4gEventPasser( attr0, attr1 ) {
					public void pass( Mvp4gModule module ) {
						com.mvp4g.example.client.product.ProductEventBus eventBus = (com.mvp4g.example.client.product.ProductEventBus)module
								.getEventBus();
						eventBus.goToProduct( (java.lang.Integer)eventObjects[0], (java.lang.Integer)eventObjects[1] );
					}
				} );
				place( itself, "goToProduct", com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter.onGoToProduct( attr0, attr1 ) );
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onGoToProduct( attr0, attr1 );
			}

			public void selectProductMenu() {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onSelectProductMenu();
			}

			public void displayMessage( java.lang.String attr0 ) {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onDisplayMessage( attr0 );
			}

			public void errorOnLoad( java.lang.Throwable attr0 ) {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onErrorOnLoad( attr0 );
			}

			public void changeBody( com.google.gwt.user.client.ui.Widget attr0 ) {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onChangeBody( attr0 );
			}

			public void beforeLoad() {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onBeforeLoad();
			}

			public void selectCompanyMenu() {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onSelectCompanyMenu();
			}

			public void afterLoad() {
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onAfterLoad();
			}

			public void onStart() {
			}

			public void goToCompany( int attr0, int attr1 ) {
				loadcom_mvp4g_example_client_company_CompanyModule( new Mvp4gEventPasser( attr0, attr1 ) {
					public void pass( Mvp4gModule module ) {
						com.mvp4g.example.client.company.CompanyEventBus eventBus = (com.mvp4g.example.client.company.CompanyEventBus)module
								.getEventBus();
						eventBus.goToCompany( (Integer)eventObjects[0], (Integer)eventObjects[1] );
					}
				} );
				place( itself, "goToCompany", com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter.onGoToCompany( attr0, attr1 ) );
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onGoToCompany( attr0, attr1 );
			}

			public void clearHistory() {
				clearHistory( itself );
				com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
				com_mvp4g_example_client_main_MainPresenter.onClearHistory();
			}

			public void dispatch( String eventType, Object... data ) {
				try {
					if ( "goToProduct".equals( eventType ) ) {
						goToProduct( (java.lang.Integer)data[0], (java.lang.Integer)data[1] );
					} else if ( "selectProductMenu".equals( eventType ) ) {
						selectProductMenu();
					} else if ( "displayMessage".equals( eventType ) ) {
						displayMessage( (java.lang.String)data[0] );
					} else if ( "errorOnLoad".equals( eventType ) ) {
						errorOnLoad( (java.lang.Throwable)data[0] );
					} else if ( "changeBody".equals( eventType ) ) {
						changeBody( (com.google.gwt.user.client.ui.Widget)data[0] );
					} else if ( "beforeLoad".equals( eventType ) ) {
						beforeLoad();
					} else if ( "selectCompanyMenu".equals( eventType ) ) {
						selectCompanyMenu();
					} else if ( "afterLoad".equals( eventType ) ) {
						afterLoad();
					} else if ( "onStart".equals( eventType ) ) {
						onStart();
					} else if ( "goToCompany".equals( eventType ) ) {
						goToCompany( (Integer)data[0], (Integer)data[1] );
					} else if ( "clearHistory".equals( eventType ) ) {
						clearHistory();
					} else {
						throw new Mvp4gException( "Event " + eventType
								+ " doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?" );
					}
				} catch ( ClassCastException e ) {
					handleClassCastException( e, eventType );
				}
			}
		};
		addConverter( "goToProduct", com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter );
		addConverter( "goToCompany", com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter );
		com_mvp4g_example_client_main_MainPresenter.setEventBus( eventBus );
		placeService.setModule( itself );
		this.startView = com_mvp4g_example_client_main_MainPresenterView;
		com_mvp4g_example_client_main_MainPresenter.bindIfNeeded();
		History.fireCurrentHistoryState();
	}

	public Object getStartView() {
		return startView;
	}

	public EventBus getEventBus() {
		return eventBus;
	}
}

package com.mvp4g.example.client.main.presenter;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.main.MainEventFilter;
import com.mvp4g.example.client.main.view.MainView;
import com.mvp4g.example.client.util.index.IndexGenerator;

@Presenter( view = MainView.class, multiple = true )
public class MainPresenter extends BasePresenter<MainPresenter.MainViewInterface, MainEventBus> {

	public Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();

	public interface MainViewInterface {

		HasClickHandlers getCompanyMenu();

		HasClickHandlers getProductMenu();

		void setBody( Widget newBody );

		void displayErrorMessage( String error );

		void setWaitVisible( boolean visible );

		void displayText( String message );

		void selectCompanyMenu();

		void selectProductMenu();

		HasClickHandlers getClearHistoryButton();

		void displayAlertMessage( String message );

		int getStartIndex();

		int getLastIndex();

		HasValue<Boolean> getFilter();

		HasValue<Boolean> getFilterByActivate();

		HasClickHandlers getHasBeenThere();

		HasClickHandlers getBroadcastInfo();

	}

	@Inject
	private IndexGenerator indexGenerator;

	//have this filter to test force filter option & add/remove event filter
	private MainEventFilter filter = new MainEventFilter();

	public void bind() {
		view.getCompanyMenu().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToCompany( indexGenerator.generateIndex( view.getStartIndex() ), indexGenerator.generateIndex( view.getLastIndex() ) );
			}
		} );
		view.getProductMenu().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToProduct( indexGenerator.generateIndex( view.getStartIndex() ), indexGenerator.generateIndex( view.getLastIndex() ) );
			}
		} );
		view.getClearHistoryButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.clearHistory();
			}
		} );
		view.getFilter().addValueChangeHandler( new ValueChangeHandler<Boolean>() {

			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				if ( event.getValue() ) {
					eventBus.addEventFilter( filter );
				} else {
					eventBus.removeEventFilter( filter );
				}
			}
		} );
		view.getHasBeenThere().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.hasBeenThere();
			}

		} );
		view.getBroadcastInfo().addClickHandler( new ClickHandler() {

			@Override
			public void onClick( ClickEvent event ) {
				eventBus.broadcastInfo( new String[] { "Info1", "Info2" } );
			}

		} );
	}

	public void onChangeBody( Widget w ) {
		view.setBody( w );
		onDisplayMessage( "" );
	}

	public void onErrorOnLoad( Throwable reason ) {
		view.displayErrorMessage( reason.getMessage() );
	}

	public void onBeforeLoad() {
		view.setWaitVisible( true );
	}

	public void onAfterLoad() {
		view.setWaitVisible( false );
	}

	public void onDisplayMessage( String message ) {
		view.displayText( message );
	}

	public void onGoToCompany( int start, int end ) {
		view.selectCompanyMenu();
	}

	public void onGoToProduct( Integer start, Integer end ) {
		view.selectProductMenu();
	}

	public void onSelectProductMenu() {
		view.selectProductMenu();
	}

	public void onSelectCompanyMenu() {
		view.selectCompanyMenu();
	}

	public void onClearHistory() {
		view.displayAlertMessage( "History has been cleared" );
	}

	public boolean isActivated( boolean passive, String eventName, Object... parameters ) {
		if ( view.getFilterByActivate().getValue() ) {
			view.displayText( "Filtered by Activate: " + eventName );
			return false;
		} else {
			return super.isActivated( passive, eventName, parameters );
		}
	}

}

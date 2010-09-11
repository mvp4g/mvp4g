package com.mvp4g.example.client.main;

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
import com.mvp4g.client.presenter.XmlPresenter;
import com.mvp4g.example.client.util.index.IndexGenerator;

@Presenter( view = MainView.class )
public class MainPresenter extends XmlPresenter<MainPresenter.MainViewInterface> {

	public Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();

	public interface MainViewInterface {

		public HasClickHandlers getCompanyMenu();

		public HasClickHandlers getProductMenu();

		public void setBody( Widget newBody );

		public void displayErrorMessage( String error );

		public void setWaitVisible( boolean visible );

		public void setMessage( String message );

		public void selectCompanyMenu();

		public void selectProductMenu();

		public int getStartIndex();

		public int getLastIndex();
		
		public HasValue<Boolean> getFilter();

	}
	
	//have this filter to test force filter option & add/remove event filter
	private MainEventFilter filter = new MainEventFilter();

	@Inject
	private IndexGenerator index;

	public void bind() {
		view.getCompanyMenu().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToCompany", index.generateIndex( view.getStartIndex() ), index.generateIndex( view.getLastIndex() ) );
			}
		} );
		view.getProductMenu().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.dispatch( "goToProduct", index.generateIndex( view.getStartIndex() ), index.generateIndex( view.getLastIndex() ) );
			}
		} );
view.getFilter().addValueChangeHandler( new ValueChangeHandler<Boolean>() {
			
			public void onValueChange( ValueChangeEvent<Boolean> event ) {
				if(event.getValue()){
					eventBus.addEventFilter( filter );
				}
				else{
					eventBus.removeEventFilter( filter );
				}
			}
		});


	}

	public void onChangeBody( Widget w ) {
		view.setBody( w );
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
		view.setMessage( message );
	}

	public void onSelectProductMenu() {
		view.selectProductMenu();
	}

	public void onSelectCompanyMenu() {
		view.selectCompanyMenu();
	}

}

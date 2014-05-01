package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.mvp4g.client.presenter.CyclePresenter;
import com.mvp4g.client.view.CycleView;
import com.mvp4g.example.client.company.CompanyEventBus;
import com.mvp4g.example.client.company.CompanyServiceAsync;
import com.mvp4g.example.client.company.bean.CompanyBean;

public abstract class AbstractCompanyPresenter extends CyclePresenter<AbstractCompanyPresenter.CompanyViewInterface, CompanyEventBus> {

	protected CompanyBean company = null;

	@Inject
	protected CompanyServiceAsync service = null;

	public interface CompanyViewInterface extends CycleView, IsWidget {
		HasValue<String> getName();

		HasClickHandlers getLeftButton();

		HasClickHandlers getRightButton();
		
		HasClickHandlers getSelectNameButton();

		void alert(String message);
		
		boolean confirm(String message);		
		
	}

	public void bindView() {
		view.getLeftButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				clickOnLeftButton( event );
			}
		} );

		view.getRightButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				clickOnRightButton( event );
			}
		} );
		view.getSelectNameButton().addClickHandler( new ClickHandler() {
			
			public void onClick( ClickEvent event ) {
				eventBus.displayNameSelector();
			}
		});

	}

	protected void fillView() {
		view.getName().setValue( company.getName() );
	}

	protected void fillBean() {
		company.setName( view.getName().getValue() );
	}

	protected void clear() {
		view.getName().setValue( "" );
	}

	abstract protected void clickOnLeftButton( ClickEvent event );

	abstract protected void clickOnRightButton( ClickEvent event );

}

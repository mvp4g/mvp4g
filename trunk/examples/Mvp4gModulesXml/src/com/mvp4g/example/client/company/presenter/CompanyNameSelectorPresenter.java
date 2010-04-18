package com.mvp4g.example.client.company.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyXmlPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.company.view.CompanyNameSelectorView;

@Presenter( view = CompanyNameSelectorView.class )
public class CompanyNameSelectorPresenter extends LazyXmlPresenter<CompanyNameSelectorPresenter.CompanyNameSelectorViewInterface> {

	public interface CompanyNameSelectorViewInterface extends LazyView {

		public void show();

		public void hide();

		public HasClickHandlers getSelectButton();

		public ListBox getNames();

	}

	@Override
	public void bindView() {
		ListBox names = view.getNames();
		for ( int i = 0; i < 10; i++ ) {
			names.addItem( "Company" + i );
		}
		view.getSelectButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				ListBox names = view.getNames();
				String name = names.getValue( names.getSelectedIndex() );
				view.hide();
				eventBus.dispatch( "nameSelected", name );
			}

		} );
	}

	public void onDisplayNameSelector() {
		view.show();
	}

}

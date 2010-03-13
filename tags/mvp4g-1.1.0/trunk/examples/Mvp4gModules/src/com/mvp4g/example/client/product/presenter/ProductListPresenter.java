package com.mvp4g.example.client.product.presenter;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.product.ProductEventBus;
import com.mvp4g.example.client.product.ProductServiceAsync;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.view.ProductListView;

@Presenter( view = ProductListView.class )
public class ProductListPresenter extends LazyPresenter<ProductListPresenter.ProductListViewInterface, ProductEventBus> {

	private ProductServiceAsync service = null;
	private List<ProductBean> products = null;

	public interface ProductListViewInterface extends LazyView {
		public HasClickHandlers getCreateButton();

		public HasClickHandlers[] addProduct( String product, int row );

		public void removeProduct( int row );

		public void updateProduct( String product, int row );

		public Widget getViewWidget();
	}

	@Override
	public void bindView() {
		view.getCreateButton().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToCreation();
			}

		} );
	}

	public void onGoToProduct() {
		service.getProducts( new AsyncCallback<List<ProductBean>>() {

			public void onSuccess( List<ProductBean> result ) {
				products = result;
				for ( int i = 0; i < result.size(); i++ ) {
					addProduct( result.get( i ), i );
				}
				eventBus.changeBody( view.getViewWidget() );
			}

			public void onFailure( Throwable caught ) {
				eventBus.displayMessage( "Failed to retrieve products" );
			}
		} );
	}

	public void onGoToList() {
		eventBus.changeBody( view.getViewWidget() );
	}

	public void onProductDeleted( ProductBean product ) {
		eventBus.changeBody( view.getViewWidget() );
		finishDeletion( product );
	}

	public void onProductCreated( ProductBean product ) {
		int row = products.size();
		products.add( product );
		view.addProduct( product.getName(), row );
	}

	@InjectService
	public void setService( ProductServiceAsync service ) {
		this.service = service;
	}

	private void addProduct( final ProductBean product, int row ) {
		HasClickHandlers[] buttons = view.addProduct( product.getName(), row );
		buttons[0].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToDisplay( product );
			}
		} );
		buttons[1].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				eventBus.goToEdit( product );
			}
		} );
		buttons[2].addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				deleteProduct( product );
			}
		} );
	}

	private void deleteProduct( final ProductBean product ) {
		service.deleteProduct( product, new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				eventBus.displayMessage( "Deletion Failed" );
			}

			public void onSuccess( Void result ) {
				finishDeletion( product );
			}
		} );
	}

	private void finishDeletion( ProductBean Product ) {
		int row = products.indexOf( Product );
		products.remove( row );
		view.removeProduct( row );
		eventBus.displayMessage( "Deletion Succeeded" );
	}

}

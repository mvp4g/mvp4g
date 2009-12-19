package com.mvp4g.example.client.product.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.view.ProductDisplayView;

@Presenter(view = ProductDisplayView.class)
public class ProductDisplayPresenter extends AbstractProductPresenter {

	public void onGoToDisplay(ProductBean product) {
		displayProduct(product);
	}

	public void onProductCreated(ProductBean product) {
		displayProduct(product);
	}

	@Override
	protected void clickOnLeftButton(ClickEvent event) {
		eventBus.dispatch("goToEdit", product);
	}

	@Override
	protected void clickOnRightButton(ClickEvent event) {
		fillBean();
		service.deleteProduct(product, new AsyncCallback<Void>() {

			public void onSuccess(Void result) {
				eventBus.dispatch("productDeleted", product);
			}

			public void onFailure(Throwable caught) {
				eventBus.dispatch("displayMessage", "Deletion Failed");
			}
		});
	}

	private void displayProduct(ProductBean product) {
		this.product = product;
		fillView();
		eventBus.dispatch("changeBody", view.getViewWidget());
	}

}

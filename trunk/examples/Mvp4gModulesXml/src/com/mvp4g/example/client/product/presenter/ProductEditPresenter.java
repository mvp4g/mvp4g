package com.mvp4g.example.client.product.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.example.client.product.bean.ProductBean;
import com.mvp4g.example.client.product.view.ProductEditView;

@Presenter(view = ProductEditView.class)
public class ProductEditPresenter extends AbstractProductPresenter {

	public void onGoToEdit(ProductBean product) {
		this.product = product;
		fillView();
		eventBus.dispatch("changeBody", view.getViewWidget());
	}

	@Override
	protected void clickOnLeftButton(ClickEvent event) {
		fillBean();
		service.updateProduct(product, new AsyncCallback<Void>() {

			public void onSuccess(Void result) {
				eventBus.dispatch("goToDisplay", product);
				eventBus.dispatch("displayMessage", "Update Succeeded");
			}

			public void onFailure(Throwable caught) {
				eventBus.dispatch("displayMessage", "Update Failed");
			}
		});
	}

	@Override
	protected void clickOnRightButton(ClickEvent event) {
		clear();
	}

}

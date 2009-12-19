package com.mvp4g.example.client.product.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.LazyXmlPresenter;
import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.product.view.ProductRootView;

@Presenter(view = ProductRootView.class)
public class ProductRootPresenter
		extends
		LazyXmlPresenter<ProductRootPresenter.ProductRootViewInterface> {

	public interface ProductRootViewInterface extends LazyView {

		public void displayText(String text);

		public void setBody(Widget newBody);

	}

	public void onDisplayMessage(String message) {
		view.displayText(message);
	}

	public void onChangeBody(Widget body) {
		view.displayText("");
		view.setBody(body);
	}

}

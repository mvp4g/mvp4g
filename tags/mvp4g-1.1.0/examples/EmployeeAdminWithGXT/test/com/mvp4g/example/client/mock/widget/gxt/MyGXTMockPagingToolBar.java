package com.mvp4g.example.client.mock.widget.gxt;

import com.extjs.gxt.ui.client.data.PagingLoader;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTPagingToolBarInterface;

public class MyGXTMockPagingToolBar implements MyGXTPagingToolBarInterface {

	private PagingLoader<?> loader = null;

	public void bind( PagingLoader<?> loader ) {
		this.loader = loader;
	}

	public PagingLoader<?> getLoader() {
		return loader;
	}

}

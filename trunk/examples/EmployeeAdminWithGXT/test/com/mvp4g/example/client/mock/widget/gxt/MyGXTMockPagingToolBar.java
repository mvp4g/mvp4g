package com.mvp4g.example.client.mock.widget.gxt;

import com.extjs.gxt.ui.client.data.PagingLoader;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTPagingToolBar;

public class MyGXTMockPagingToolBar implements IGXTPagingToolBar {

	private PagingLoader<?> loader = null;

	public void bind( PagingLoader<?> loader ) {
		this.loader = loader;
	}

	public PagingLoader<?> getLoader() {
		return loader;
	}

}

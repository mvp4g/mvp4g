package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.util.test_tools.annotation.Presenters.SimplePresenter;

public class Views {
	
	public static class SimpleView {
				
	}
	
	public static class SimpleInjectedView extends SimpleView implements ReverseViewInterface<Presenters.SimplePresenter> {

		public SimplePresenter getPresenter() {
			return null;
		}

		public void setPresenter( SimplePresenter presenter ) {
			
		}
	}

}

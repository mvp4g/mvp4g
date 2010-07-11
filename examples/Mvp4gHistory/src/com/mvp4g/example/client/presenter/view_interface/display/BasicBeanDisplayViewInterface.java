package com.mvp4g.example.client.presenter.view_interface.display;

import com.mvp4g.client.view.LazyView;
import com.mvp4g.example.client.widget.IView;

public interface BasicBeanDisplayViewInterface extends LazyView, IView {

	void setName( String name );

	void setDescription( String description );

}

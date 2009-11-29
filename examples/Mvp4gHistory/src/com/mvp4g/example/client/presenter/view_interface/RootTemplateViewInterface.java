package com.mvp4g.example.client.presenter.view_interface;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.LazyView;

public interface RootTemplateViewInterface extends LazyView {
	
	public void setTopWidget(Widget w);
	public void clearMainWidget();
	public void setMainWidget(Widget w);
	public void setBottomWidget(Widget w);
	
	public Label getMessageBar();
	

}

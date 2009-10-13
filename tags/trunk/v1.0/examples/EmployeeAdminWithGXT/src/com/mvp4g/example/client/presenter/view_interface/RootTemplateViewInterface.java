package com.mvp4g.example.client.presenter.view_interface;

import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public interface RootTemplateViewInterface {
	
	public void setTopWidget(MyWidgetInterface widget);
	public void setLeftBottomWidget(MyWidgetInterface widget);
	public void setRightBottomWidget(MyWidgetInterface widget);

}

package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.LayoutPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.MailPresenter.IMailView;

public class MailView extends Composite implements IMailView{
	
	interface Binder extends UiBinder<DockLayoutPanel, MailView> { }

	private static final Binder binder = GWT.create(Binder.class);

	  @UiField SimplePanel topPanel;
	  @UiField LayoutPanel mailList;
	  @UiField LayoutPanel mailDetail;
	  @UiField LayoutPanel shortcuts;
	  
	public MailView(){
		initWidget(binder.createAndBindUi(this));
	}

	public void setMiddleCenterWidget(Widget w) {
		mailDetail.clear();
		mailDetail.add(w);		
	}

	public void setMiddleNorthWidget(Widget w) {
		mailList.clear();
		mailList.add(w);		
	}

	public void setMiddleWestWidget(Widget w) {
		shortcuts.clear();
		shortcuts.add(w);		
	}

	public void setNorthWidget(Widget w) {
		topPanel.setWidget(w);		
	}

}

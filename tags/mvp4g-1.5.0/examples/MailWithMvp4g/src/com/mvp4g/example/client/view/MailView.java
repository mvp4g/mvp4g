package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.inject.Inject;
import com.mvp4g.example.client.presenter.interfaces.IMailView;
import com.mvp4g.example.client.presenter.interfaces.IMailView.IMailPresenter;
import com.mvp4g.example.client.view.widget.ReverseResizeComposite;

public class MailView extends ReverseResizeComposite<IMailPresenter> implements IMailView {

	interface Binder extends UiBinder<DockLayoutPanel, MailView> {
	}

	private static final Binder binder = GWT.create( Binder.class );

	@UiField( provided = true )
	TopView topPanel;
	@UiField( provided = true )
	MailListView mailList;
	@UiField( provided = true )
	ShortcutsView shortcuts;
	@UiField( provided = true )
	MailDetailView mailDetail;

	@Inject
	public MailView( TopView topPanel, ShortcutsView shortcuts, MailListView mailList, MailDetailView mailDetail ) {
		this.topPanel = topPanel;
		this.shortcuts = shortcuts;
		this.mailList = mailList;
		this.mailDetail = mailDetail;
		initWidget( binder.createAndBindUi( this ) );
	}

}

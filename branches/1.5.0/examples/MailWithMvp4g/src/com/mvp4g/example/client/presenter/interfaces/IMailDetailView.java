package com.mvp4g.example.client.presenter.interfaces;

import com.mvp4g.client.view.ReverseViewInterface;

public interface IMailDetailView extends ReverseViewInterface<IMailDetailView.IMailDetailPresenter>{
	
	interface IMailDetailPresenter {};

	void setSubject( String subject );

	void setSender( String sender );

	void setRecipient( String recipient );

	void setBody( String body );

}

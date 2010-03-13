package com.mvp4g.example.client.presenter.gxt;

import com.extjs.gxt.ui.client.data.BaseModelData;

public class MyListModel extends BaseModelData {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4666744760960149273L;
	final private static String TEXT = "text";

	public MyListModel() {

	}

	public MyListModel( String text ) {
		setText( text );
	}

	public void setText( String text ) {
		set( TEXT, text );
	}

	public String getText() {
		return get( TEXT );
	}

}

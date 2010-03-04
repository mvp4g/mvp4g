/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.example.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.ResizeComposite;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.presenter.MailListPresenter;

/**
 * A composite that displays a list of emails that can be selected.
 */
public class MailListView extends ResizeComposite implements MailListPresenter.IMailListView {

	interface Binder extends UiBinder<Widget, MailListView> {
	}

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	private static final Binder binder = GWT.create( Binder.class );

	@UiField
	FlexTable header;
	@UiField
	FlexTable table;
	@UiField
	SelectionStyle selectionStyle;

	public MailListView() {
		initWidget( binder.createAndBindUi( this ) );
		initTable();
	}

	/**
	 * Initializes the table so that it contains enough rows for a full page of emails. Also creates
	 * the images that will be used as 'read' flags.
	 */
	private void initTable() {
		// Initialize the header.
		header.getColumnFormatter().setWidth( 0, "128px" );
		header.getColumnFormatter().setWidth( 1, "192px" );
		header.getColumnFormatter().setWidth( 3, "256px" );

		header.setText( 0, 0, "Sender" );
		header.setText( 0, 1, "Email" );
		header.setText( 0, 2, "Subject" );
		header.getCellFormatter().setHorizontalAlignment( 0, 3, HasHorizontalAlignment.ALIGN_RIGHT );

		// Initialize the table.
		table.getColumnFormatter().setWidth( 0, "128px" );
		table.getColumnFormatter().setWidth( 1, "192px" );
	}

	public String getSelectedStyle() {
		return selectionStyle.selectedRow();
	}

	public FlexTable getTable() {
		return table;
	}

	public Widget getViewWidget() {
		return this;
	}

	public void setNavigationBar( Widget navigationBar ) {
		header.setWidget( 0, 3, navigationBar );
	}

}

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
package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.data.MailItem;
import com.mvp4g.example.client.data.MailItems;
import com.mvp4g.example.client.view.MailListView;

@Presenter( view = MailListView.class )
public class MailListPresenter extends BasePresenter<MailListPresenter.IMailListView, MailEventBus> {

	public interface IMailListView {
		public FlexTable getTable();

		public void setNavigationBar( Widget navigationBar );

		public String getSelectedStyle();

		public Widget getViewWidget();
	}

	private int startIndex, selectedRow = -1;

	interface SelectionStyle extends CssResource {
		String selectedRow();
	}

	static final int VISIBLE_EMAIL_COUNT = 20;

	@Override
	public void bind() {
		view.getTable().addClickHandler( new ClickHandler() {

			public void onClick( ClickEvent event ) {
				Cell cell = view.getTable().getCellForEvent( event );
				if ( cell != null ) {
					int row = cell.getRowIndex();
					selectRow( row );
				}
			}
		} );
	}

	public void onStart() {

		update();

		// Select the first row if none is selected.
		if ( selectedRow == -1 ) {
			selectRow( 0 );
		}
	}

	public void onSetNavigationBar( Widget navigationBar ) {
		view.setNavigationBar( navigationBar );
	}

	public void onNewer() {
		// Move back a page.
		startIndex -= VISIBLE_EMAIL_COUNT;
		if ( startIndex < 0 ) {
			startIndex = 0;
		} else {
			styleRow( selectedRow, false );
			selectedRow = -1;
			update();
		}
	}

	public void onOlder() {
		// Move forward a page.
		startIndex += VISIBLE_EMAIL_COUNT;
		if ( startIndex >= MailItems.getMailItemCount() ) {
			startIndex -= VISIBLE_EMAIL_COUNT;
		} else {
			styleRow( selectedRow, false );
			selectedRow = -1;
			update();
		}
	}

	/**
	 * Selects the given row (relative to the current page).
	 * 
	 * @param row
	 *            the row to be selected
	 */
	private void selectRow( int row ) {
		// When a row (other than the first one, which is used as a header) is
		// selected, display its associated MailItem.
		MailItem item = MailItems.getMailItem( startIndex + row );
		if ( item == null ) {
			return;
		}

		styleRow( selectedRow, false );
		styleRow( row, true );

		item.read = true;
		selectedRow = row;

		eventBus.itemSelected( item );
	}

	private void update() {
		// Update the older/newer buttons & label.
		int count = MailItems.getMailItemCount();
		int max = startIndex + VISIBLE_EMAIL_COUNT;
		if ( max > count ) {
			max = count;
		}

		// Update the nav bar.
		eventBus.setNavStatus( startIndex + 1, max, count );

		FlexTable table = view.getTable();

		// Show the selected emails.
		int i = 0;
		for ( ; i < VISIBLE_EMAIL_COUNT; ++i ) {
			// Don't read past the end.
			if ( startIndex + i >= MailItems.getMailItemCount() ) {
				break;
			}

			MailItem item = MailItems.getMailItem( startIndex + i );

			// Add a new row to the table, then set each of its columns to the
			// email's sender and subject values.
			table.setText( i, 0, item.sender );
			table.setText( i, 1, item.email );
			table.setText( i, 2, item.subject );
		}

		// Clear any remaining slots.
		for ( ; i < VISIBLE_EMAIL_COUNT; ++i ) {
			table.removeRow( table.getRowCount() - 1 );
		}
	}

	private void styleRow( int row, boolean selected ) {
		if ( row != -1 ) {
			String style = view.getSelectedStyle();

			if ( selected ) {
				view.getTable().getRowFormatter().addStyleName( row, style );
			} else {
				view.getTable().getRowFormatter().removeStyleName( row, style );
			}
		}
	}
}

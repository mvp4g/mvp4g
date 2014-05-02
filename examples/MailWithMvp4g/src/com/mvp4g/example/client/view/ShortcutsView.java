/*
 * Copyright 2008 Google Inc.
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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.StackLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.example.client.presenter.ShortCutsPresenter.FOLDER_TYPE;
import com.mvp4g.example.client.presenter.interfaces.IShortCutsView;
import com.mvp4g.example.client.presenter.interfaces.IShortCutsView.IShortCutsPresenter;
import com.mvp4g.example.client.view.widget.ContactPopup;
import com.mvp4g.example.client.view.widget.Contacts;
import com.mvp4g.example.client.view.widget.Mailboxes;
import com.mvp4g.example.client.view.widget.ReverseResizeComposite;
import com.mvp4g.example.client.view.widget.Tasks;

/**
 * A composite that contains the shortcut stack panel on the left side. The mailbox tree and
 * shortcut lists don't actually do anything, but serve to show how you can construct an interface
 * using {@link com.google.gwt.user.client.ui.StackPanel},
 * {@link com.google.gwt.user.client.ui.Tree}, and other custom widgets.
 */
@Singleton
public class ShortcutsView extends ReverseResizeComposite<IShortCutsPresenter> implements IShortCutsView {

	interface Binder extends UiBinder<StackLayoutPanel, ShortcutsView> {
	}

	private static final Binder binder = GWT.create( Binder.class );

	@UiField
	Mailboxes mailboxes;
	@UiField
	Tasks tasks;
	@UiField
	Contacts contacts;

	/**
	 * Constructs a new shortcuts widget using the specified images.
	 * 
	 * @param images
	 *            a bundle that provides the images for this widget
	 */
	public ShortcutsView() {
		initWidget( binder.createAndBindUi( this ) );
	}

	public Anchor addContact( String name ) {
		return contacts.addContact( name );
	}

	public void addTask( String task ) {
		tasks.addTask( task );
	}

	public Widget getViewWidget() {
		return this;
	}

	public void showContactPopup( String name, String email, int left, int top ) {

	}

	public void addFolder( FOLDER_TYPE folder ) {
		mailboxes.addImageItem( folder );
	}

	@Override
	public void addContact( String name, final int index ) {
		final Anchor anchor = contacts.addContact( name );
		anchor.addClickHandler( new ClickHandler() {

			@Override
			public void onClick( ClickEvent event ) {
				presenter.onContactClick( index, anchor );
			}
		} );
	}

	@Override
	public void showContactPopup( String name, String email, IsWidget widget ) {
		ContactPopup popup = new ContactPopup( name, email );
		Widget w = widget.asWidget();
		popup.setPopupPosition( w.getAbsoluteLeft() + 14, w.getAbsoluteTop() + 14);
		popup.show();
	}

}

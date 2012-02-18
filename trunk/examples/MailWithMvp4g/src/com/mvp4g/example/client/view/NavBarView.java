/*
 * Copyright 2009 Google Inc.
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
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.example.client.presenter.interfaces.INavBarView;
import com.mvp4g.example.client.presenter.interfaces.INavBarView.INavBarPresenter;
import com.mvp4g.example.client.view.widget.ReverseComposite;

/**
 * A simple widget representing prev/next page navigation.
 */
@Singleton
public class NavBarView extends ReverseComposite<INavBarPresenter> implements INavBarView {

	@UiTemplate( "NavBarView.ui.xml" )
	interface Binder extends UiBinder<Widget, NavBarView> {
	}

	private static final Binder binder = GWT.create( Binder.class );

	@UiField
	Element countLabel;
	@UiField
	Anchor newerButton;
	@UiField
	Anchor olderButton;

	public NavBarView() {
		initWidget( binder.createAndBindUi( this ) );
	}
	
	@UiHandler("newerButton")
	public void onNewerClick(ClickEvent event){
		presenter.onNewerButtonClick();
	}
	
	@UiHandler("olderButton")
	public void onOlderClick(ClickEvent event){
		presenter.onOlderButtonClick();
	}

	public void setNavText( String text ) {
		countLabel.setInnerHTML( text );
	}

	public void setOlderVisible( boolean visible ) {
		olderButton.setVisible( visible );
	}

	public void setNewerVisible( boolean visible ) {
		newerButton.setVisible( visible );
	}
}

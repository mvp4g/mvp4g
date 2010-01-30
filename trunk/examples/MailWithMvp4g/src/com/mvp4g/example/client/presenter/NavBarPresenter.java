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
package com.mvp4g.example.client.presenter;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.bean.NavStatus;
import com.mvp4g.example.client.view.NavBarView;

/**
 * A simple widget representing prev/next page navigation.
 */
@Presenter(view=NavBarView.class)
public class NavBarPresenter extends
		BasePresenter<NavBarPresenter.INavBarView, MailEventBus> {

	public interface INavBarView {
		public Anchor getNewerButton();

		public Anchor getOlderButton();

		public void setNavText(String text);
		
		public Widget getViewWidget();
	}
	
	public void onStart(){
		eventBus.setNavigationBar(view.getViewWidget());
	}

	public void bind() {
		view.getNewerButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.newer();
			}

		});

		view.getOlderButton().addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				eventBus.older();
			}

		});
	}

	public void onSetNavStatus(NavStatus status) {
		int startIndex = status.getStartIndex();
		int count = status.getNumberOfElements();

		view.getNewerButton().setVisible(startIndex != 1);
		view
				.getOlderButton().setVisible((startIndex + MailListPresenter.VISIBLE_EMAIL_COUNT) <= count);
		view.setNavText("" + startIndex + " - " + status.getEndIndex() + " of "
				+ count);
	}

}

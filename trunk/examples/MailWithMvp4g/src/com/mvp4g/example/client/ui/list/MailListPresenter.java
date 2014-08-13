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
package com.mvp4g.example.client.ui.list;

import com.google.gwt.resources.client.CssResource;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.MailItems;
import com.mvp4g.example.client.bean.MailItem;
import com.mvp4g.example.client.ui.list.IMailListView.IMailListPresenter;

import java.util.ArrayList;
import java.util.List;

@Presenter(view = IMailListView.class)
public class MailListPresenter
    extends BasePresenter<IMailListView, MailEventBus>
    implements IMailListPresenter {

  public static final int VISIBLE_EMAIL_COUNT = 20;
  private int startIndex, selectedRow = -1;

  @Override
  public void bind() {
    eventBus.setListView(view.asWidget());
  }

  @Override
  public void doSelect(MailItem item) {
    eventBus.itemSelected(item);
  }

  private void styleRow(int row,
                        boolean selected) {
    if (row != -1) {
      view.selectRow(row,
                     selected);
    }
  }

  public void onStart() {
    update();
  }

  private void update() {
    // Show the selected emails.
    view.clearEmails();
    List<MailItem> emails = new ArrayList<MailItem>();
    for (int i = 0; i < MailItems.getMailItemCount(); ++i) {
      emails.add(MailItems.getMailItem(i));
    }
    view.setEmails(emails);
  }

  public void onNewer() {
    // Move back a page.
    startIndex -= VISIBLE_EMAIL_COUNT;
    if (startIndex < 0) {
      startIndex = 0;
    } else {
      styleRow(selectedRow,
               false);
      selectedRow = -1;
      update();
    }
  }

  public void onOlder() {
    // Move forward a page.
    startIndex += VISIBLE_EMAIL_COUNT;
    if (startIndex >= MailItems.getMailItemCount()) {
      startIndex -= VISIBLE_EMAIL_COUNT;
    } else {
      styleRow(selectedRow,
               false);
      selectedRow = -1;
      update();
    }
  }

  interface SelectionStyle
      extends CssResource {
    String selectedRow();
  }
}

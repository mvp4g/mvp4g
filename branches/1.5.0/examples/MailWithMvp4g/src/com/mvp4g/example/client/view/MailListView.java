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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mvp4g.example.client.presenter.interfaces.IMailListView;
import com.mvp4g.example.client.presenter.interfaces.IMailListView.IMailListPresenter;
import com.mvp4g.example.client.view.widget.ReverseComposite;

/**
 * A composite that displays a list of emails that can be selected.
 */
@Singleton
public class MailListView
  extends ReverseComposite<IMailListPresenter>
  implements IMailListView {

  interface Binder
    extends UiBinder<Widget, MailListView> {
  }

  interface SelectionStyle
    extends CssResource {
    String selectedRow();
  }

  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  FlexTable      header;
  @UiField
  FlexTable      table;
  @UiField
  SelectionStyle selectionStyle;

  @Inject
  public MailListView(NavBarView navView) {
    initWidget(binder.createAndBindUi(this));
    initTable();
    header.setWidget(0,
                     3,
                     navView);
  }

  @UiHandler("table")
  public void onTableClick(ClickEvent event) {
    Cell c = table.getCellForEvent(event);
    if (c != null) {
      presenter.onTableClick(c.getRowIndex());
    }
  }

  /**
   * Initializes the table so that it contains enough rows for a full page of emails. Also creates
   * the images that will be used as 'read' flags.
   */
  private void initTable() {
    // Initialize the header.
    header.getColumnFormatter().setWidth(0,
                                         "128px");
    header.getColumnFormatter().setWidth(1,
                                         "192px");
    header.getColumnFormatter().setWidth(3,
                                         "256px");

    header.setText(0,
                   0,
                   "Sender");
    header.setText(0,
                   1,
                   "Email");
    header.setText(0,
                   2,
                   "Subject");
    header.getCellFormatter().setHorizontalAlignment(0,
                                                     3,
                                                     HasHorizontalAlignment.ALIGN_RIGHT);

    // Initialize the table.
    table.getColumnFormatter().setWidth(0,
                                        "128px");
    table.getColumnFormatter().setWidth(1,
                                        "192px");

  }

  public void setRow(int row,
                     String sender,
                     String email,
                     String subject) {
    table.setText(row,
                  0,
                  sender);
    table.setText(row,
                  1,
                  email);
    table.setText(row,
                  2,
                  subject);
  }

  public void clearEmails() {
    table.clear(true);
  }

  public void selectRow(int row,
                        boolean selected) {
    if (selected) {
      table.getRowFormatter().addStyleName(row,
                                           selectionStyle.selectedRow());
    } else {
      table.getRowFormatter().removeStyleName(row,
                                              selectionStyle.selectedRow());
    }
  }

}

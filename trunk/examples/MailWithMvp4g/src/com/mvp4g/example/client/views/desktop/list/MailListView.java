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
package com.mvp4g.example.client.views.desktop.list;

import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.example.client.bean.MailItem;
import com.mvp4g.example.client.ui.list.IMailListView;
import com.mvp4g.example.client.ui.list.IMailListView.IMailListPresenter;
import com.mvp4g.example.client.widgets.ReverseResizeComposite;

import java.util.ArrayList;
import java.util.List;

/**
 * A composite that displays a list of emails that can be selected.
 */
@Singleton
public class MailListView
    extends ReverseResizeComposite<IMailListPresenter>
    implements IMailListView {

  private static final Binder binder = GWT.create(Binder.class);
  @UiField(provided = true)
  DataGrid list;
  @UiField
  SelectionStyle                         selectionStyle;
  @UiField
  SimpleLayoutPanel container;

  private MailItem currentSelection;

  @SuppressWarnings("unused")
  private MailListView() {
    list = new DataGrid<MailItem>();

    initWidget(binder.createAndBindUi(this));

    createGrid();
  }

  private void createGrid() {
    list.setHeight("100%");
    list.setWidth("100%");
    list.getElement()
        .getStyle()
        .setOverflow(Style.Overflow.HIDDEN);


    addColumn(new ClickableTextCell(),
              "Sender",
              new GetValue<String>() {
                @Override
                public String getValue(MailItem item) {
                  return item.getSender();
                }
              },
              new FieldUpdater<MailItem, String>() {
                @Override
                public void update(int index,
                                   MailItem object,
                                   String value) {
                  currentSelection = object;
                  presenter.doSelect(currentSelection);
                }
              });

    addColumn(new ClickableTextCell(),
              "E-Mail",
              new GetValue<String>() {
                @Override
                public String getValue(MailItem item) {
                  return item.getEmail();
                }
              },
              new FieldUpdater<MailItem, String>() {
                @Override
                public void update(int index,
                                   MailItem object,
                                   String value) {
                  currentSelection = object;
                  presenter.doSelect(currentSelection);
                }
              });

    addColumn(new ClickableTextCell(),
              "Subject",
              new GetValue<String>() {
                @Override
                public String getValue(MailItem item) {
                  return item.getSubject();
                }
              },
              new FieldUpdater<MailItem, String>() {
                @Override
                public void update(int index,
                                   MailItem object,
                                   String value) {
                  currentSelection = object;
                  presenter.doSelect(currentSelection);
                }
              });
  }

  /**
   * Add a column with a header.
   *
   * @param <C>        the cell type
   * @param cell       the cell used to render the column
   * @param headerText the header string
   * @param getter     the value getter for the cell
   */
  private <C> Column<MailItem, C> addColumn(com.google.gwt.cell.client.Cell<C> cell,
                                            String headerText,
                                            final GetValue<C> getter,
                                            FieldUpdater<MailItem, C> fieldUpdater) {
    Column<MailItem, C> column = new Column<MailItem, C>(cell) {
      @Override
      public C getValue(MailItem object) {
        return getter.getValue(object);
      }
    };
    column.setFieldUpdater(fieldUpdater);
    list.addColumn(column,
                   headerText);
    return column;
  }

  @Override
  public void clearEmails() {
    list.setRowData(new ArrayList<MailItem>());
  }

  @Override
  public void setEmails(List<MailItem> emails) {
    list.setRowData(emails);
  }

  @Override
  public void selectRow(int row,
                        boolean selected) {

  }

//  @UiHandler("table")
//  public void onTableClick(ClickEvent event) {
//    Cell c = table.getCellForEvent(event);
//    if (c != null) {
//      presenter.onTableClick(c.getRowIndex());
//    }
//  }
//
//  public void clearEmails() {
//    table.clear(true);
//  }
//
//  public void setEmails(int row,
//                     String sender,
//                     String email,
//                     String subject) {
//    table.setText(row,
//                  0,
//                  sender);
//    table.setText(row,
//                  1,
//                  email);
//    table.setText(row,
//                  2,
//                  subject);
//  }
//
//  public void selectRow(int row,
//                        boolean selected) {
//    if (selected) {
//      table.getRowFormatter()
//           .addStyleName(row,
//                         selectionStyle.selectedRow());
//    } else {
//      table.getRowFormatter()
//           .removeStyleName(row,
//                            selectionStyle.selectedRow());
//    }
//  }

  interface Binder
      extends UiBinder<Widget, MailListView> {
  }

  interface SelectionStyle
      extends CssResource {
    String selectedRow();
  }

  /**
   * Get a cell value from a record.
   *
   * @param <T> the cell type
   */
  private static interface GetValue<T> {
    T getValue(MailItem user);
  }
}

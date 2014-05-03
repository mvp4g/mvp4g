package com.mvp4g.example.client.widget.impl;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.user.client.ui.FlexTable;
import com.mvp4g.example.client.widget.interfaces.ITable;

public class MyTable
  extends FlexTable
  implements ITable {

  public void selectRow(int row) {
    getRowFormatter().addStyleName(row,
                                   "selected");

  }

  public void unSelectRow(int row) {
    getRowFormatter().removeStyleName(row,
                                      "selected");
  }

  public int getRowForEvent(ClickEvent event) {
    return getCellForEvent(event).getRowIndex();
  }

}

package com.mvp4g.example.client.widget.interfaces;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;

public interface ITable
    extends HasClickHandlers {

  public int getRowForEvent(ClickEvent event);

  public void selectRow(int row);

  public void unSelectRow(int row);

  public void setText(int row,
                      int column,
                      String text);

  public int getRowCount();

  public void removeRow(int row);

}

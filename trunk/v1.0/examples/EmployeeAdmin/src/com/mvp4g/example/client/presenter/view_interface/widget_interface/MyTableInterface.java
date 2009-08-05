package com.mvp4g.example.client.presenter.view_interface.widget_interface;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.HTMLTable.Cell;

public interface MyTableInterface extends HasClickHandlers{
	
	public Cell getCellForEvent(ClickEvent event);
	public void selectRow(int row);
	public void unSelectRow(int row);
	public void setText(int row, int column, String text);
	public int getRowCount();
	public void removeRow(int row);

}

package com.mvp4g.example.client.presenter.view_interface;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.mvp4g.example.client.presenter.gxt.MyListModel;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.MyWidgetInterface;

public interface UserRoleViewInterface {
	
	public MyWidgetInterface getViewWidget();
	public ListField<MyListModel> getSelectedRolesListBox();
	public SimpleComboBox<String> getRoleChoiceListBox();
	public Button getAddButton();
	public Button getRemoveButton();
	public void displayError(String error);

}

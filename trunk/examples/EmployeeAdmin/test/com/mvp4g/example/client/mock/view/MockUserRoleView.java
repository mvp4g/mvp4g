package com.mvp4g.example.client.mock.view;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.mock.widget.MyMockButton;
import com.mvp4g.example.client.mock.widget.MyMockListBox;
import com.mvp4g.example.client.ui.user.role.IUserRoleView;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.IListBox;

public class MockUserRoleView implements IUserRoleView {

	private String error = null;
	private MyMockButton add = new MyMockButton();
	private MyMockButton remove = new MyMockButton();
	private MyMockListBox choices = new MyMockListBox();
	private MyMockListBox selectedRoles = new MyMockListBox();

	public void displayError( String error ) {
		this.error = error;
	}

	public IButton getAddButton() {
		return add;
	}

	public IButton getRemoveButton() {
		return remove;
	}

	public IListBox getRoleChoiceListBox() {
		return choices;
	}

	public IListBox getSelectedRolesListBox() {
		return selectedRoles;
	}

	public Widget getViewWidget() {
		return null;
	}

	public Widget getMyWidget() {
		return null;
	}

	public String getError() {
		return error;
	}

  @Override
  public Widget asWidget() {
    return null;
  }
}

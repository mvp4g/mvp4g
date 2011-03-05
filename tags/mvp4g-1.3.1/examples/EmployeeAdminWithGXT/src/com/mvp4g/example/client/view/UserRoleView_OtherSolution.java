package com.mvp4g.example.client.view;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.ListField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.presenter.UserRolePresenter_OtherSolution.IUserRoleView_OtherSolution;
import com.mvp4g.example.client.presenter.gxt.MyListModel;

/**
 * SOLUTION 2
 * 
 * Description: use GXT directly in the view interface and presenter. 
 * 
 * Advantages: 
 * -No extra class needed, it's easier to code. -You keep GXT logic 
 * 
 * Drawbacks:
 * -You can't test presenters with JUnit or Mock libraries since GXT widgets can only be
 * tested with GWTTestCase (http://www.extjs.com/forum/showthread.php?t=63053).
 * -Switching widget library has a big impact on your code
 */
public class UserRoleView_OtherSolution extends LayoutContainer implements IUserRoleView_OtherSolution, Constants {

	private ListField<MyListModel> selectedRoles = new ListField<MyListModel>();
	private SimpleComboBox<String> rolesChoices = new SimpleComboBox<String>();

	private Button add = new Button( "Add" );
	private Button remove = new Button( "Remove" );

	public UserRoleView_OtherSolution() {

		selectedRoles.setWidth( "100%" );

		FormPanel form = new FormPanel();
		form.setStyleName( "userRoles" );
		form.setHeading( "User Roles" );
		selectedRoles.setHideLabel( true );
		rolesChoices.setHideLabel( true );
		selectedRoles.setStore( new ListStore<MyListModel>() );
		form.add( selectedRoles );
		form.add( rolesChoices );
		form.addButton( add );
		form.addButton( remove );

		form.setButtonAlign( HorizontalAlignment.CENTER );

		add( form );
	}

	public void displayError( String error ) {
		Window.alert( error );
	}

	public Button getAddButton() {
		return add;
	}

	public Button getRemoveButton() {
		return remove;
	}

	public SimpleComboBox<String> getRoleChoiceListBox() {
		return rolesChoices;
	}

	public ListField<MyListModel> getSelectedRolesListBox() {
		return selectedRoles;
	}

	public Widget getMyWidget() {
		return this;
	}

}

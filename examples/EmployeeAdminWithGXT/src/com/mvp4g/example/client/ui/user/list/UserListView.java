package com.mvp4g.example.client.ui.user.list;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.resources.ImageResources;
import com.mvp4g.example.shared.dto.UserBean;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class UserListView
    implements IUserListView,
               ReverseViewInterface<IUserListView.IUserListPresenter> {

  private static UserBeanProperties userBeanProperties = GWT.create(UserBeanProperties.class);

  private IUserListView.IUserListPresenter presenter;

  private FramedPanel container;

  private ListStore<UserBean> store;
  private Grid<UserBean>      grid;
  private IdentityValueProvider<UserBean>  identity;
  private CheckBoxSelectionModel<UserBean> sm;

  private ToolBar    toolBar;
  private TextButton deleteButton;
  private TextButton newButton;

  public UserListView() {
    createGrid();

    createView();

    bind();
  }

  private void createGrid() {
    store = new ListStore<UserBean>(userBeanProperties.id());

    identity = new IdentityValueProvider<UserBean>();
    sm       = new CheckBoxSelectionModel<UserBean>(identity);
    sm.setSelectionMode(Style.SelectionMode.SINGLE);
    sm.addSelectionHandler(new SelectionHandler<UserBean>() {
      @Override
      public void onSelection(SelectionEvent<UserBean> event) {
        deleteButton.setEnabled(sm.getSelection()
                                  .size() > 0);
        presenter.doShowUser(sm.getSelectedItem());
      }
    });

    ColumnConfig<UserBean, String> firstNameColumn = new ColumnConfig<UserBean, String>(userBeanProperties.firstName(),
                                                                                        200,
                                                                                        "First Name");

    ColumnConfig<UserBean, String> lastNameColumn = new ColumnConfig<UserBean, String>(userBeanProperties.lastName(),
                                                                                       200,
                                                                                       "Last Name");
    ColumnConfig<UserBean, String> departmentColumn = new ColumnConfig<UserBean, String>(userBeanProperties.department(),
                                                                                         300,
                                                                                         "Department");
    ColumnConfig<UserBean, String> emailColumn = new ColumnConfig<UserBean, String>(userBeanProperties.email(),
                                                                                    300,
                                                                                    "E-Mail");
    ColumnConfig<UserBean, String> userNameColumn = new ColumnConfig<UserBean, String>(userBeanProperties.username(),
                                                                                       200,
                                                                                       "Username");
    List<ColumnConfig<UserBean, ?>> columns = new ArrayList<ColumnConfig<UserBean, ?>>();
    columns.add(sm.getColumn());
    columns.add(firstNameColumn);
    columns.add(lastNameColumn);
    columns.add(departmentColumn);
    columns.add(emailColumn);
    columns.add(userNameColumn);
    ColumnModel<UserBean> cm = new ColumnModel<UserBean>(columns);

    grid = new Grid<UserBean>(store,
                              cm);
    grid.setSize("100%",
                 "100%");
    grid.getView().setAutoExpandColumn(lastNameColumn);
    grid.getView().setStripeRows(true);
    grid.setSelectionModel(sm);
  }

  private void createView() {
    container = new FramedPanel();
    container.setHeaderVisible(true);
    container.setHeadingText("Users");

    toolBar = new ToolBar();
    deleteButton = new TextButton("   Delete User");
    deleteButton.setIcon(ImageResources.IMAGES.iconDelete());
    deleteButton.setEnabled(false);
    newButton = new TextButton("   New User");
    newButton.setIcon(ImageResources.IMAGES.iconNew());
    toolBar.add(newButton);
    toolBar.add(deleteButton);
    container.addTool(toolBar);


    VerticalLayoutContainer contentContainer = new VerticalLayoutContainer();
    container.add(contentContainer);
    contentContainer.add(toolBar,
                         new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                        -1,
                                                                        new Margins(0)));
    contentContainer.add(grid,
                         new VerticalLayoutContainer.VerticalLayoutData(1,
                                                                        1,
                                                                        new Margins(0)));
  }

  private void bind() {
    newButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        presenter.doNewUser();
      }
    });

    deleteButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
       if (sm.getSelectedItem() == null) {
          Window.alert("Please select a user");
        } else {
          if (Window.confirm("Are you sure?")) {
            presenter.doDeleteUser(sm.getSelectedItem());
          }
        }
      }
    });
  }

  @Override
  public Widget asWidget() {
    return container;
  }


  @Override
  public void setUserList(List<UserBean> userList) {
    store.clear();
    store.addAll(userList);
  }

  @Override
  public void setUserSelected(boolean enabled) {
    if (enabled) {
      deleteButton.setEnabled(true);
    } else {
      deleteButton.setEnabled(false);
    }
  }

  public interface UserBeanProperties
      extends PropertyAccess<UserBean> {

    ModelKeyProvider<UserBean> id();

    ValueProvider<UserBean, String> firstName();

    ValueProvider<UserBean, String> lastName();

    ValueProvider<UserBean, String> email();

    ValueProvider<UserBean, String> username();

    ValueProvider<UserBean, String> department();

  }

  @Override
  public void setPresenter(IUserListPresenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public IUserListView.IUserListPresenter getPresenter() {
    return this.presenter;
  }
}

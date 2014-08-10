package com.mvp4g.example.client.ui.user.role;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.Constants;
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
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;

public class UserRoleView
    implements IUserRoleView,
               ReverseViewInterface<IUserRoleView.IUserRolePresenter> {

  private static RoleProperties roleProperties = GWT.create(RoleProperties.class);

  private IUserRoleView.IUserRolePresenter presenter;
  private FramedPanel                      container;
  private TextButton                       addButton;
  private TextButton                       removeButton;

  private List<Role> roles = new ArrayList<Role>();

  private ListStore<Role>              storeSelectedRoles;
  private Grid<Role>                   gridSelectedRoles;
  private CheckBoxSelectionModel<Role> smSelectedRoles;

  private ListStore<Role>              storeRolesChoices;
  private Grid<Role>                   gridRolesChoices;
  private CheckBoxSelectionModel<Role> smRolesChoices;

  public UserRoleView() {
    roles = createRoleList();

    createButtons();
    createGridRolesChoices();
    createGridSelectedRoles();
    createView();

    bind();
    initialize();
  }

  private List<Role> createRoleList() {
    long id = 1;
    List<Role> roles = new ArrayList<Role>();
    for (String role : Constants.ROLES) {
      roles.add(new Role(id++,
                         role));
    }
    return roles;
  }

  private void createButtons() {
    removeButton = new TextButton("Remove");
    removeButton.setWidth("100px");
    addButton = new TextButton("Add");
    addButton.setWidth("100px");
  }

  private void createGridRolesChoices() {
    storeRolesChoices = new ListStore<Role>(roleProperties.id());

    IdentityValueProvider<Role> identityRolesChoices = new IdentityValueProvider<Role>();
    smRolesChoices = new CheckBoxSelectionModel<Role>(identityRolesChoices);
    smRolesChoices.setSelectionMode(Style.SelectionMode.SINGLE);
    smRolesChoices.addSelectionHandler(new SelectionHandler<Role>() {
      @Override
      public void onSelection(SelectionEvent<Role> event) {
        addButton.setEnabled(smRolesChoices.getSelectedItems()
                                           .size() > 0);
      }
    });

    ColumnConfig<Role, String> roleColumn = new ColumnConfig<Role, String>(roleProperties.role(),
                                                                           200,
                                                                           "Roles Choices");

    List<ColumnConfig<Role, ?>> columns = new ArrayList<ColumnConfig<Role, ?>>();
    columns.add(smRolesChoices.getColumn());
    columns.add(roleColumn);
    ColumnModel<Role> cm = new ColumnModel<Role>(columns);

    gridRolesChoices = new Grid<Role>(storeRolesChoices,
                                      cm);
    gridRolesChoices.setSize("100%",
                             "200px");
    gridRolesChoices.setBorders(true);
    gridRolesChoices.getView()
                    .setAutoExpandColumn(roleColumn);
    gridRolesChoices.getView()
                    .setStripeRows(true);
    gridRolesChoices.setSelectionModel(smRolesChoices);
  }

  private void createGridSelectedRoles() {
    storeSelectedRoles = new ListStore<Role>(roleProperties.id());

    IdentityValueProvider<Role> identitySelectedRoles = new IdentityValueProvider<Role>();
    smSelectedRoles = new CheckBoxSelectionModel<Role>(identitySelectedRoles);
    smSelectedRoles.setSelectionMode(Style.SelectionMode.SINGLE);
    smSelectedRoles.addSelectionHandler(new SelectionHandler<Role>() {
      @Override
      public void onSelection(SelectionEvent<Role> event) {
        addButton.setEnabled(smSelectedRoles.getSelectedItems()
                                            .size() > 0);
      }
    });

    ColumnConfig<Role, String> roleColumn = new ColumnConfig<Role, String>(roleProperties.role(),
                                                                           200,
                                                                           "Roles Selected");

    List<ColumnConfig<Role, ?>> columns = new ArrayList<ColumnConfig<Role, ?>>();
    columns.add(smSelectedRoles.getColumn());
    columns.add(roleColumn);
    ColumnModel<Role> cm = new ColumnModel<Role>(columns);

    gridSelectedRoles = new Grid<Role>(storeSelectedRoles,
                                       cm);
    gridSelectedRoles.setSize("100%",
                              "100%");
    gridSelectedRoles.setBorders(true);
    gridSelectedRoles.getView()
                     .setAutoExpandColumn(roleColumn);
    gridSelectedRoles.getView()
                     .setStripeRows(true);
    gridSelectedRoles.setSelectionModel(smSelectedRoles);
  }

  private void createView() {
    container = new FramedPanel();
    container.setHeight("400px");
    container.setHeaderVisible(true);
    container.setHeadingText("User Role");

    VerticalLayoutContainer vlc01 = new VerticalLayoutContainer();
    container.add(vlc01);

    HorizontalLayoutContainer hlc01 = new HorizontalLayoutContainer();
    vlc01.add(hlc01,
              new VerticalLayoutContainer.VerticalLayoutData(1,
                                                             180));
    hlc01.add(gridRolesChoices,
              new HorizontalLayoutContainer.HorizontalLayoutData(1,
                                                                 1,
                                                                 new Margins(0,
                                                                             0,
                                                                             6,
                                                                             0)));
    hlc01.add(addButton,
              new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                 -1,
                                                                 new Margins(141,
                                                                             3,
                                                                             6,
                                                                             12)));

    HorizontalLayoutContainer hlc02 = new HorizontalLayoutContainer();
    vlc01.add(hlc02,
              new VerticalLayoutContainer.VerticalLayoutData(1,
                                                             180));
    hlc02.add(gridSelectedRoles,
              new HorizontalLayoutContainer.HorizontalLayoutData(1,
                                                                 1,
                                                                 new Margins(0,
                                                                             0,
                                                                             0,
                                                                             0)));
    hlc02.add(removeButton,
              new HorizontalLayoutContainer.HorizontalLayoutData(-1,
                                                                 -1,
                                                                 new Margins(143,
                                                                             3,
                                                                             3,
                                                                             12)));
  }

  private void bind() {
    addButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        presenter.doAddRole(smRolesChoices.getSelectedItem()
                                          .getRole());
      }
    });

    removeButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        presenter.doRemoveRole(smSelectedRoles.getSelectedItem()
                                              .getRole());
      }
    });

    smRolesChoices.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Role>() {
      @Override
      public void onSelectionChanged(SelectionChangedEvent<Role> event) {
        addButton.setEnabled(smRolesChoices.getSelectedItem() != null);
      }
    });

    smSelectedRoles.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<Role>() {
      @Override
      public void onSelectionChanged(SelectionChangedEvent<Role> event) {
        removeButton.setEnabled(smSelectedRoles.getSelectedItem() != null);
      }
    });
  }

  private void initialize() {
    storeSelectedRoles.clear();
    storeRolesChoices.clear();
    addButton.setEnabled(false);
    removeButton.setEnabled(false);
  }

  @Override
  public Widget asWidget() {
    return container;
  }

  @Override
  public void clear() {
    storeRolesChoices.clear();
    storeSelectedRoles.clear();
  }

  @Override
  public void disable() {
    initialize();
  }

  @Override
  public void showUser(UserBean user) {
    initialize();

    List<String> rolesUser = user.getRoles();
    List<String> selectedRolesItems = new ArrayList<String>();
    if (rolesUser != null) {
      for (String role : rolesUser) {
        selectedRolesItems.add(role);
      }
    }
    storeSelectedRoles.addAll(createRoleListForStore(selectedRolesItems));
    removeButton.setEnabled(false);

    for (Role role : roles) {
      boolean alreadyUsed = false;
      for (String roleString : selectedRolesItems) {
        if (role.getRole()
                .equals(roleString)) {
          alreadyUsed = true;
          break;
        }
      }
      if (!alreadyUsed) {
        storeRolesChoices.add(role);
      }
    }
    addButton.setEnabled(false);
  }

  private List<Role> createRoleListForStore(List<String> list) {
    long id = 1;
    List<Role> roles = new ArrayList<Role>();
    for (String role : list) {
      roles.add(new Role(id++,
                         role));
    }
    return roles;
  }

  interface RoleProperties
      extends PropertyAccess<Role> {

    ModelKeyProvider<Role> id();

    ValueProvider<Role, String> role();

  }

  class Role {

    private long   id;
    private String role;

    Role(long id,
         String role) {
      this.id = id;
      this.role = role;
    }

    public long getId() {
      return id;
    }

    public void setId(long id) {
      this.id = id;
    }

    public String getRole() {
      return role;
    }

    public void setRole(String role) {
      this.role = role;
    }
  }

  @Override
  public void setPresenter(IUserRolePresenter presenter) {
    this.presenter = presenter;
  }


  @Override
  public IUserRolePresenter getPresenter() {
    return this.presenter;
  }
}

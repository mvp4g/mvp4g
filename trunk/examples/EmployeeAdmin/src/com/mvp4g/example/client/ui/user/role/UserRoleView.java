package com.mvp4g.example.client.ui.user.role;

import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.resources.Resources;
import com.mvp4g.example.client.widgets.FlowLayoutPanel;
import com.mvp4g.example.shared.dto.UserBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRoleView
    extends ResizeComposite
    implements IUserRoleView,
               ReverseViewInterface<IUserRoleView.IUserRolePresenter>,
               ProvidesResize,
               RequiresResize {

  private IUserRoleView.IUserRolePresenter presenter;

  private FlowLayoutPanel container;

  private Button addButton;
  private Button removeButton;

  private ValueListBox<String> selectedRoles = new ValueListBox<String>(new Renderer<String>() {
    @Override
    public String render(String object) {
      return object == null || object.trim()
                                     .length() == 0 ? "-- None Selected --" : object;
    }

    @Override
    public void render(String object,
                       Appendable appendable)
        throws IOException {
      appendable.append(object);
    }
  });
  private ListBox              rolesChoices  = new ListBox();


  public UserRoleView() {
    createView();

    bind();
    initialize();

    initWidget(container);
  }

  private void createView() {
    container = new FlowLayoutPanel();
    container.addStyleName(Resources.CSS.employeeAdmin()
                                        .container());

    DockLayoutPanel fpContainer = new DockLayoutPanel(Style.Unit.PX);
    fpContainer.setSize("412px",
                        "336px");
    container.add(fpContainer);

    FlowLayoutPanel fp01 = new FlowLayoutPanel();
    Label la01 = new Label("User Role");
    la01.addStyleName(Resources.CSS.employeeAdmin()
                                   .containerHeadline());
    fp01.add(la01);
    fpContainer.addNorth(fp01,
                         25);

    FlowLayoutPanel fp02 = new FlowLayoutPanel();
    fp02.getElement()
        .getStyle()
        .setMarginTop(8,
                      Style.Unit.PX);
    fp02.setWidth("100%");

    selectedRoles.addStyleName(Resources.CSS.employeeAdmin()
                                            .selectedRoles());
    fp02.add(selectedRoles);
    removeButton = new Button("Remove");
    removeButton.addStyleName(Resources.CSS.employeeAdmin()
                                           .removeButton());
    fp02.add(removeButton);
    addButton = new Button("Add");
    addButton.addStyleName(Resources.CSS.employeeAdmin()
                                        .addButtom());
    fp02.add(addButton);
    fpContainer.addSouth(fp02,
                         42);

    FlowLayoutPanel fp03 = new FlowLayoutPanel();
    fpContainer.add(fp03);

    rolesChoices.setVisibleItemCount(12);
    rolesChoices.setWidth("396px");
    fp03.add(rolesChoices);
  }

  private void bind() {
    addButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        presenter.doAddRole(rolesChoices.getItemText(rolesChoices.getSelectedIndex()));
      }
    });

    removeButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (Window.confirm("Are you sure?")) {
          presenter.doRemoveRole(selectedRoles.getValue());
        }
      }
    });

    rolesChoices.addChangeHandler(new ChangeHandler() {
      @Override
      public void onChange(ChangeEvent event) {
        addButton.setEnabled(true);
      }
    });

    selectedRoles.addValueChangeHandler(new ValueChangeHandler<String>() {
      @Override
      public void onValueChange(ValueChangeEvent<String> event) {
        removeButton.setEnabled(true);
      }
    });
  }

  private void initialize() {
    addButton.setEnabled(false);
    removeButton.setEnabled(false);
  }

  @Override
  public void clear() {
    rolesChoices.clear();
    selectedRoles.setAcceptableValues(new ArrayList<String>());
  }

  @Override
  public void disable() {
    initialize();
    rolesChoices.setEnabled(false);
    selectedRoles.setEnabled(false);
  }

  @Override
  public void showUser(UserBean user) {
    selectedRoles.setValue(null);
    selectedRoles.setAcceptableValues(new ArrayList<String>());
    List<String> roles = user.getRoles();
    List<String> selectedRolesItems = new ArrayList<String>();
    if (roles != null) {
      for (String role : roles) {
        selectedRolesItems.add(role);
      }
    }
    selectedRoles.setAcceptableValues(selectedRolesItems);
    if (selectedRolesItems.size() == 0) {
      selectedRoles.setEnabled(false);
    } else {
      selectedRoles.setEnabled(true);
    }
    removeButton.setEnabled(false);

    rolesChoices.clear();
    for (String role : Constants.ROLES) {
      if (!roles.contains(role)) {
        rolesChoices.addItem(role);
      }
    }
    if (rolesChoices.getItemCount() == 0) {
      rolesChoices.setEnabled(false);
    } else {
      rolesChoices.setEnabled(true);
    }
    addButton.setEnabled(false);
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

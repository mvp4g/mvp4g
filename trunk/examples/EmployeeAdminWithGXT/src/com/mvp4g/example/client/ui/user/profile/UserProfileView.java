package com.mvp4g.example.client.ui.user.profile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.validators.EmailValidator;
import com.mvp4g.example.shared.dto.UserBean;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.StringLabelProvider;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.BlurEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.SimpleComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;

import java.util.Arrays;

public class UserProfileView
    implements IUserProfileView,
               ReverseViewInterface<IUserProfileView.IUserProfilePresenter>,
               Editor<UserBean> {

  private final static int WIDGET_WIDTH = 175;

  @Path("firstName")
  TextField              firstName       = new TextField();
  @Path("lastName")
  TextField              lastName        = new TextField();
  @Path("email")
  TextField              email           = new TextField();
  @Path("username")
  TextField              username        = new TextField();
  @Path("password")
  PasswordField          password        = new PasswordField();
  @Path("password")
  PasswordField          confirmPassword = new PasswordField();
  @Path("department")
  SimpleComboBox<String> department      = new SimpleComboBox<String>(new StringLabelProvider<String>());

  private Driver driver = GWT.create(Driver.class);
  private TextButton                             cancelButton;
  private IUserProfileView.IUserProfilePresenter presenter;
  private FramedPanel                            container;
  private TextButton                             updateButton;
  private boolean                                create;

  private KeyUpHandler handler = new KeyUpHandler() {
    public void onKeyUp(KeyUpEvent event) {
      enableUpdateButton();
    }
  };


  public UserProfileView() {
    createView();

    bind();
    initialize();

    driver.initialize(this);
  }

  private void createView() {
    container = new FramedPanel();
    container.setHeight("400px");
    container.setHeaderVisible(true);
    container.setHeadingText("User Profile");

    firstName.setAllowBlank(false);
    lastName.setAllowBlank(false);
    email.setAllowBlank(false);
    email.addValidator(new EmailValidator());
    password.setAllowBlank(false);
    confirmPassword.setAllowBlank(false);
    username.setAllowBlank(false);

    department.add(Arrays.asList(Constants.DEPARTMENTS));
    department.setEmptyText("-- None Selected --");
    department.setEditable(false);
    department.setTriggerAction(ComboBoxCell.TriggerAction.ALL);

    VerticalLayoutContainer form = new VerticalLayoutContainer();
    container.add(form);

    FieldLabel flFirstName = new FieldLabel(firstName,
                                            "First Name");
    FieldLabel flLastName = new FieldLabel(lastName,
                                           "Last Name");
    FieldLabel flEmail = new FieldLabel(email,
                                        "E-Mail");
    FieldLabel flUserName = new FieldLabel(username,
                                           "Username");
    FieldLabel flPassword = new FieldLabel(password,
                                           "Password");
    FieldLabel flConfirmPassword = new FieldLabel(confirmPassword,
                                                  "Confirm Password");
    FieldLabel flDepartment = new FieldLabel(department,
                                             "Department");

    flFirstName.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flLastName.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flEmail.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flUserName.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flPassword.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flConfirmPassword.setLabelWidth(UserProfileView.WIDGET_WIDTH);
    flDepartment.setLabelWidth(UserProfileView.WIDGET_WIDTH);

    form.add(flFirstName,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flLastName,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flEmail,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flUserName,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flPassword,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flConfirmPassword,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));
    form.add(flDepartment,
             new VerticalLayoutContainer.VerticalLayoutData(1,
                                                            -1,
                                                            new Margins(8)));

    updateButton = new TextButton("Update");
    updateButton.setWidth("100px");
    container.addButton(updateButton);
    cancelButton = new TextButton("Cancel");
    cancelButton.setWidth("100px");
    container.addButton(cancelButton);
  }

  private void bind() {
    updateButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (create) {
          presenter.doCreateUser(driver.flush());
        } else {
          presenter.doUpdateUser(driver.flush());
        }
      }
    });

    cancelButton.addSelectHandler(new SelectEvent.SelectHandler() {
      @Override
      public void onSelect(SelectEvent event) {
        if (Window.confirm("Are you sure?")) {
          presenter.doCancel();
        }
      }
    });

    firstName.addKeyUpHandler(handler);
    firstName.addKeyUpHandler(handler);
    lastName.addKeyUpHandler(handler);
    username.addKeyUpHandler(handler);
    password.addKeyUpHandler(handler);
    confirmPassword.addKeyUpHandler(handler);
    department.addBlurHandler(new BlurEvent.BlurHandler() {
      @Override
      public void onBlur(BlurEvent event) {
        enableUpdateButton();
      }
    });
  }

  private void initialize() {
    updateButton.setEnabled(false);
    cancelButton.setEnabled(true);

    firstName.setValue("");
    lastName.setValue("");
    email.setValue("");
    username.setValue("");
    password.setValue("");
    confirmPassword.setValue("");

    department.setValue(null);
  }

  private void enableUpdateButton() {
    boolean enabled = (username.getValue() != null) && (username.getValue()
                                                                .length() > 0) &&
                      (department.getValue() != null) &&
                      (password.getValue() != null) && (password.getValue()
                                                                .length() > 0) &&
                      (confirmPassword.getValue() != null) && (confirmPassword.getValue()
                                                                              .length() > 0 &&
                                                               (confirmPassword.getValue()
                                                                               .equals(password.getValue())
                      ));
    updateButton.setEnabled(enabled);
  }

  @Override
  public Widget asWidget() {
    return container;
  }

  @Override
  public void clear() {
    initialize();
  }

  @Override
  public void showUser(UserBean user,
                       boolean create) {
    this.create = create;
    driver.edit(user);
    updateButton.setEnabled(false);
    if (create) {
      updateButton.setText("Add User");
    } else {
      updateButton.setText("Update User");
    }
    cancelButton.setEnabled(true);

    firstName.selectAll();
  }

  interface Driver
      extends SimpleBeanEditorDriver<UserBean, UserProfileView> {
  }

  @Override
  public IUserProfilePresenter getPresenter() {
    return presenter;
  }

  @Override
  public void setPresenter(IUserProfilePresenter presenter) {
    this.presenter = presenter;
  }
}
package com.mvp4g.example.client.ui.user.profile;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.shared.dto.UserBean;
import com.sencha.gxt.widget.core.client.ContentPanel;

public class UserProfileView
    implements IUserProfileView,
               ReverseViewInterface<IUserProfileView.IUserProfilePresenter>,
               Editor<UserBean> {
//
//  private final static String WIDGET_WIDTH = "262px";

  //  @Path("firstName")
//  TextBox              firstName       = new TextBox();
//  @Path("lastName")
//  TextBox              lastName        = new TextBox();
//  @Path("email")
//  TextBox              email           = new TextBox();
//  @Path("username")
//  TextBox              username        = new TextBox();
//  @Path("password")
//  PasswordTextBox      password        = new PasswordTextBox();
//  @Path("password")
//  PasswordTextBox      confirmPassword = new PasswordTextBox();
//  @Path("department")
//  ValueListBox<String> department      = new ValueListBox<String>(new Renderer<String>() {
//    @Override
//    public String render(String object) {
//      return object == null || object.trim()
//                                     .length() == 0 ? "-- None Selected --" : object;
//    }
//
//    @Override
//    public void render(String object,
//                       Appendable appendable)
//        throws IOException {
//      appendable.append(object);
//    }
//  });
//  KeyUpHandler handler = new KeyUpHandler() {
//    public void onKeyUp(KeyUpEvent event) {
//      enableUpdateButton();
//    }
//  };
  private Driver driver = GWT.create(Driver.class);
  //  private Button                                 cancelButton;
  private IUserProfileView.IUserProfilePresenter presenter;
  private ContentPanel                           container;
//  private Button                                 updateButton;
//  private boolean                                create;

  public UserProfileView() {
    createView();

    bind();
    initialize();

    driver.initialize(this);
  }

  private void createView() {
    container = new ContentPanel();
    container.setHeaderVisible(true);
    container.setHeadingText("User Profile");
////    container.addStyleName(Resources.CSS.employeeAdmin()
////                                        .container());
////
////    DockLayoutPanel fpContainer = new DockLayoutPanel(Style.Unit.PX);
////    fpContainer.setSize("400px",
////                        "336px");
////    container.add(fpContainer);
////
////    FlowLayoutPanel fp01 = new FlowLayoutPanel();
////    Label la01 = new Label("User Profile");
////    la01.addStyleName(Resources.CSS.employeeAdmin()
////                                   .containerHeadline());
////    fp01.add(la01);
////    fpContainer.addNorth(fp01,
////                         25);
////
////    FlowLayoutPanel fp02 = new FlowLayoutPanel();
////    fp02.getElement()
////        .getStyle()
////        .setMarginTop(8,
////                      Style.Unit.PX);
////    updateButton = new Button("Update");
////    updateButton.getElement()
////                .getStyle()
////                .setMarginRight(12,
////                                Style.Unit.PX);
////    fp02.add(updateButton);
////    cancelButton = new Button("Cancel");
////    fp02.add(cancelButton);
////    fpContainer.addSouth(fp02,
////                         42);
////
////    FlowLayoutPanel fp03 = new FlowLayoutPanel();
////    fpContainer.add(fp03);
////    FieldContainer fc01 = new FieldContainer("First Name",
////                                             firstName);
////    fp03.add(fc01);
////    FieldContainer fc02 = new FieldContainer("Last Name",
////                                             lastName);
////    fp03.add(fc02);
////    FieldContainer fc03 = new FieldContainer("Email",
////                                             email);
////    fp03.add(fc03);
////    FieldContainer fc04 = new FieldContainer("Username *",
////                                             username);
////    fp03.add(fc04);
////    FieldContainer fc05 = new FieldContainer("Password *",
////                                             password);
////    fp03.add(fc05);
////    FieldContainer fc06 = new FieldContainer("Confirm Password *",
////                                             confirmPassword);
////    fp03.add(fc06);
////    FieldContainer fc07 = new FieldContainer("Departement *",
////                                             department);
////    department.setAcceptableValues(Arrays.asList(Constants.DEPARTMENTS));
////    fp03.add(fc07);
////
////    firstName.setWidth(UserProfileView.WIDGET_WIDTH);
////    lastName.setWidth(UserProfileView.WIDGET_WIDTH);
////    email.setWidth(UserProfileView.WIDGET_WIDTH);
////    username.setWidth(UserProfileView.WIDGET_WIDTH);
////    password.setWidth(UserProfileView.WIDGET_WIDTH);
////    confirmPassword.setWidth(UserProfileView.WIDGET_WIDTH);
////    department.setWidth(UserProfileView.WIDGET_WIDTH);
  }

  private void bind() {
//    updateButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent event) {
//        if (create) {
//          presenter.doCreateUser(driver.flush());
//        } else {
//          presenter.doUpdateUser(driver.flush());
//        }
//      }
//    });
//
//    cancelButton.addClickHandler(new ClickHandler() {
//      @Override
//      public void onClick(ClickEvent event) {
//        if (Window.confirm("Are you sure?")) {
//          presenter.doCancel();
//        }
//      }
//    });
//
//    firstName.addKeyUpHandler(handler);
//    lastName.addKeyUpHandler(handler);
//    username.addKeyUpHandler(handler);
//    password.addKeyUpHandler(handler);
//    confirmPassword.addKeyUpHandler(handler);
//    department.addValueChangeHandler(new ValueChangeHandler<String>() {
//      @Override
//      public void onValueChange(ValueChangeEvent<String> event) {
//        enableUpdateButton();
//      }
//    });
  }

  private void initialize() {
//    updateButton.setEnabled(false);
//    cancelButton.setEnabled(false);
//
//    firstName.setValue("");
//    lastName.setValue("");
//    email.setValue("");
//    username.setValue("");
//    password.setValue("");
//    confirmPassword.setValue("");
//
//    department.setValue(null);
  }

  private void enableUpdateButton() {
//    boolean enabled = (username.getValue() != null) && (username.getValue()
//                                                                .length() > 0
//    ) &&
//                      (department.getValue() != null) &&
//                      (password.getValue() != null) && (password.getValue()
//                                                                .length() > 0
//    ) &&
//                      (confirmPassword.getValue() != null) && (confirmPassword.getValue()
//                                                                              .length() > 0
//    ) && (confirmPassword.getValue()
//                         .equals(password.getValue())
//                      );
//    updateButton.setEnabled(enabled);
  }

  @Override
  public Widget asWidget() {
    return container;
  }

  @Override
  public void clear() {
//    initialize();
  }

  @Override
  public void showUser(UserBean user,
                       boolean create) {
//    this.create = create;
//    driver.edit(user);
//    updateButton.setEnabled(false);
//    if (create) {
//      updateButton.setText("Add User");
//    } else {
//      updateButton.setText("Update User");
//    }
//    cancelButton.setEnabled(true);
//
//    firstName.selectAll();
//    firstName.setFocus(true);
  }

  @Override
  public void setPresenter(IUserProfilePresenter presenter) {
    this.presenter = presenter;
  }

  interface Driver
      extends SimpleBeanEditorDriver<UserBean, UserProfileView> {

  }


  @Override
  public IUserProfilePresenter getPresenter() {
    return presenter;
  }
}
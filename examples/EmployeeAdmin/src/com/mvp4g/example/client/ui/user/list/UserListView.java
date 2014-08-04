package com.mvp4g.example.client.ui.user.list;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ClickableTextCell;
import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.resources.Resources;
import com.mvp4g.example.client.widgets.FlowLayoutPanel;
import com.mvp4g.example.shared.dto.UserBean;

import java.util.List;

public class UserListView
    extends ResizeComposite
    implements IUserListView,
               ReverseViewInterface<IUserListView.IUserListPresenter>,
               ProvidesResize,
               RequiresResize {

  private IUserListView.IUserListPresenter presenter;

  private FlowLayoutPanel container;

  private DataGrid<UserBean> grid;

  private Button deleteButton;
  private Button newButton;

  private UserBean currentSelection;

  public UserListView() {
    createView();

    bind();

    initWidget(container);
  }

  private void createView() {
    container = new FlowLayoutPanel();
    container.addStyleName(Resources.CSS.employeeAdmin()
                                        .container());

    DockLayoutPanel fpContainer = new DockLayoutPanel(Style.Unit.PX);
    fpContainer.setSize("100%",
                        "100%");
    container.add(fpContainer);

    FlowLayoutPanel fp01 = new FlowLayoutPanel();
    Label la01 = new Label("Users");
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
    newButton = new Button("New");
    newButton.getElement()
             .getStyle()
             .setMarginRight(12,
                             Style.Unit.PX);
    fp02.add(newButton);
    deleteButton = new Button("Delete");
    fp02.add(deleteButton);
    fpContainer.addSouth(fp02,
                         42);

    createGrid();
    FlowLayoutPanel fp03 = new FlowLayoutPanel();
    fp03.getElement()
        .getStyle()
        .setBorderWidth(1,
                        Style.Unit.PX);
    fp03.getElement()
        .getStyle()
        .setBorderStyle(Style.BorderStyle.SOLID);
    fp03.add(grid);
    fpContainer.add(fp03);
  }

  private void bind() {
    newButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        presenter.doNewUser();
      }
    });

    deleteButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent event) {
        if (currentSelection == null) {
          Window.alert("Please select a user");
        } else {
          if (Window.confirm("Are you sure?")) {
            presenter.doDeleteUser(currentSelection);
          }
        }
      }
    });
  }

  private void createGrid() {
    grid = new DataGrid<UserBean>();
    grid.setHeight("100%");
    grid.getElement()
        .getStyle()
        .setOverflow(Style.Overflow.HIDDEN);


    addColumn(new ClickableTextCell(),
              "First Name",
              new GetValue<String>() {
                @Override
                public String getValue(UserBean user) {
                  return user.getFirstName();
                }
              },
              new FieldUpdater<UserBean, String>() {
                @Override
                public void update(int index,
                                   UserBean object,
                                   String value) {
                  currentSelection = object;
                  presenter.doShowUser(object);
                }
              });

    addColumn(new ClickableTextCell(),
              "Last Name",
              new GetValue<String>() {
                @Override
                public String getValue(UserBean user) {
                  return user.getLastName();
                }
              },
              new FieldUpdater<UserBean, String>() {
                @Override
                public void update(int index,
                                   UserBean object,
                                   String value) {
                  currentSelection = object;
                  presenter.doShowUser(object);
                }
              });

    addColumn(new ClickableTextCell(),
              "E-Mail",
              new GetValue<String>() {
                @Override
                public String getValue(UserBean user) {
                  return user.getEmail();
                }
              },
              new FieldUpdater<UserBean, String>() {
                @Override
                public void update(int index,
                                   UserBean object,
                                   String value) {
                  currentSelection = object;
                  presenter.doShowUser(object);
                }
              });

    addColumn(new ClickableTextCell(),
              "Department",
              new GetValue<String>() {
                @Override
                public String getValue(UserBean user) {
                  return user.getDepartment();
                }
              },
              new FieldUpdater<UserBean, String>() {
                @Override
                public void update(int index,
                                   UserBean object,
                                   String value) {
                  currentSelection = object;
                  presenter.doShowUser(object);
                }
              });
  }

  /**
   * Add a column with a header.
   *
   * @param <C>        the cell type
   * @param cell       the cell used to render the column
   * @param headerText the header string
   * @param getter     the value getter for the cell
   */
  private <C> Column<UserBean, C> addColumn(Cell<C> cell,
                                            String headerText,
                                            final GetValue<C> getter,
                                            FieldUpdater<UserBean, C> fieldUpdater) {
    Column<UserBean, C> column = new Column<UserBean, C>(cell) {
      @Override
      public C getValue(UserBean object) {
        return getter.getValue(object);
      }
    };
    column.setFieldUpdater(fieldUpdater);
    grid.addColumn(column,
                   headerText);
    return column;
  }

  @Override
  public void setUserList(List<UserBean> userList) {
    grid.setRowData(0,
                    userList);
    currentSelection = null;
  }

  @Override
  public void setUserSelected(boolean enabled) {
    if (enabled) {
      deleteButton.setEnabled(true);
    } else {
      deleteButton.setEnabled(false);
    }
  }

  /**
   * Get a cell value from a record.
   *
   * @param <T> the cell type
   */
  private static interface GetValue<T> {
    T getValue(UserBean user);

  }  @Override
  public void setPresenter(IUserListPresenter presenter) {
    this.presenter = presenter;
  }



  @Override
  public IUserListView.IUserListPresenter getPresenter() {
    return this.presenter;
  }


}

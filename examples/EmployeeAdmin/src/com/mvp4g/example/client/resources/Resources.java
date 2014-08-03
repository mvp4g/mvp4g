package com.mvp4g.example.client.resources;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by hoss on 01.08.14.
 */
public interface Resources
    extends ClientBundle {

  Resources CSS = GWT.create(Resources.class);

  @ClientBundle.Source("EmployeeAdmin.css")
  public EmployeeAdminCssStyles employeeAdmin();


  public interface EmployeeAdminCssStyles
      extends CssResource {

    String addButtom();

    String clear();

    String container();

    String containerHeadline();

    String fieldContainerLabel();

    String fieldContainerWidget();

    String label();

    String removeButton();

    String selectedRoles();
  }
}

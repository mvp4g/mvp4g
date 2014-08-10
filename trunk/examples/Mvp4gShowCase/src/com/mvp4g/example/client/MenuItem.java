package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.TreeItem;

public class MenuItem
    extends TreeItem {

  private String  description  = null;
  private String  appId        = null;
  private String  projectName  = null;
  private String  eventBusName = null;
  private boolean withXml      = false;

  public MenuItem(String description,
                  String appId,
                  String projectName,
                  String eventBusName,
                  boolean withXml) {
    this.setText(projectName);
    this.description = description;
    this.appId = appId;
    this.projectName = projectName;
    this.eventBusName = eventBusName;
    this.withXml = withXml;
  }

  public String getDescription() {
    return description;
  }

  public String getAppId() {
    return appId;
  }

  public String getProjectName() {
    return projectName;
  }

  public String getEventBusName() {
    return eventBusName;
  }

  public boolean isWithXml() {
    return withXml;
  }

}

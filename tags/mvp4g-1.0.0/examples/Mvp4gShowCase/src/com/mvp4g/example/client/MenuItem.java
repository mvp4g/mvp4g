package com.mvp4g.example.client;

import com.google.gwt.user.client.ui.TreeItem;

public class MenuItem extends TreeItem {

	private String description = null;
	private String appId = null;
	private String projectName = null;

	public MenuItem(String description, String appId, String projectName ) {
		super( projectName );
		this.description = description;
		this.appId = appId;
		this.projectName = projectName;
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

}

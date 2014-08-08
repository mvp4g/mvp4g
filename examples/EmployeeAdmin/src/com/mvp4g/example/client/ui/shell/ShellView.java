package com.mvp4g.example.client.ui.shell;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.widgets.FlowLayoutPanel;

public class ShellView
    extends ResizeComposite
    implements IShellView,
               ReverseViewInterface<IShellView.IShellPresenter>,
               ProvidesResize,
               RequiresResize {

  private IShellView.IShellPresenter presenter;

  private DockLayoutPanel shellPanel;

  private FlowLayoutPanel profileView;
  private FlowLayoutPanel roleView;

  private Widget masterWidget;
  private Widget profileWidget;
  private Widget roleWidget;


  public ShellView() {
    shellPanel = new DockLayoutPanel(Style.Unit.PX);
    shellPanel.setSize("850px",
                       "100%");
    DockLayoutPanel profilePanel = new DockLayoutPanel(Style.Unit.PX);

    shellPanel.addSouth(profilePanel,
                        360);

    profileView = new FlowLayoutPanel();
    profilePanel.addWest(profileView,
                         425);
    roleView = new FlowLayoutPanel();
    profilePanel.add(roleView);

    initWidget(shellPanel);
  }

  @Override
  public void setMasterView(Widget widget) {
    if (masterWidget != null) {
      masterWidget.removeFromParent();
    }
    shellPanel.add(widget);
    masterWidget = widget;
  }

  @Override
  public void setProfileView(Widget widget) {
    if (profileWidget != null) {
      profileWidget.removeFromParent();
    }
    profileView.add(widget);
    profileWidget = widget;
  }

  @Override
  public void setRoleView(Widget widget) {
    if (roleWidget != null) {
      roleWidget.removeFromParent();
    }
    roleView.add(widget);
    roleWidget = widget;
  }

  @Override
  public IShellView.IShellPresenter getPresenter() {
    return this.presenter;
  }


  @Override
  public void setPresenter(IShellView.IShellPresenter presenter) {
    this.presenter = presenter;
  }
}

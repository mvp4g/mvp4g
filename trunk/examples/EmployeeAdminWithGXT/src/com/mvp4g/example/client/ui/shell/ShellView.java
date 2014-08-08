package com.mvp4g.example.client.ui.shell;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.*;

public class ShellView
    extends Viewport
    implements IShellView,
               ReverseViewInterface<IShellView.IShellPresenter> {

  private IShellView.IShellPresenter presenter;

  private BorderLayoutContainer shellPanel;

  private SimpleContainer           listView;
  private HorizontalLayoutContainer southPanel;
  private SimpleContainer           profileView;
  private SimpleContainer           roleView;

  private Widget masterWidget;
  private Widget profileWidget;
  private Widget roleWidget;


  public ShellView() {
    shellPanel = new BorderLayoutContainer();
    shellPanel.setSize("100%",
                       "100%");
    shellPanel.setBorders(true);
    super.add(shellPanel);

    southPanel = new HorizontalLayoutContainer();
    southPanel.setSize("100%",
                       "100%");
    BorderLayoutContainer.BorderLayoutData southPanelData = new BorderLayoutContainer.BorderLayoutData();
    southPanelData.setMargins(new Margins(4));
    shellPanel.setSouthWidget(southPanel,
                              southPanelData);

    profileView = new SimpleContainer();
    profileView.setSize("50%",
                        "420px");
    HorizontalLayoutContainer.HorizontalLayoutData hld01 = new HorizontalLayoutContainer.HorizontalLayoutData(.5,
                                                                                                              1,
                                                                                                              new Margins(3));
    southPanel.add(profileView,
                   hld01);

    roleView = new SimpleContainer();
    roleView.setSize("50%",
                     "420px");
    HorizontalLayoutContainer.HorizontalLayoutData hld02 = new HorizontalLayoutContainer.HorizontalLayoutData(.5,
                                                                                                              1,
                                                                                                              new Margins(3));
    southPanel.add(roleView,
                   hld02);

    VerticalLayoutContainer vlc = new VerticalLayoutContainer();
    listView = new SimpleContainer();
    listView.setSize("100%",
                     "100%");
    vlc.add(listView, new VerticalLayoutContainer.VerticalLayoutData(1, 1, new Margins(6, 6, 0, 6)));
    shellPanel.add(vlc);
  }

  @Override
  public void setMasterView(Widget widget) {
    if (masterWidget != null) {
      masterWidget.removeFromParent();
    }
    listView.add(widget);
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

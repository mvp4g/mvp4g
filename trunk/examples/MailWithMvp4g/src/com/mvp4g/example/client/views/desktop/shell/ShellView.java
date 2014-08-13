package com.mvp4g.example.client.views.desktop.shell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;
import com.mvp4g.example.client.ui.shell.IShellView;
import com.mvp4g.example.client.widgets.ReverseResizeComposite;

public class ShellView
    extends ReverseResizeComposite<IShellView.IShellPresenter>
    implements IShellView {

  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  SimpleLayoutPanel topPanel;
  @UiField
  SimpleLayoutPanel mailList;
  @UiField
  SimpleLayoutPanel shortcuts;
  @UiField
  SimplePanel       mailDetail;
  @UiField
  SplitLayoutPanel  splitter;

  private ShellView() {
    initWidget(binder.createAndBindUi(this));
  }

  @Override
  public void setTopView(Widget widget) {
    topPanel.add(widget);
  }

  @Override
  public void setListView(Widget widget) {
    mailList.add(widget);
  }

  @Override
  public void setShortCutsView(Widget widget) {
    shortcuts.add(widget);
  }

  @Override
  public void setDetailView(Widget widget) {
    mailDetail.add(widget);
  }

  interface Binder
      extends UiBinder<DockLayoutPanel, ShellView> {
  }
}

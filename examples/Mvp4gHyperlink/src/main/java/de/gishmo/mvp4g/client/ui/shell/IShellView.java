package de.gishmo.mvp4g.client.ui.shell;

import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IShellView
    extends RequiresResize,
            ReverseViewInterface<IShellView.IShellPresenter> {

  void setHeaderView(Widget widget);

  void setNavigationView(Widget widget);

  void setContentView(Widget widget);

  interface IShellPresenter {
  }

}

package de.gishmo.mvp4g.client.ui.page02;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IPage02View
    extends IsWidget,
            RequiresResize,
            ReverseViewInterface<IPage02View.IPage02Presenter> {

  interface IPage02Presenter {
  }
}

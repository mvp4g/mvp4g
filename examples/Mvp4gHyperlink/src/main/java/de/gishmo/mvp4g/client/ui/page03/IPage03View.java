package de.gishmo.mvp4g.client.ui.page03;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IPage03View
    extends IsWidget,
            RequiresResize,
            ReverseViewInterface<IPage03View.IPage03Presenter> {

  interface IPage03Presenter {
  }
}

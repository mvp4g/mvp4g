package de.gishmo.mvp4g.client.ui.page01;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IPage01View
    extends IsWidget,
            RequiresResize,
            ReverseViewInterface<IPage01View.IPage01Presenter> {

  interface IPage01Presenter {
  }
}

package de.gishmo.mvp4g.client.ui.header;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.mvp4g.client.view.ReverseViewInterface;

public interface IHeaderView
    extends IsWidget,
            RequiresResize,
            ReverseViewInterface<IHeaderView.IHeaderPresenter> {

  interface IHeaderPresenter {
  }
}

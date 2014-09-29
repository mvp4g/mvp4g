package de.gishmo.mvp4g.client.ui.navigation;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.mvp4g.client.view.ReverseViewInterface;

public interface INavigationView
    extends IsWidget,
            RequiresResize,
            ReverseViewInterface<INavigationView.INavigationPresenter> {

  interface INavigationPresenter {
  }
}

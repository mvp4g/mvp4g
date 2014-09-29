package de.gishmo.mvp4g.client.ui.navigation;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = INavigationView.class)
public class NavigationPresenter
    extends BasePresenter<INavigationView, Mvp4gHyperlinkEventBus>
    implements INavigationView.INavigationPresenter {

  public void bind() {
    eventBus.setNavigationView(view.asWidget());
  }
}

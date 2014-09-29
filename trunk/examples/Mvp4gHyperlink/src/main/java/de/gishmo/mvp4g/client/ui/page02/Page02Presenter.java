package de.gishmo.mvp4g.client.ui.page02;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage02View.class)
public class Page02Presenter
    extends BasePresenter<IPage02View, Mvp4gHyperlinkEventBus>
    implements IPage02View.IPage02Presenter {

  public void onShowPage02() {
    eventBus.setContentView(view.asWidget());
  }
}

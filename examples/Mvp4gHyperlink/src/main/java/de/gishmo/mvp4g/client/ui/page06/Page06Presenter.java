package de.gishmo.mvp4g.client.ui.page06;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage06View.class)
public class Page06Presenter
    extends BasePresenter<IPage06View, Mvp4gHyperlinkEventBus>
    implements IPage06View.IPage06Presenter {

  public void onShowPage06() {
    eventBus.setContentView(view.asWidget());
  }
}

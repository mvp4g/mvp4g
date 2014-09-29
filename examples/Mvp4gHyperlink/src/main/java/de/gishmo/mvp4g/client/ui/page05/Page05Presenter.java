package de.gishmo.mvp4g.client.ui.page05;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage05View.class)
public class Page05Presenter
    extends BasePresenter<IPage05View, Mvp4gHyperlinkEventBus>
    implements IPage05View.IPage05Presenter {

  public void onShowPage05() {
    eventBus.setContentView(view.asWidget());
  }
}

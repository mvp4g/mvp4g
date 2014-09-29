package de.gishmo.mvp4g.client.ui.page01;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage01View.class)
public class Page01Presenter
    extends BasePresenter<IPage01View, Mvp4gHyperlinkEventBus>
    implements IPage01View.IPage01Presenter {

  public void onStart() {
    eventBus.setContentView(view.asWidget());
  }

  public void onShowPage01() {
    eventBus.setContentView(view.asWidget());
  }
}

package de.gishmo.mvp4g.client.ui.page03;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage03View.class)
public class Page03Presenter
    extends BasePresenter<IPage03View, Mvp4gHyperlinkEventBus>
    implements IPage03View.IPage03Presenter {

  public void onShowPage03() {
    eventBus.setContentView(view.asWidget());
  }
}

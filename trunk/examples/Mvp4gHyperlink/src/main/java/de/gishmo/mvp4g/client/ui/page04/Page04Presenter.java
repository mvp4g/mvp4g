package de.gishmo.mvp4g.client.ui.page04;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IPage04View.class)
public class Page04Presenter
    extends BasePresenter<IPage04View, Mvp4gHyperlinkEventBus>
    implements IPage04View.IPage04Presenter {

  public void onShowPage04() {
    eventBus.setContentView(view.asWidget());
  }
}

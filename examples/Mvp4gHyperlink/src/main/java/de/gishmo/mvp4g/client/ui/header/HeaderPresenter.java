package de.gishmo.mvp4g.client.ui.header;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IHeaderView.class)
public class HeaderPresenter
    extends BasePresenter<IHeaderView, Mvp4gHyperlinkEventBus>
    implements IHeaderView.IHeaderPresenter {

  public void bind() {
    eventBus.setHeaderView(view.asWidget());
  }
}

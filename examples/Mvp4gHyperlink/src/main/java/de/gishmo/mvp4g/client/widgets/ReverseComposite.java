package de.gishmo.mvp4g.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.mvp4g.client.view.ReverseViewInterface;

public class ReverseComposite<T>
    extends Composite
    implements ReverseViewInterface<T> {

  protected T presenter;

  @Override
  public void setPresenter(T presenter) {
    this.presenter = presenter;
  }

  @Override
  public T getPresenter() {
    return presenter;
  }

}

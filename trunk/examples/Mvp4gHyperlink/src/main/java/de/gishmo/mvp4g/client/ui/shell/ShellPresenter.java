package de.gishmo.mvp4g.client.ui.shell;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import de.gishmo.mvp4g.client.Mvp4gHyperlinkEventBus;


@Presenter(view = IShellView.class)
public class ShellPresenter
    extends BasePresenter<IShellView, Mvp4gHyperlinkEventBus>
    implements IShellView.IShellPresenter {

  public void onSetHeaderView(Widget widget) {
    view.setHeaderView(widget);
  }

  public void onSetNavigationView(Widget widget){
    view.setNavigationView(widget);
  }

  public void onSetContentView(Widget widget) {
    GWT.debugger();
    view.setContentView(widget);
  }

}

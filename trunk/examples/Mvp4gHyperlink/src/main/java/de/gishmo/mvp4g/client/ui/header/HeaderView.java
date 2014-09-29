package de.gishmo.mvp4g.client.ui.header;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class HeaderView
    extends ReverseResizeComposite<IHeaderView.IHeaderPresenter>
    implements IHeaderView {

  private HeaderCSS         style;
  private SimpleLayoutPanel headerPanel;


  private HeaderView() {
    style = GWT.<HeaderResources>create(HeaderResources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Hyperlink Navigation - Test With NavigationConfirmation & History");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface HeaderCSS
      extends CssResource {

    String headline();

  }

  interface HeaderResources
      extends ClientBundle {

    @Source("header.css")
    HeaderCSS css();
  }
}

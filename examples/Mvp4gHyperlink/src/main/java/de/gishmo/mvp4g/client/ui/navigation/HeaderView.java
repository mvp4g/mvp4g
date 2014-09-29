package de.gishmo.mvp4g.client.ui.navigation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class NavigationView
    extends ReverseResizeComposite<INavigationView.INavigationPresenter>
    implements INavigationView {

  private NavigationCSS         style;
  private SimpleLayoutPanel headerPanel;


  private NavigationView() {
    style = GWT.<NavigationResources>create(NavigationResources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Hyperlink Navigation - Test With NavigationConfirmation & History");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface NavigationCSS
      extends CssResource {

    String headline();

  }

  interface NavigationResources
      extends ClientBundle {

    @Source("navigation.css")
    NavigationCSS css();
  }
}

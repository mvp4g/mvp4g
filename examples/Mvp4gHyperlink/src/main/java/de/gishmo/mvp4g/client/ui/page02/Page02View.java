package de.gishmo.mvp4g.client.ui.page02;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page02View
    extends ReverseResizeComposite<IPage02View.IPage02Presenter>
    implements IPage02View {

  private Page02CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page02View() {
    style = GWT.<Page02Resources>create(Page02Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 02");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page02CSS
      extends CssResource {

    String headline();

  }

  interface Page02Resources
      extends ClientBundle {

    @Source("page02.css")
    Page02CSS css();
  }
}

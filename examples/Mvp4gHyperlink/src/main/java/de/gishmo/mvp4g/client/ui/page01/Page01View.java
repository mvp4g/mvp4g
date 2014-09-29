package de.gishmo.mvp4g.client.ui.page01;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page01View
    extends ReverseResizeComposite<IPage01View.IPage01Presenter>
    implements IPage01View {

  private Page01CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page01View() {
    style = GWT.<Page01Resources>create(Page01Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 01");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page01CSS
      extends CssResource {

    String headline();

  }

  interface Page01Resources
      extends ClientBundle {

    @Source("page01.css")
    Page01CSS css();
  }
}

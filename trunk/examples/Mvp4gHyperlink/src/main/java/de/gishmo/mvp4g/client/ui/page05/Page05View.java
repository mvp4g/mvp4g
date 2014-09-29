package de.gishmo.mvp4g.client.ui.page05;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page05View
    extends ReverseResizeComposite<IPage05View.IPage05Presenter>
    implements IPage05View {

  private Page05CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page05View() {
    style = GWT.<Page05Resources>create(Page05Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 05");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page05CSS
      extends CssResource {

    String headline();

  }

  interface Page05Resources
      extends ClientBundle {

    @Source("page05.css")
    Page05CSS css();
  }
}

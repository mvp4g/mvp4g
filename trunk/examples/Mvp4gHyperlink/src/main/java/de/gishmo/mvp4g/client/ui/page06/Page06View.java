package de.gishmo.mvp4g.client.ui.page06;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page06View
    extends ReverseResizeComposite<IPage06View.IPage06Presenter>
    implements IPage06View {

  private Page06CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page06View() {
    style = GWT.<Page06Resources>create(Page06Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 06");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page06CSS
      extends CssResource {

    String headline();

  }

  interface Page06Resources
      extends ClientBundle {

    @Source("page06.css")
    Page06CSS css();
  }
}

package de.gishmo.mvp4g.client.ui.page04;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page04View
    extends ReverseResizeComposite<IPage04View.IPage04Presenter>
    implements IPage04View {

  private Page04CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page04View() {
    style = GWT.<Page04Resources>create(Page04Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 04");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page04CSS
      extends CssResource {

    String headline();

  }

  interface Page04Resources
      extends ClientBundle {

    @Source("page04.css")
    Page04CSS css();
  }
}

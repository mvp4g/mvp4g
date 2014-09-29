package de.gishmo.mvp4g.client.ui.page03;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class Page03View
    extends ReverseResizeComposite<IPage03View.IPage03Presenter>
    implements IPage03View {

  private Page03CSS         style;
  private SimpleLayoutPanel headerPanel;


  private Page03View() {
    style = GWT.<Page03Resources>create(Page03Resources.class)
               .css();
    style.ensureInjected();

    headerPanel = new SimpleLayoutPanel();

    Label label = new Label("Page 03");
    label.setStyleName(style.headline());
    headerPanel.add(label);
    initWidget(headerPanel);
  }

  public interface Page03CSS
      extends CssResource {

    String headline();

  }

  interface Page03Resources
      extends ClientBundle {

    @Source("page03.css")
    Page03CSS css();
  }
}

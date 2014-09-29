package de.gishmo.mvp4g.client.ui.shell;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.SimpleLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import de.gishmo.mvp4g.client.widgets.ReverseResizeComposite;

public class ShellView
    extends ReverseResizeComposite<IShellView.IShellPresenter>
    implements IShellView {

  private DockLayoutPanel shell;

  private SimpleLayoutPanel header;
  private SimpleLayoutPanel navigation;
  private SimpleLayoutPanel content;


  public ShellView() {
    shell = new DockLayoutPanel(Style.Unit.PX);

    header = new SimpleLayoutPanel();
    navigation = new SimpleLayoutPanel();
    content = new SimpleLayoutPanel();

    shell.addNorth(header, 75);
    shell.addWest(navigation, 275);
    shell.add(content);

    initWidget(shell);
  }


  @Override
  public void setHeaderView(Widget widget) {
    header.add(widget);
  }

  @Override
  public void setNavigationView(Widget widget) {
    navigation.add(widget);
  }

  @Override
  public void setContentView(Widget widget) {
    if (content.getElement()
               .getChildCount() > 0) {
      for (int i = 0; i < content.getElement()
                                 .getChildCount(); i++) {
        content.getElement()
               .getChild(i)
               .removeFromParent();
      }
    }
    content.add(widget);
  }
}

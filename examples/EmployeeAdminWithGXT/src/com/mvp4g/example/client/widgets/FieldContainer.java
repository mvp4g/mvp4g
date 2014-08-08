package com.mvp4g.example.client.widgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * container takes label & widget
 */
public class FieldContainer
    extends Composite {

  private FlowPanel container;

  public FieldContainer(String label,
                        Widget widget) {
    createWidget(label,
                 widget);
    initWidget(container);
  }

  private void createWidget(String label,
                            Widget widget) {
    container = new FlowPanel();

//    FlowPanel fpLabel = new FlowPanel();
//    fpLabel.addStyleName(Resources.CSS.employeeAdmin()
//                                      .fieldContainerLabel());
//    Label label01 = new Label(label);
//    label01.setStyleName(Resources.CSS.employeeAdmin()
//                                      .label());
//    fpLabel.add(label01);
//    container.add(fpLabel);
//
//    FlowPanel widgetContainer = new FlowPanel();
//    widgetContainer.addStyleName(Resources.CSS.employeeAdmin()
//                                              .fieldContainerWidget());
//    widgetContainer.add(widget);
//    container.add(widgetContainer);
//
//    FlowPanel clearFp = new FlowPanel();
//    clearFp.setStyleName(Resources.CSS.employeeAdmin()
//                                      .clear());
//    container.add(clearFp);
  }
}

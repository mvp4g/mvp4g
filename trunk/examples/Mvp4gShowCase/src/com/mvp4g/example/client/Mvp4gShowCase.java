package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Mvp4gShowCase
    implements EntryPoint {

  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    HorizontalPanel mainPanel = new HorizontalPanel();

    final MiddlePanel mPanel = new MiddlePanel();

    Tree menu = new Tree();
    menu.addSelectionHandler(new SelectionHandler<TreeItem>() {
      public void onSelection(SelectionEvent<TreeItem> event) {
        mPanel.set((MenuItem) event.getSelectedItem());
      }
    });

    initMenu(menu);
    mainPanel.add(menu);
    mainPanel.add(mPanel);
    menu.setSelectedItem(menu.getItem(0));
    mainPanel.setStyleName("mvp4gshowcase");
    RootPanel.get()
             .add(mainPanel);
  }

  private void initMenu(Tree menu) {
    //EmployeeAdmin
    String d1 = "The GWT PureMvc example (as shown <a href=\"http://employeeadm.appspot.com/\" target=\"blank\">here</a>) but implemented with the Mvp4g framework.";
    menu.addItem(new MenuItem(d1,
                              "mvp4gexea",
                              "EmployeeAdmin",
                              "EmployeeAdminEventBus",
                              false));

    //EmployeeAdmin with GXT
    String d2 = "Same example as EmployeeAdmin but implemented with GXT using different approaches (<a href=\"http://groups.google.com/group/mvp4g/browse_thread/thread/5ba8664afbbda1f7\" target=\"blank\">see Mvp4g and GXT post</a>).";
    menu.addItem(new MenuItem(d2,
                              "mvp4gexeagxt",
                              "EmployeeAdminWithGXT",
                              "EmployeeAdminWithGXTEventBus",
                              false));

    //Mvp4gHistory
    String d3 = "This example shows how history is managed thanks to a Place Service. (<a href=\"http://code.google.com/p/mvp4g/wiki/PlaceService\" target=\"blank\">see the wiki page for more information</a>).<br/>"
                + "<img src=\"beware.gif\" height=15 width=15/> You need to open this application <a href=\"http://mvp4ghistory.appspot.com\" target=\"blank\">mvp4gexhi.appspot.com</a> in a new window in order to see url modifications.";
    menu.addItem(new MenuItem(d3,
                              "mvp4gexhi",
                              "Mvp4gHistory",
                              "MyEventBus",
                              false));

    String d4 = "This example demonstrates how you can divide your application into modules and take advantage of GWT Code splitting feature (<a href=\"http://code.google.com/p/mvp4g/wiki/MultiModules\" target=\"blank\">see the wiki page for more information</a>)<br/>"
                + "It also illustrates all the features available with Mvp4g.<br/>"
                + "<img src=\"beware.gif\" height=15 width=15/> You need to open this application <a href=\"http://mvp4gmodules.appspot.com\" target=\"blank\">mvp4gexmo.appspot.com</a> in a new window in order to see url modifications.";
    menu.addItem(new MenuItem(d4,
                              "mvp4gexmo",
                              "Mvp4gModules",
                              "main/MainEventBus",
                              false));

    String d5 = "This example shows the GWT Mail example converted with Mvp4g. It illustrates how Mvp4g can be used with UiBinder.";
    menu.addItem(new MenuItem(d5,
                              "mvp4gexma",
                              "MailWithMvp4g",
                              "MailEventBus",
                              false));
  }
}

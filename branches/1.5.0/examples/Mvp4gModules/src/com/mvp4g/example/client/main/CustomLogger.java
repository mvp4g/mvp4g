package com.mvp4g.example.client.main;

import com.google.gwt.core.client.GWT;
import com.mvp4g.client.event.Mvp4gLogger;

public class CustomLogger
  implements Mvp4gLogger {

  public void log(String message,
                  int depth) {
    String indent = "";
    for (int i = 0; i < depth; ++i)
      indent += "    ";
    GWT.log(indent + "CustomLogger: " + message);
  }

}

/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.mvp4g.client.event;

import com.google.gwt.core.client.GWT;

/**
 * Default implementation of Mvp4gLogger.
 *
 * @author plcoirier
 */
public class DefaultMvp4gLogger
  implements Mvp4gLogger {

  static final String INDENT = "    ";

  public void log(String message,
                  int depth) {
    GWT.log(createLog(message,
                      depth),
            null);
  }

  String createLog(String message,
                   int depth) {
    if (depth == 0) {
      return message;
    } else {
      StringBuilder indent = new StringBuilder(message.length() + depth * INDENT.length());
      for (int i = 0; i < depth; ++i) {
        indent.append(INDENT);
      }
      indent.append(message);
      return indent.toString();
    }
  }
}

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
package de.gishmo.gwt.mvp4g2.client.eventbus.internal;

import de.gishmo.gwt.mvp4g2.client.Mvp4g2;
import de.gishmo.gwt.mvp4g2.client.eventbus.Mvp4g2Logger;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Default implementation of Mvp4gLogger.
 *
 * @author plcoirier
 */
public class DefaultMvp4g2Logger
  implements Mvp4g2Logger {

  static final String INDENT = "    ";

  public void log(String message,
                  int depth) {
    Mvp4g2.log(createLog(message,
                         depth));
  }

  String createLog(String message,
                   int depth) {
    if (depth == 0) {
      return message;
    } else {
      String indent = IntStream.range(0,
                                      depth)
                               .mapToObj(i -> INDENT)
                               .collect(Collectors.joining("",
                                                           "",
                                                           message));
      return indent;
    }
  }
}

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
package com.mvp4g.rebind.exception.loader;

import com.mvp4g.rebind.exception.InvalidMvp4gConfigurationException;

public class Mvp4gAnnotationException
  extends InvalidMvp4gConfigurationException {

  /**
   *
   */
  private static final long   serialVersionUID = -2933352484580972114L;
  private              String className        = null;
  private              String methodName       = null;

  public Mvp4gAnnotationException(String className,
                                  String methodName,
                                  String message) {
    super(message);
    this.className = className;
    this.methodName = methodName;
  }

  @Override
  public String getMessage() {

    StringBuilder builder = new StringBuilder(100);
    if ((className != null) && (className.length() > 0)) {
      builder.append(className);
      builder.append(": ");
    }
    if ((methodName != null) && (methodName.length() > 0)) {
      builder.append("Method ");
      builder.append(methodName);
      builder.append(": ");
    }
    builder.append(super.getMessage());

    return builder.toString();
  }

}

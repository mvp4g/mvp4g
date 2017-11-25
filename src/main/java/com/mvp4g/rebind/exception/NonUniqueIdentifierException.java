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
package com.mvp4g.rebind.exception;

/**
 * Error triggered when two or more Mvp4g elements share the same textual identifier.
 *
 * @author javier
 */
public class NonUniqueIdentifierException
  extends InvalidMvp4gConfigurationException {

  private static final long serialVersionUID = 3388673072418851036L;

  private static final String MESSAGE = "Element identifier '%s' is not globally unique.";

  public NonUniqueIdentifierException(String elementId) {
    super(getErrorMessage(elementId));
  }

  private static String getErrorMessage(String elementId) {
    return String.format(MESSAGE,
                         elementId);
  }
}

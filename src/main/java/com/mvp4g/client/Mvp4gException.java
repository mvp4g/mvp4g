/*
 * Copyright 2010 Pierre-Laurent Coirier
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
package com.mvp4g.client;

/**
 * Exception thrown when an exception occurs while executing code of the Mvp4g core library.<br>
 * <br>
 * You shouldn't try to catch these exceptions because if they occur, it means that there is an
 * error in the application (most of the time, it's a wrong configuration of the mvp4g configuration
 * file).<br>
 * <br>
 * A message is always associated to this type of exception in order to help the developer to fix
 * the error.
 *
 * @author plcoirier
 */
public class Mvp4gException
  extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = - 6357990791906431745L;

  /**
   * Build a Mvp4gException
   *
   * @param message message describing the error
   */
  public Mvp4gException(String message) {
    super(message);
  }

}

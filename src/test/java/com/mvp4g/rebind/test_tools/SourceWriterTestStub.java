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

/**
 *
 */
package com.mvp4g.rebind.test_tools;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * A SourceWriter double to aid isolation of unit tests.
 *
 * @author javier
 */
public class SourceWriterTestStub
  implements SourceWriter {

  private static final String NEW_LINE = System.getProperty("line.separator");

  private StringBuilder data = new StringBuilder();

  public void beginJavaDocComment() {
    throw new UnsupportedOperationException();
  }

  public void commit(TreeLogger logger) {
    throw new UnsupportedOperationException();
  }

  public void endJavaDocComment() {
    throw new UnsupportedOperationException();
  }

  public void indent() {
    // Testing with indentation is brittle: just ignore indentation calls
  }

  public void indentln(String s) {
    // Testing with indentation is brittle: just ignore indentation calls
  }

  public void indentln(String s,
                       Object... args) {
    // TODO Auto-generated method stub

  }

  public void outdent() {
    // Testing with indentation is brittle: just ignore indentation calls
  }

  public void print(String s) {
    data.append(s);
  }

  public void print(String s,
                    Object... args) {
    // TODO Auto-generated method stub

  }

  public void println() {
    data.append(NEW_LINE);
  }

  public void println(String s) {
    print(s);
    println();
  }

  public void println(String s,
                      Object... args) {
    // TODO Auto-generated method stub

  }

  public boolean dataContains(String s) {
    return getData().contains(s);
  }

  public String getData() {
    return data == null ?
           "" :
           data.toString();
  }
}

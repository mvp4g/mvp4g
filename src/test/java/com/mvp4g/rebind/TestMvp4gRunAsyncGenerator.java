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

package com.mvp4g.rebind;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.dev.javac.typemodel.StubClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.mvp4g.rebind.test_tools.GeneratorContextStub;
import com.mvp4g.rebind.test_tools.Mvp4gRunAsyncCallbackStub;
import com.mvp4g.rebind.test_tools.SourceWriterTestStub;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TestMvp4gRunAsyncGenerator {

  Mvp4gRunAsyncGenerator generator;
  SourceWriterTestStub   sourceWriter;
  TypeOracleStub         oracle;

  @Before
  public void setUp() {
    generator = new Mvp4gRunAsyncGenerator();
    sourceWriter = new SourceWriterTestStub();
    GeneratorContextStub context = new GeneratorContextStub();
    oracle = context.getTypeOracleStub();
  }

  @Test
  public void testClassesToImport() {
    String[] classesToImport = new String[] { "com.google.gwt.core.client.GWT",
                                              "com.google.gwt.core.client.RunAsyncCallback" };
    assertArrayEquals(classesToImport,
                      generator.getClassesToImport());
  }

  @Test
  public void testWriteClass() {
    assertOutput(getExpectedViews(),
                 false);
    generator.writeClass(sourceWriter,
                         "MyRunAsync");
    assertOutput(getExpectedViews(),
                 true);
  }

  private void assertOutput(String[] statements,
                            boolean expected) {
    String error = null;
    if (expected) {
      error = " not found in output data:\n" + sourceWriter.getData();
    } else {
      error = " unexpected in output data:\n" + sourceWriter.getData();
    }

    for (String statement : statements) {
      assertEquals(statement + error,
                   expected,
                   sourceWriter.dataContains(statement));
    }

  }

  private String[] getExpectedViews() {
    return new String[] { "public void load(MyRunAsync callback) {",
                          "GWT.runAsync(callback);" };
  }

  @Test
  public void testGetRunAsync() {
    Assert.assertEquals(Mvp4gRunAsyncCallbackStub.class.getCanonicalName(),
                        generator.getRunAsync(new StubClassType(oracle)));
  }

}

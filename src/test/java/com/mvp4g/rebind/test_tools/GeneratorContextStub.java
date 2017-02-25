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

package com.mvp4g.rebind.test_tools;

import java.io.OutputStream;
import java.io.PrintWriter;

import com.google.gwt.core.ext.CachedGeneratorResult;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.Artifact;
import com.google.gwt.core.ext.linker.GeneratedResource;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.google.gwt.dev.resource.ResourceOracle;

public class GeneratorContextStub
  implements GeneratorContext {

  private PropertyOracle propertyOracle = new PropertyOracleStub();

  private TypeOracleStub typeOracle = new TypeOracleStub();

  public TypeOracleStub getTypeOracleStub() {
    return typeOracle;
  }

  @Override
  public boolean checkRebindRuleAvailable(String sourceTypeName) {
    // nothing to do
    return false;
  }

  public void commit(TreeLogger logger,
                     PrintWriter pw) {
    // nothing to do
  }

  public void commitArtifact(TreeLogger logger,
                             Artifact<?> artifact)
    throws UnableToCompleteException {
    // nothing to do
  }

  public GeneratedResource commitResource(TreeLogger logger,
                                          OutputStream os)
    throws UnableToCompleteException {
    // nothing to do
    return null;
  }

  @Override
  public CachedGeneratorResult getCachedGeneratorResult() {
    // nothing to do
    return null;
  }

  public PropertyOracle getPropertyOracle() {
    return propertyOracle;
  }

  public ResourceOracle getResourcesOracle() {
    // nothing to do
    return null;
  }

  public TypeOracle getTypeOracle() {
    return typeOracle;
  }

  @Override
  public boolean isGeneratorResultCachingEnabled() {
    // nothing to do
    return false;
  }

  @Override
  public boolean isProdMode() {
    // nothing to do
    return false;
  }

  public PrintWriter tryCreate(TreeLogger logger,
                               String packageName,
                               String simpleName) {
    // nothing to do
    return null;
  }

  public OutputStream tryCreateResource(TreeLogger logger,
                                        String partialPath)
    throws UnableToCompleteException {
    // nothing to do
    return null;
  }

  @Override
  public boolean tryReuseTypeFromCache(String typeName) {
    // nothing to do
    return false;
  }

}

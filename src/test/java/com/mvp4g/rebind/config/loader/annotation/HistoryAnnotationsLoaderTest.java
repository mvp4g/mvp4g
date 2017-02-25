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

package com.mvp4g.rebind.config.loader.annotation;

import java.util.Set;

import com.mvp4g.client.annotation.History;
import com.mvp4g.rebind.config.element.HistoryConverterElement;
import com.mvp4g.rebind.test_tools.annotation.HistoryConverters;
import com.mvp4g.rebind.test_tools.annotation.history_converters.SimpleHistoryConverter01;

public class HistoryAnnotationsLoaderTest
  extends AbstractMvp4gAnnotationsWithServiceLoaderTest<History, HistoryAnnotationsLoader> {

  @Override
  protected HistoryAnnotationsLoader createLoader() {
    return new HistoryAnnotationsLoader();
  }

  @Override
  protected Class<?> getSimpleClass() {
    return SimpleHistoryConverter01.class;
  }

  @Override
  protected Set<HistoryConverterElement> getSet() {
    return configuration.getHistoryConverters();
  }

  @Override
  protected Class<?> getWithNameClass() {
    return HistoryConverters.HistoryConverterWithName.class;
  }

  @Override
  protected Class<?> getWrongInterface() {
    return Object.class;
  }

  @Override
  protected Class<?> getClassNotPublic() {
    return HistoryConverters.HistoryConverterNotPublic.class;
  }

  @Override
  protected Class<?> getClassWithNoParameter() {
    return HistoryConverters.HistoryConverterWithNoParameter.class;
  }

  @Override
  protected Class<?> getClassWithMoreThanOne() {
    return HistoryConverters.HistoryConverterWithMoreThanOneParameter.class;
  }

  @Override
  protected Class<?> getServiceWithName() {
    return HistoryConverters.HistoryConverterWithServiceAndName.class;
  }

  @Override
  protected Class<?> getService() {
    return HistoryConverters.HistoryConverterWithService.class;
  }

  @Override
  protected Class<?> getSameService() {
    return HistoryConverters.HistoryConverterWithSameService.class;
  }

  @Override
  protected Class<?> getClassNotAsync() {
    return HistoryConverters.HistoryConverterNotAsync.class;
  }

}

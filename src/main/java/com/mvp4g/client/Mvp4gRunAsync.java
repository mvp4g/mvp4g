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
package com.mvp4g.client;

import com.google.gwt.core.client.RunAsyncCallback;

/**
 * Interface to define a Mvp4gRunAsync. This class just defines a load method that will only contain
 * a call to GWT.runAsync.<br>
 * This class is used to fix an issue with multiple-devices support and code splitting where all
 * generated fragments go to the left-over fragment (see
 * http://groups.google.com/group/google-web-toolkit/browse_thread/thread/99759f1d000b4f8f/).<br>
 * <br>
 * Each Mvp4gRunAsync must be associated with its own RunAsynCallback implementation otherwise code
 * splitting is not working very well and some fragment can end up in the left-over.
 *
 * @author plcoirier
 */
public interface Mvp4gRunAsync<T extends RunAsyncCallback> {

  void load(T callback);

}

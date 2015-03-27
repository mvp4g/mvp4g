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
package com.mvp4g.rebind;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.*;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Class uses to create the implementation class of Mvp4gStarter
 *
 * @author plcoirier
 */
public class Mvp4gRunAsyncGenerator
  extends IncrementalGenerator {

  /*
   * A version id. Increment this as needed, when structural changes are made to
   * the generated output, specifically with respect to it's effect on the
   * caching and reuse of previous generator results. Previously cached
   * generator results will be invalidated automatically if they were generated
   * by a version of this generator with a different version id.
   */
  private static final long GENERATOR_VERSION_ID = 1L;

  @Override
  public RebindResult generateIncrementally(TreeLogger logger,
                                            GeneratorContext generatorContext,
                                            String typeName)
    throws UnableToCompleteException {

    TypeOracle typeOracle = generatorContext.getTypeOracle();
    assert (typeOracle != null);

    JClassType originalType = typeOracle.findType(typeName);
    if (originalType == null) {
      logger.log(TreeLogger.ERROR,
                 "Unable to find metadata for type '" + typeName + "'");
      throw new UnableToCompleteException();
    }

    if (originalType.isInterface() == null) {
      logger.log(TreeLogger.ERROR,
                 "'" + typeName + "' is not a interface");
      throw new UnableToCompleteException();
    }

    TreeLogger moduleLogger = logger.branch(TreeLogger.DEBUG,
                                            "Generating client proxy for remote service interface '" + originalType.getQualifiedSourceName() + "'",
                                            null);

    return create(originalType,
                  moduleLogger,
                  generatorContext,
                  typeName);
  }

  @Override
  public long getVersionId() {
    return GENERATOR_VERSION_ID;
  }


  private RebindResult create(JClassType originalType,
                              TreeLogger logger,
                              GeneratorContext context,
                              String typeName)
    throws UnableToCompleteException {

    Date start = new Date();

    String packageName                 = originalType.getPackage().getName();
    String originalClassName           = originalType.getSimpleSourceName();
    String generatedClassName          = originalClassName + "Impl";
    String generatedClassQualifiedName = packageName + "." + generatedClassName;

    // check weather there is a usual version or not.
    if (checkAlreadyGenerated(logger,
                              context,
                              generatedClassQualifiedName)) {
      // Log
      logger.log(TreeLogger.INFO,
                 "reuse already generated files",
                 null);
      // stop generating
      return new RebindResult(RebindMode.USE_EXISTING,
                              generatedClassQualifiedName);
    }

    logger.log(TreeLogger.INFO,
               "Generating writer for " + packageName + "." + generatedClassName,
               null);

    PrintWriter printWriter = context.tryCreate(logger,
                                                packageName,
                                                generatedClassName);

    ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory(packageName,
                                                                                     generatedClassName);

    classFactory.addImplementedInterface(originalType.getName());
    String[] classesToImport = getClassesToImport();
    for (String classToImport : classesToImport) {
      classFactory.addImport(classToImport);
    }

    if (printWriter != null) {
      SourceWriter sourceWriter = classFactory.createSourceWriter(context,
                                                                  printWriter);
      logger.log(TreeLogger.INFO,
                 "Generating source for " + generatedClassQualifiedName + " ",
                 null);
      writeClass(sourceWriter,
                 getRunAsync(originalType));
      sourceWriter.commit(logger);
    }

    Date end = new Date();

    logger.log(TreeLogger.INFO,
               "Mvp4g Module Cretor: " + (end.getTime() - start.getTime()) + "ms.");

    return new RebindResult(RebindMode.USE_ALL_NEW_WITH_NO_CACHING,
                            generatedClassQualifiedName);
  }

  private boolean checkAlreadyGenerated(TreeLogger logger,
                                        GeneratorContext ctx,
                                        String generatedClassQualifiedName) {

    CachedGeneratorResult lastRebindResult = ctx.getCachedGeneratorResult();

    if (lastRebindResult == null || ! ctx.isGeneratorResultCachingEnabled()) {
      return false;
    }
    /* it looks like we could use the existing one */
    return true;
  }

  void writeClass(SourceWriter sourceWriter,
                  String callBackType) {
    sourceWriter.print("public void load(");
    sourceWriter.print(callBackType);
    sourceWriter.println(" callback) {");
    sourceWriter.indent();
    sourceWriter.println("GWT.runAsync(callback);");
    sourceWriter.outdent();
    sourceWriter.println("}");
  }

  String[] getClassesToImport() {
    return new String[] {GWT.class.getName(), RunAsyncCallback.class.getName()};
  }

  String getRunAsync(JClassType originalType) {
    JMethod[] methods = originalType.getOverridableMethods();
    for (JMethod method : methods) {
      if ("load".equals(method.getName())) {
        return method.getParameters()[0].getType().getQualifiedSourceName();
      }
    }
    return null;
  }
}

/*
 * Copyright 2009 Pierre-Laurent Coirier
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
package com.mvp4g.util;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Class uses to create the implementation class of Mvp4gStarter
 * 
 * @author plcoirier
 *
 */
public class Mvp4gGenerator extends Generator {

	private SourceWriter sourceWriter;

	/*
	 * (non-Javadoc)
	 * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger, com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		String generatedClassQualifiedName;
		generatedClassQualifiedName = createClass(logger, context, typeName);

		if (generatedClassQualifiedName == null) {
			throw new UnableToCompleteException();
		}
		return generatedClassQualifiedName;
	}

	private String createClass(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		sourceWriter = getSourceWriter(logger, context, typeName);
		if (sourceWriter != null) {
			writeClass(logger);
			sourceWriter.commit(logger);
		}

		TypeOracle typeOracle = context.getTypeOracle();
		JClassType originalType = typeOracle.findType(typeName);
		return originalType.getParameterizedQualifiedSourceName() + "Impl";
	}

	private SourceWriter getSourceWriter(TreeLogger logger,
			GeneratorContext context, String typeName)
			throws UnableToCompleteException {
		logger.log(TreeLogger.INFO, "Generating source for " + typeName, null);

		TypeOracle typeOracle = context.getTypeOracle();
		JClassType originalType = typeOracle.findType(typeName);
		if (originalType == null) {
			logger.log(TreeLogger.ERROR, "Unable to find metadata for type '"
					+ typeName + "'", null);
			throw new UnableToCompleteException();
		}

		logger.log(TreeLogger.INFO, "Generating source for "
				+ originalType.getQualifiedSourceName(), null);

		String packageName = originalType.getPackage().getName();
		String originalClassName = originalType.getSimpleSourceName();
		String generatedClassName = originalClassName + "Impl";

		ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory(
				packageName, generatedClassName);
		classFactory.addImplementedInterface(originalType.getName());
		classFactory.addImport("com.mvp4g.client.event.EventBus");
		classFactory.addImport("com.mvp4g.client.event.Event");
		classFactory.addImport("com.mvp4g.client.event.Command");

		PrintWriter printWriter = context.tryCreate(logger, packageName,
				generatedClassName);
		if (printWriter == null) {
			return null;
		}

		return classFactory.createSourceWriter(context, printWriter);
	}

	private void writeClass(TreeLogger logger)
			throws UnableToCompleteException {

		sourceWriter.indent();
		sourceWriter.println("public void start(){");
		sourceWriter.indent();
		new Mvp4gConfigurationFileReader(sourceWriter, logger).writeConf();
		sourceWriter.outdent();
		sourceWriter.println("};");
	}
}

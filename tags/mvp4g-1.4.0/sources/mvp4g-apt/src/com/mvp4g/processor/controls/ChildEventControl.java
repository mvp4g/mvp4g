package com.mvp4g.processor.controls;

import java.util.List;
import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.processor.Messages;
import com.mvp4g.processor.ProcessorUtil;
import com.mvp4g.processor.bean.ApplicationInfo;
import com.mvp4g.processor.bean.ModuleInfo;

public class ChildEventControl {

	public void control( ProcessingEnvironment processingEnv, ExecutableElement e, TypeElement parentEventBus,
			List<? extends AnnotationValue> modulesToLoad, ApplicationInfo appInfo ) {

		if ( modulesToLoad != null ) {

			String mName;
			ModuleInfo info;
			TypeElement type;
			String module;
			String methodName = e.getSimpleName().toString();
			Map<String, ModuleInfo> modules = appInfo.getModules();
			for ( AnnotationValue value : modulesToLoad ) {
				module = ( (DeclaredType)value.getValue() ).toString();
				info = modules.get( module );
				if ( ( info == null ) || ( info.getCurrentEventBus() == null ) ) {
					processingEnv.getMessager().printMessage( Kind.ERROR, String.format( Messages.MODULE_NO_EVENT_BUS, module ), e );
				} else {
					type = info.getParentEventBus();
					if ( ( type == null ) || ( !type.getQualifiedName().toString().equals( parentEventBus.getQualifiedName().toString() ) ) ) {
						processingEnv.getMessager().printMessage( Kind.ERROR, String.format( Messages.MODULE_NOT_A_CHILD, parentEventBus.getQualifiedName(), module ), e );
					} else {
						type = info.getCurrentEventBus();
						boolean found = false;
						for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( type ) ) ) {
							mName = method.getSimpleName().toString();
							if ( mName.toString().equals( methodName ) ) {
								if ( method.getModifiers().contains( Modifier.PUBLIC ) ) {
									found = ProcessorUtil.sameParameters( e.getParameters(), method.getParameters(), e );
									if ( found ) {
										break;
									}
								}
							}
						}
						if ( !found ) {
							processingEnv.getMessager().printMessage(
									Kind.ERROR,
									String.format( Messages.MISSING_METHOD, type.getSimpleName(), ProcessorUtil.getMethodName( methodName, e
											.getParameters() ), e.getReturnType() ), e );
						}
					}
				}
			}
		}
	}
}

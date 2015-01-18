package com.google.gwt.dev.javac.typemodel;

import com.google.gwt.core.ext.typeinfo.JType;
import com.mvp4g.client.Mvp4gLoader;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter01;
import com.mvp4g.rebind.test_tools.annotation.views.SimpleView;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypeOracleStub
    extends TypeOracle {

  private boolean isGWT2 = true;

  @Override
  public JClassType findType(String name) {
    JClassType type;
    if ( "com.google.gwt.core.client.RunAsyncCallback".equals( name ) ) {
      // if GWT2, return any class as long as type is not null
      type = ( isGWT2 ) ? findType( Object.class.getName() ) : null;
    } else {
      type = super.findType(name);
      if (type == null) {
        try {
          type = addClass(Class.forName(name));
        } catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
      }
    }
    return type;
  }

  public JGenericType addClass(Class<?> c) {
    JGenericType type;
    if (!c.isArray()) {
      JPackage p = getOrCreatePackage(c.getPackage()
                                       .getName());
      Class<?> enclosingClass = c.getEnclosingClass();
      JClassType enclosingType = null;
      if (enclosingClass != null) {
        enclosingType = findType(enclosingClass.getName());
      }

      type = new MyGenericType(this,
                               p,
                               (enclosingType == null) ? null : enclosingType.getSimpleSourceName(),
                               c.getSimpleName(),
                               c.isInterface(),
                               new JTypeParameter[0]);

      Class<?> superClass = c.getSuperclass();
      if (superClass != null) {
        type.setSuperclass(findType(superClass.getName()));
      }

      List<JClassType> implementedInterfaces = getImplementedInterfaces(c);

      for (JClassType implementedInterface : implementedInterfaces) {
        type.addImplementedInterface(implementedInterface);
      }

      Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<>();
      for (Annotation a : c.getAnnotations()) {
        annotations.put(a.annotationType(),
                        a);
      }
      type.addAnnotations(annotations);

      if (c.getPackage()
           .getName()
           .contains("com.mvp4g.rebind.test_tools.annotation")) {
        JMethod method;
        String returnType;
        for (Method m : c.getDeclaredMethods()) {
          annotations = new HashMap<>();
          for (Annotation a : m.getAnnotations()) {
            annotations.put(a.annotationType(),
                            a);
          }

          method = new JMethod(type,
                               m.getName(),
                               annotations,
                               null);
          returnType = m.getReturnType()
                        .getCanonicalName();
          // if return type not an object, just return object
          if (!returnType.contains(".")) {
            returnType = Object.class.getName();
          }
          method.setReturnType(findType(returnType));
          if (m.getModifiers() == Modifier.PUBLIC) {
            method.addModifierBits(0x00000020);
          } else {
            method.addModifierBits(0x00000010);
          }

          Map<Class<? extends Annotation>, Annotation> declaredAnnotations = new HashMap<>();
          for (Class<?> param : m.getParameterTypes()) {
            new JParameter(method,
                           findType(param.getName()),
                           param.getSimpleName(),
                           declaredAnnotations,
                           true);
          }

        }
      }
    } else {
      type = (JGenericType) findType(Object.class.getCanonicalName());
    }

    return type;
  }

  private List<JClassType> getImplementedInterfaces(Class<?> c) {
    List<JClassType> interfaces = new ArrayList<>();
    for (Class<?> implementedInterface : c.getInterfaces()) {
      interfaces.add(new MyParameterizedType((JGenericType) findType(implementedInterface.getName()),
                                             null,
                                             new JClassType[0]));
    }

    Class<?> superClass = c.getSuperclass();
    if (superClass != null) {
      interfaces.addAll(getImplementedInterfaces(superClass));
    }

    return interfaces;
  }

  private class MyGenericType
      extends JGenericType {

    MyGenericType(TypeOracle arg0,
                  JPackage arg1,
                  String arg2,
                  String arg3,
                  boolean arg4,
                  JTypeParameter[] arg5) {
      super(arg0,
            arg1,
            arg2,
            arg3,
            arg4,
            arg5);
    }

    @Override
    public JParameterizedType asParameterizationOf(com.google.gwt.core.ext.typeinfo.JGenericType type) {
      JParameterizedType superType = super.asParameterizationOf(type);
      return (superType == null) ? null : new MyParameterizedType(this,
                                                                  null,
                                                                  new JClassType[0]);
    }

  }

  private class MyParameterizedType
      extends JParameterizedType {

    public MyParameterizedType(JGenericType baseType,
                               JClassType enclosingType,
                               JClassType[] typeArgs) {
      super(baseType,
            enclosingType,
            typeArgs);
    }

    @Override
    public JMethod findMethod(String name,
                              JType[] paramTypes) {
      JMethod method;

      if ("getEventBus".equals(name)) {
        method = new JMethod(this.getBaseType(),
                             name,
                             new HashMap<Class<? extends Annotation>, Annotation>(),
                             null);
        method.setReturnType(findType(EventBusWithLookup.class.getName()));
      } else if ("getView".equals(name)) {
        method = new JMethod(this.getBaseType(),
                             name,
                             new HashMap<Class<? extends Annotation>, Annotation>(),
                             null);
        method.setReturnType(findType(SimpleView.class.getCanonicalName()));
      } else if ("getPresenter".equals(name)) {
        method = new JMethod(this.getBaseType(),
                             name,
                             new HashMap<Class<? extends Annotation>, Annotation>(),
                             null);
        method.setReturnType(findType(SimplePresenter01.class.getCanonicalName()));
      } else {
        method = super.findMethod(name,
                                  paramTypes);
      }

      return method;
    }

    @Override
    public JMethod[] getMethods() {
      JMethod[] methods;
      Map<Class<? extends Annotation>, Annotation> declaredAnnotations = new HashMap<>();
      if (isAssignableTo(findType(HistoryConverter.class.getName()))) {
        JMethod method = new JMethod(this.getBaseType(),
                                     "convertFromToken",
                                     declaredAnnotations,
                                     null);
        new JParameter(method,
                       findType(String.class.getName()),
                       "eventType",
                       declaredAnnotations,
                       true);
        new JParameter(method,
                       findType(String.class.getName()),
                       "form",
                       declaredAnnotations,
                       true);
        new JParameter(method,
                       findType(EventBusWithLookup.class.getName()),
                       "eventBus",
                       declaredAnnotations,
                       true);
        methods = new JMethod[]{method,
                                method};
      } else if (isAssignableTo(findType(EventFilter.class.getName()))) {
        JMethod method = new JMethod(this.getBaseType(),
                                     "filterEvent",
                                     declaredAnnotations,
                                     null);
        new JParameter(method,
                       findType(String.class.getName()),
                       "eventType",
                       declaredAnnotations,
                       true);
        new JParameter(method,
                       findType(String.class.getName()),
                       "form",
                       declaredAnnotations,
                       true);
        new JParameter(method,
                       findType(EventBusWithLookup.class.getName()),
                       "eventBus",
                       declaredAnnotations,
                       true);
        methods = new JMethod[]{method,
                                method};
      } else if (isAssignableTo(findType(Mvp4gLoader.class.getName()))) {
        JMethod method = new JMethod(this.getBaseType(),
                                     "preLoad",
                                     declaredAnnotations,
                                     null);
        new JParameter(method,
                       findType(EventBusWithLookup.class.getName()),
                       "eventBus",
                       declaredAnnotations,
                       true);
        methods = new JMethod[]{method};
      } else {
        methods = super.getMethods();
      }

      return methods;
    }
  }

  /**
   * @param isGWT2 the isGWT2 to set
   */
  public void setGWT2(boolean isGWT2) {
    this.isGWT2 = isGWT2;
  }
}

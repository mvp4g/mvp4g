package com.mvp4g.rebind.config.element;

public class ChildModulesElementTest
  extends AbstractMvp4gElementTest<ChildModulesElement> {

  protected static final String[] properties = { "errorEvent",
                                                 "beforeEvent",
                                                 "afterEvent" };

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Override
  protected String getTag() {
    return "childModules";
  }

  @Override
  protected String getUniqueIdentifierName() {
    return ChildModulesElement.class.getName();
  }

  @Override
  protected ChildModulesElement newElement() {
    return new ChildModulesElement();
  }

}

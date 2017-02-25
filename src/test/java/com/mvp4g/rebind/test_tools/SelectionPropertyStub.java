package com.mvp4g.rebind.test_tools;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import com.google.gwt.core.ext.SelectionProperty;

public class SelectionPropertyStub
  implements SelectionProperty {

  private String name, value;

  public SelectionPropertyStub(String name,
                               String value) {
    super();
    this.name = name;
    this.value = value;
  }

  public String getName() {
    return name;
  }

  public String getCurrentValue() {
    return value;
  }

  public String getFallbackValue() {
    return value;
  }

  public SortedSet<String> getPossibleValues() {
    SortedSet<String> set = new TreeSet<String>();
    set.add(value);
    return set;
  }

  public List<? extends Set<String>> getFallbackValues(String value) {
    // TODO Auto-generated method stub
    return null;
  }

}

package com.mvp4g.util.config.element;

import com.mvp4g.util.exception.DuplicatePropertyNameException;

/**
 * A simple Mvp4g configuration element.</p>
 * 
 * A simple element consists of two attributes:
 * 
 * <ul>
 * <li/> A <i>name</i> uniquely identifying the element within the configuration;
 * <li/> A <i>class</i> specifying the fully qualified Java class for the element.
 * </ul>
 * 
 */
public class SimpleMvp4gElement extends Mvp4gElement {

	private ClassResolver resolver = new ClassResolver();
	
	public SimpleMvp4gElement() {
		super("simple");
	}
	
	public SimpleMvp4gElement(String tagName) {
		super(tagName);
	}
	
	public SimpleMvp4gElement(String name, String className) throws DuplicatePropertyNameException {
		this();
		setName(name);
		setClassName(className);
	}

	public void setName( String name ) throws DuplicatePropertyNameException {
		setProperty("name", name);
	}
	
	public String getName() {
		return getProperty("name");
	}
	
	public void setClassName( String className ) throws DuplicatePropertyNameException {
		setProperty("class", className);
	}
	
	public String getClassName() {
		String packageName = getProperty("package");
		String className = getProperty("class");
		return resolver.getClassNameFrom( packageName, className );
	}
	
	@Override
	public String toString() {
		return "[" + getName() + " : " + getClassName() + "]";
	}

	@Override
	public String getUniqueIdentifierName() {
		return "name";
	}
}

package com.google.gwt.dev.javac.typemodel;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.mvp4g.util.test_tools.Mvp4gRunAsyncCallbackTest;

public class StubClassType extends JClassType {

	TypeOracle oracle;

	public StubClassType( TypeOracle oracle ) {
		this.oracle = oracle;
	}

	@Override
	public JMethod[] getOverridableMethods() {
		Map<Class<? extends Annotation>, Annotation> declaredAnnotations = new HashMap<Class<? extends Annotation>, Annotation>();
		JMethod method = new JMethod( this, "load", declaredAnnotations, null );
		new JParameter( method, oracle.findType( Mvp4gRunAsyncCallbackTest.class.getCanonicalName() ), "callback", declaredAnnotations );
		return new JMethod[] { method };
	}

	@Override
	public JConstructor findConstructor( JType[] paramTypes ) {

		return null;
	}

	@Override
	public JField findField( String name ) {

		return null;
	}

	@Override
	public JMethod findMethod( String name, JType[] paramTypes ) {

		return null;
	}

	@Override
	public JClassType findNestedType( String typeName ) {

		return null;
	}

	@Override
	public <T extends Annotation> T getAnnotation( Class<T> annotationClass ) {

		return null;
	}

	@Override
	public Annotation[] getAnnotations() {

		return null;
	}

	@Override
	public JConstructor getConstructor( JType[] paramTypes ) throws NotFoundException {

		return null;
	}

	@Override
	public JConstructor[] getConstructors() {

		return null;
	}

	@Override
	public Annotation[] getDeclaredAnnotations() {

		return null;
	}

	@Override
	public JClassType getEnclosingType() {

		return null;
	}

	@Override
	public JClassType getErasedType() {

		return null;
	}

	@Override
	public JField getField( String name ) {

		return null;
	}

	@Override
	public JField[] getFields() {

		return null;
	}

	@Override
	public JClassType[] getImplementedInterfaces() {

		return null;
	}

	@Override
	public JMethod[] getInheritableMethods() {

		return null;
	}

	@Override
	public String getJNISignature() {

		return null;
	}

	@Override
	public JMethod getMethod( String name, JType[] paramTypes ) throws NotFoundException {

		return null;
	}

	@Override
	public JMethod[] getMethods() {

		return null;
	}

	@Override
	public String getName() {

		return null;
	}

	@Override
	public JClassType getNestedType( String typeName ) throws NotFoundException {

		return null;
	}

	@Override
	public JClassType[] getNestedTypes() {

		return null;
	}

	@Override
	public TypeOracle getOracle() {

		return null;
	}

	@Override
	public JMethod[] getOverloads( String name ) {

		return null;
	}

	@Override
	public JPackage getPackage() {

		return null;
	}

	@Override
	public String getQualifiedBinaryName() {

		return null;
	}

	@Override
	public String getQualifiedSourceName() {

		return null;
	}

	@Override
	public String getSimpleSourceName() {

		return null;
	}

	@Override
	public JClassType[] getSubtypes() {

		return null;
	}

	@Override
	public JClassType getSuperclass() {

		return null;
	}

	@Override
	public boolean isAbstract() {

		return false;
	}

	@Override
	public boolean isAnnotationPresent( Class<? extends Annotation> annotationClass ) {

		return false;
	}

	@Override
	public JArrayType isArray() {

		return null;
	}

	@Override
	public JClassType isClass() {

		return null;
	}

	@Override
	public boolean isDefaultInstantiable() {

		return false;
	}

	@Override
	public JEnumType isEnum() {

		return null;
	}

	@Override
	public boolean isFinal() {

		return false;
	}

	@Override
	public JGenericType isGenericType() {

		return null;
	}

	@Override
	public JClassType isInterface() {

		return null;
	}

	@Override
	public boolean isMemberType() {

		return false;
	}

	@Override
	public JParameterizedType isParameterized() {

		return null;
	}

	@Override
	public JPrimitiveType isPrimitive() {

		return null;
	}

	@Override
	public boolean isPrivate() {

		return false;
	}

	@Override
	public boolean isProtected() {

		return false;
	}

	@Override
	public boolean isPublic() {

		return false;
	}

	@Override
	public JRawType isRawType() {

		return null;
	}

	@Override
	public boolean isStatic() {

		return false;
	}

	@Override
	public JWildcardType isWildcard() {

		return null;
	}

	@Override
	protected void acceptSubtype( JClassType me ) {

	}

	@Override
	protected void getInheritableMethodsOnSuperclassesAndThisClass( Map<String, JMethod> methodsBySignature ) {

	}

	@Override
	protected void getInheritableMethodsOnSuperinterfacesAndMaybeThisInterface( Map<String, JMethod> methodsBySignature ) {

	}

	@Override
	protected int getModifierBits() {

		return 0;
	}

	@Override
	protected void notifySuperTypesOf( JClassType me ) {

	}

	@Override
	protected void removeSubtype( JClassType me ) {

	}

	@Override
	void addConstructor( JConstructor ctor ) {

	}

	@Override
	void addField( JField field ) {

	}

	@Override
	void addImplementedInterface( JClassType intf ) {

	}

	@Override
	void addMethod( JMethod method ) {

	}

	@Override
	void addModifierBits( int bits ) {

	}

	@Override
	void addNestedType( JClassType type ) {

	}

	@Override
	JClassType findNestedTypeImpl( String[] typeName, int index ) {

		return null;
	}

	@Override
	JClassType getSubstitutedType( JParameterizedType parameterizedType ) {

		return null;
	}

	@Override
	void notifySuperTypes() {

	}

	@Override
	void removeFromSupertypes() {

	}

	@Override
	void setSuperclass( JClassType type ) {

	}

}

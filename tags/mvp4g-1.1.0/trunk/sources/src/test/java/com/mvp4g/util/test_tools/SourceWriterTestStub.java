/**
 * 
 */
package com.mvp4g.util.test_tools;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * A SourceWriter double to aid isolation of unit tests.
 * 
 * @author javier
 * 
 */
public class SourceWriterTestStub implements SourceWriter {

	private static final String NEW_LINE = System.getProperty( "line.separator" );

	private StringBuilder data = new StringBuilder();

	public void beginJavaDocComment() {
		throw new UnsupportedOperationException();
	}

	public void commit( TreeLogger logger ) {
		throw new UnsupportedOperationException();
	}

	public void endJavaDocComment() {
		throw new UnsupportedOperationException();
	}

	public void indent() {
		// Testing with indentation is brittle: just ignore indentation calls
	}

	public void indentln( String s ) {
		// Testing with indentation is brittle: just ignore indentation calls
	}

	public void outdent() {
		// Testing with indentation is brittle: just ignore indentation calls
	}

	public void print( String s ) {
		data.append( s );
	}

	public void println() {
		data.append( NEW_LINE );
	}

	public void println( String s ) {
		print( s );
		println();
	}

	public String getData() {
		return data == null ? "" : data.toString();
	}

	public boolean dataContains( String s ) {
		return getData().contains( s );
	}
}

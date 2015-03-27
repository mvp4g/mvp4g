package com.mvp4g.rebind.config.element;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mvp4g.client.annotation.Debug.LogLevel;
import com.mvp4g.client.event.DefaultMvp4gLogger;

public class DebugElementTest extends AbstractMvp4gElementTest<DebugElement> {

	protected static final String[] properties = { "logLevel", "logger" };

	@Test
	public void testNullLogLevel() {
		assertEquals( LogLevel.SIMPLE.name(), element.getLogLevel() );
	}

	@Test
	public void testLoggerLevel() {
		assertEquals( DefaultMvp4gLogger.class.getName(), element.getLogger() );
	}

	@Override
	protected String[] getProperties() {
		return properties;
	}

	@Override
	protected String getTag() {
		return "debug";
	}

	@Override
	protected String getUniqueIdentifierName() {
		return DebugElement.class.getName();
	}

	@Override
	protected DebugElement newElement() {
		return new DebugElement();
	}

}

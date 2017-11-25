package de.gishmo.gwt.mvp4g2.client.annotation.internal;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to mark classes, which will used by the framework.
 * <p>
 * <p>Do not use lasses annotated with internalFrameworkClass!</p>
 *
 * @author Frank Hossfeld
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ForInternalUseOnly {
}

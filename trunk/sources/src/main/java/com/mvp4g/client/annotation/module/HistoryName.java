package com.mvp4g.client.annotation.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( RetentionPolicy.RUNTIME )
public @interface HistoryName {

	String value();

}

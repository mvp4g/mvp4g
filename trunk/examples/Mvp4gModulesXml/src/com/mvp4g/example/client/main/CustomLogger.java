package com.mvp4g.example.client.main;

import com.google.gwt.core.client.GWT;
import com.mvp4g.client.event.Mvp4gLogger;

public class CustomLogger implements Mvp4gLogger {

    @Override
    public void log( String message ) {
        GWT.log( "CustomLogger: " + message );
    }

}
